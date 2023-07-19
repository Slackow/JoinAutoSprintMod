package com.slackow.joinautosprintmod.mixin;

import com.slackow.joinautosprintmod.SprintOnRespawn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onPlayerRespawn", at = @At("HEAD"))
    public void onRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
        if (SprintOnRespawn.val) {
            var options = MinecraftClient.getInstance().options;
            var sprintKey = options.sprintKey;
            options.getSprintToggled().setValue(true);
            if (!sprintKey.isPressed()) {
                sprintKey.setPressed(true);
            }
        }
    }
}
