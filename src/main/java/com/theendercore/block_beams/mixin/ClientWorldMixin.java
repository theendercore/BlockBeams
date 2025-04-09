package com.theendercore.block_beams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.theendercore.block_beams.BeamControl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientWorldMixin {
    @Inject(method = "doAnimateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;animateTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", shift = At.Shift.AFTER))
    public void randomDisplayTick(CallbackInfo ci, @Local(argsOnly = true) BlockPos.MutableBlockPos pos, @Local net.minecraft.world.level.block.state.BlockState state) {
        BeamControl.spawn(state, (Level) (Object) this, pos);
    }
}
