package org.teamvoided.modid


import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DustParticleEffect
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.slf4j.Logger
import org.slf4j.LoggerFactory


const val MODID = "modid"

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger(MODID)

@Suppress("unused")
fun id(path: String): Identifier = Identifier(MODID, path)

//val keyBinding = KeyBindingHelper.registerKeyBinding(
//    KeyBinding(
//        "key.examplemod.spook",  // The translation key of the keybinding's name
//        InputUtil.Type.KEYSYM,  // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
//        GLFW.GLFW_KEY_R,  // The keycode of the key
//        "category.examplemod.test" // The translation key of the keybinding's category.
//    )
//)

@Suppress("unused")
fun onInitialize() {
    LOGGER.info(MODID)
}

fun getId(block: Block): Identifier = Registries.BLOCK.getId(block)
fun mcId(path: String): Identifier = Identifier(path)

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