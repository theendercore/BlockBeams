package com.theendercore.block_beems


import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DustParticleEffect
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.slf4j.Logger
import org.slf4j.LoggerFactory


const val MODID = "block_beems"

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger(MODID)
fun id(path: String): Identifier = Identifier(path)
fun getId(block: Block): Identifier = Registries.BLOCK.getId(block)
fun config(): Config = Config.INSTANCE

@JvmField
val PASSABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, id("$MODID:passable_blocks"))


@Suppress("unused")
fun onInitialize() {
    LOGGER.info("Entering the Matrix...")
    config().load()
    Keybinding.init()
}

fun beem(pos: BlockPos, color: Int) {
    for (i in 0 until 12) {
        try {
            MinecraftClient.getInstance().particleManager.addParticle(
                DustParticleEffect(Vec3d.unpackRgb(color).toVector3f(), 1f),
                pos.x.floorDiv(1) + 0.5,
                (pos.y + 1.2) + (0.25 * i),
                pos.z.floorDiv(1) + 0.5,
                0.0,
                10.0,
                0.0
            )
        } catch (throwable: Throwable) {
            LOGGER.warn("Could not spawn particle effect")
        }
    }

}