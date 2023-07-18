package com.theendercore.block_beems.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.block_beems.BlockBeemsKt.PASSABLE_BLOCKS;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("TAIL"), method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V")
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        com.theendercore.block_beems.BlockBeemsKt.config().getConfig().getEntries().forEach(
                (id, color) -> {
                    if (com.theendercore.block_beems.BlockBeemsKt.getId(state.getBlock()).equals(com.theendercore.block_beems.BlockBeemsKt.id(id))) {
                        if (world.getBlockState(pos.up()).isIn(PASSABLE_BLOCKS)
                                && world.getBlockState(pos.up(2)).isIn(PASSABLE_BLOCKS)
                                && world.getBlockState(pos.up(3)).isIn(PASSABLE_BLOCKS)) {
                            com.theendercore.block_beems.BlockBeemsKt.beem(pos, color);
                        }
                    }
                }
        );
    }

}