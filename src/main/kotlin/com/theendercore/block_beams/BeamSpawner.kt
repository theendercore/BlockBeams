package com.theendercore.block_beams

import com.theendercore.block_beams.BlockBeams.config
import com.theendercore.block_beams.BlockBeams.log
import com.theendercore.block_beams.BlockBeamsConfig.BlockCheckType.CLIENT_ENFORCED
import com.theendercore.block_beams.BlockBeamsConfig.BlockCheckType.SERVER_ENFORCED
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object BeamSpawner {

    @JvmStatic
    fun spawn(state: BlockState, level: Level, pos: BlockPos) {
        val particle = BlockBeamsConfig.particles[state.block] ?: return
        for (y in 1..3) {
            if (!isPassable(level.getBlockState(pos.above(y)))) {
                return
            }
        }
        particles(pos, particle)
    }

    fun particles(pos: BlockPos, particle: DustParticleOptions) = repeat(12) { y ->
        try {
            Minecraft.getInstance().particleEngine.createParticle(
                particle,
                pos.x.floorDiv(1) + 0.5,
                (pos.y + 1.2) + (0.25 * y),
                pos.z.floorDiv(1) + 0.5,
                0.0, 10.0, 0.0
            )
        } catch (_: Throwable) {
            log.warn("Could not spawn particle effect {}", particle)
        }
    }

    fun isPassable(state: BlockState): Boolean {
        return when (config.blockCheckType.get()) {
            CLIENT_ENFORCED -> state.`is`(BlockBeamsConfig.blockSet)
            SERVER_ENFORCED -> state.`is`(BlockBeams.PASSABLE_BLOCKS)
        }
    }

}