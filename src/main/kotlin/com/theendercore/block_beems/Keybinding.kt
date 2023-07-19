package com.theendercore.block_beems

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.registry.RegistryKeys
import org.lwjgl.glfw.GLFW


object Keybinding {
    private val configKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(
            "key.block_beems.config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.block_beems.generic"
        )
    )

    fun init() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick
        { client ->
            if (configKey.wasPressed()) {
                config().load()
                LOGGER.info("test")
                client.networkHandler!!.registryManager.get(RegistryKeys.BLOCK).getEntryList(PASSABLE_BLOCKS)
                    .stream().forEach { thing ->  LOGGER.info(thing.toString()) }
                client.networkHandler!!.registryManager.get(RegistryKeys.BLOCK).getEntryList(PASSABLE_BLOCKS)
                    .map { thing ->  LOGGER.info(thing.toString()) }
                LOGGER.info("end")
            }
        })
    }
}