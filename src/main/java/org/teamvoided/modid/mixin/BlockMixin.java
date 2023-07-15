package org.teamvoided.modid.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import static org.teamvoided.modid.ModIdKt.*;

@Mixin(Block.class)
public class BlockMixin {


    @Inject(at = @At("HEAD"), method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V")
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        if (getId(state.getBlock()).equals(mcId("iron_ore"))) {
            if (world.getBlockState(pos.up()).isAir()
                    && world.getBlockState(pos.up(2)).isAir()
                    && world.getBlockState(pos.up(3)).isAir()) {
                beem(pos, 0xe3c0aa);
            }
        }
    }

}
