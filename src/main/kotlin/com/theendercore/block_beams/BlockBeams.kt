package com.theendercore.block_beams


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding
import net.minecraft.block.Block
import net.minecraft.client.option.KeyBind
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BlockBeams {
    const val MODID = "block_beams"
    val log: Logger = LoggerFactory.getLogger(MODID)
    private val configKey: KeyBind = registerKeyBinding(
        KeyBind("key.$MODID.config", GLFW.GLFW_KEY_UNKNOWN, "category.$MODID.generic")
    )

    @Suppress("unused")
    fun onInitialize() {
        log.info("Entering the Blocktrix...")
        Config.load()
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { if (configKey.wasPressed()) Config.load() })
    }

    fun id(path: String): Identifier = Identifier.of(MODID, path)
    fun parseId(path: String): Identifier = Identifier.parse(path)
    fun getId(block: Block): Identifier = Registries.BLOCK.getId(block)
}