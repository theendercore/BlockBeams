package com.theendercore.block_beams.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.block_beams.BlockBeamsKt.*;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("TAIL"), method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V")
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        config().getConfig().getBlockBeams().forEach((id, color) -> {
            if (getId(state.getBlock()).equals(id(id)) && canRender(world, pos))
                beam(pos, color);
        });
    }

}
