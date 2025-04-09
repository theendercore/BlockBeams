package com.theendercore.block_beams

import com.theendercore.block_beams.BlockBeams.getId
import com.theendercore.block_beams.BlockBeams.id
import com.theendercore.block_beams.BlockBeams.log
import com.theendercore.block_beams.BlockBeams.parseId
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import java.awt.Color

object BeamControl {
    private val CLIENT_CACHE = mutableMapOf<Block, Boolean>()
    private val PASSABLE_BLOCKS: TagKey<Block> = TagKey.create(Registries.BLOCK, id("passable_blocks"))

    @JvmStatic
    fun spawn(state: BlockState, world: Level, pos: BlockPos) {
        val color = Config.parsedMap[getId(state.block)] ?: return
        for (i in 1..3) {
            if (!canPassable(world, pos, i)) return
        }
        particles(pos, color)
    }

    private fun particles(pos: BlockPos, color: String) = repeat(12) { i ->
        try {
            Minecraft.getInstance().particleEngine.createParticle(
                DustParticleOptions(Color.decode(color).rgb, 1f),
                pos.x.floorDiv(1) + 0.5,
                (pos.y + 1.2) + (0.25 * i),
                pos.z.floorDiv(1) + 0.5,
                0.0, 10.0, 0.0
            )
        } catch (throwable: Throwable) {
            log.warn("Could not spawn particle effect")
        }
    }

    private fun canPassable(world: Level, pos: BlockPos, dist: Int): Boolean {
        val state = world.getBlockState(pos.above(dist))
        return when (Config.data.blockCheckType) {
            BlockCheckType.CLIENT_ONLY -> clientOnlyCheck(state, Config.data)
            BlockCheckType.SERVER_ONLY -> state.`is`(PASSABLE_BLOCKS)
        }
    }

    private fun clientOnlyCheck(state: BlockState, c: ConfigData): Boolean {
        for (id in c.clientTag) {
            var cached = CLIENT_CACHE[state.block]
            if (cached == null) {
                cached = checkBlock(state, id)
                CLIENT_CACHE[state.block] = cached
            }
            if (cached) return true
        }
        return false
    }

    private fun checkBlock(state: BlockState, id: String): Boolean {
        return if (id.startsWith("#"))
            state.`is`(TagKey.create(Registries.BLOCK, parseId(id.removePrefix("#"))))
        else getId(state.block) == parseId(id)
    }
}