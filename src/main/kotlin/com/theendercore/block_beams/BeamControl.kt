package com.theendercore.block_beams

import com.theendercore.block_beams.BlockBeams.getId
import com.theendercore.block_beams.BlockBeams.id
import com.theendercore.block_beams.BlockBeams.log
import com.theendercore.block_beams.BlockBeams.parseId
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DustParticleEffect
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.awt.Color

object BeamControl {
    private val CLIENT_CACHE = mutableMapOf<Block, Boolean>()
    private val PASSABLE_BLOCKS: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, id("passable_blocks"))

    @JvmStatic
    fun spawn(state: BlockState, world: World, pos: BlockPos) {
        val color = Config.parsedMap[getId(state.block)] ?: return
        for (i in 1..3) {
            if (!canPassable(world, pos, i)) return
        }
        particles(pos, color)
    }

    private fun particles(pos: BlockPos, color: String) = repeat(12) { i ->
        try {
            MinecraftClient.getInstance().particleManager.addParticle(
                DustParticleEffect(Vec3d.unpackRgb(Color.decode(color).rgb).toVector3f(), 1f),
                pos.x.floorDiv(1) + 0.5,
                (pos.y + 1.2) + (0.25 * i),
                pos.z.floorDiv(1) + 0.5,
                0.0, 10.0, 0.0
            )
        } catch (throwable: Throwable) {
            log.warn("Could not spawn particle effect")
        }
    }

    private fun canPassable(world: World, pos: BlockPos, dist: Int): Boolean {
        val state = world.getBlockState(pos.up(dist))
        return when (Config.data.blockCheckType) {
            BlockCheckType.CLIENT_ONLY -> clientOnlyCheck(state, Config.data)
            BlockCheckType.SERVER_ONLY -> state.isIn(PASSABLE_BLOCKS)
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
            state.isIn(TagKey.of(RegistryKeys.BLOCK, parseId(id.removePrefix("#"))))
        else getId(state.block) == parseId(id)
    }
}