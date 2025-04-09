package com.theendercore.block_beams


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding
import net.minecraft.client.KeyMapping
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BlockBeams {
    const val MODID = "block_beams"
    val log: Logger = LoggerFactory.getLogger(MODID)
    private val configKey = registerKeyBinding(
        KeyMapping("key.$MODID.config", GLFW.GLFW_KEY_UNKNOWN, "category.$MODID.generic")
    )

    @Suppress("unused")
    fun onInitialize() {
        log.info("Entering the Blocktrix...")
        Config.load()
        ClientTickEvents.END_CLIENT_TICK.register { if (configKey.consumeClick()) Config.load() }
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)
    fun parseId(path: String): ResourceLocation = ResourceLocation.parse(path)
    fun getId(block: Block): ResourceLocation = BuiltInRegistries.BLOCK.getKey(block)
}