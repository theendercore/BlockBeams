package com.theendercore.block_beams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.theendercore.block_beams.BeamControl;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(method = "randomBlockDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/random/RandomGenerator;)V", shift = At.Shift.AFTER))
    public void randomDisplayTick(CallbackInfo ci, @Local(argsOnly = true) BlockPos.Mutable pos, @Local BlockState state) {
        BeamControl.spawn(state, (World) (Object) this, pos);
    }
}
