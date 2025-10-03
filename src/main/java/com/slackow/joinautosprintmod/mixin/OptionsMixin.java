package com.slackow.joinautosprintmod.mixin;

import com.slackow.joinautosprintmod.SprintOptions;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Options.class)
public class OptionsMixin {

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;<init>(Ljava/lang/String;Lnet/minecraft/client/OptionInstance$TooltipSupplier;Lnet/minecraft/client/OptionInstance$CaptionBasedToString;Lnet/minecraft/client/OptionInstance$ValueSet;Ljava/lang/Object;Ljava/util/function/Consumer;)V"
            ),
            index = 4,
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/client/Options;toggleSprint:Lnet/minecraft/client/OptionInstance;",
                            opcode = Opcodes.PUTFIELD,
                            shift = At.Shift.BEFORE
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/client/Options;toggleSprint:Lnet/minecraft/client/OptionInstance;",
                            opcode = Opcodes.PUTFIELD,
                            shift = At.Shift.AFTER
                    )
            )
    )
    private Object makeToggleSprintDefaultTrue(Object defaultValue) {
        var options = SprintOptions.load(null);
        return options.changeDefaultToToggleSprint ? Boolean.TRUE : defaultValue;
    }

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;<init>(Ljava/lang/String;Lnet/minecraft/client/OptionInstance$TooltipSupplier;Lnet/minecraft/client/OptionInstance$CaptionBasedToString;Lnet/minecraft/client/OptionInstance$ValueSet;Ljava/lang/Object;Ljava/util/function/Consumer;)V"
            ),
            index = 1,
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/client/Options;toggleSprint:Lnet/minecraft/client/OptionInstance;",
                            opcode = Opcodes.PUTFIELD,
                            shift = At.Shift.BEFORE
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/client/Options;toggleSprint:Lnet/minecraft/client/OptionInstance;",
                            opcode = Opcodes.PUTFIELD,
                            shift = At.Shift.AFTER
                    )
            )
    )
    private OptionInstance.TooltipSupplier<Boolean> changeToggleSprintTooltip(OptionInstance.TooltipSupplier<Boolean> defaultValue) {
        return (c) -> {
            var options = SprintOptions.load(null);
            return c && options.anyOn() ? Tooltip.create(Component.translatable("joinautosprint.tooltip.affected")) : Tooltip.create(Component.empty());
        };
    }
}
