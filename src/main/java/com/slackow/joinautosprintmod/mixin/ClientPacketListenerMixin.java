package com.slackow.joinautosprintmod.mixin;

import com.slackow.joinautosprintmod.SprintOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handleRespawn", at = @At("HEAD"))
    public void onRespawn(CallbackInfo ci) {
        SprintOptions sprintOptions = SprintOptions.load(Minecraft.getInstance().gameDirectory.toPath().resolve("config"));
        if (sprintOptions.autoSprintOnRespawn) {
            var options = Minecraft.getInstance().options;
            var sprintKey = options.keySprint;
            options.toggleSprint().set(true);
            if (!sprintKey.isDown()) {
                sprintKey.setDown(true);
            }
        }
    }
}
