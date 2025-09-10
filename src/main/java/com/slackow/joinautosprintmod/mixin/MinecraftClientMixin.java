package com.slackow.joinautosprintmod.mixin;

import com.slackow.joinautosprintmod.SprintOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Shadow @Final public Options options;

    @Shadow
    @Final
    public File gameDirectory;

    @Inject(method = "setLevel", at = @At("HEAD"))
    public void onWorldJoin(CallbackInfo ci) {
        SprintOptions sprintOptions = SprintOptions.load(gameDirectory.toPath().resolve("config"));
        if (sprintOptions.autoSprintOnWorldJoin) {
            var sprintKey = options.keySprint;
            options.toggleSprint().set(true);
            if (!sprintKey.isDown()) {
                sprintKey.setDown(true);
            }
        }
    }
}

