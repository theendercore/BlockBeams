package com.theendercore.block_beams.mixin;

import com.theendercore.block_beams.BlockBeams;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("TAIL"), method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V")
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockBeams.INSTANCE.config().getConfig().getBlockBeams().forEach((id, color) -> {
            if (BlockBeams.INSTANCE.getId(state.getBlock()).equals(BlockBeams.INSTANCE.id(id)) && BlockBeams.INSTANCE.canRender(world, pos))
                BlockBeams.INSTANCE.beam(pos, color);
        });
    }

}
