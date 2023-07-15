package com.theendercore.block_beems

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class Config {


    private val defaultConfig = ConfigData(
        mapOf(
            Pair("nether_quartz_ore", -1),
            Pair("coal_ore", 0),
            Pair("deepslate_coal_ore", 0),
            Pair("copper_ore", 13725018),
            Pair("deepslate_copper_ore", 13725018),
            Pair("lapis_ore", 4352721),
            Pair("deepslate_lapis_ore", 4352721),
            Pair("iron_ore", 14925994),
            Pair("deepslate_iron_ore", 14925994),
            Pair("redstone_ore", 15866651),
            Pair("deepslate_redstone_ore", 15866651),
            Pair("gold_ore", 15457862),
            Pair("deepslate_gold_ore", 15457862),
            Pair("nether_gold_ore", 15457862),
            Pair("emerald_ore", 1423167),
            Pair("deepslate_emerald_ore", 1423167),
            Pair("diamond_ore", 1884871),
            Pair("deepslate_diamond_ore", 1884871)
        )
    )
    private val configFile: File = FabricLoader.getInstance().configDir.resolve("$MODID.json").toFile()

    var config: ConfigData = defaultConfig

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json { prettyPrint = true; prettyPrintIndent = "\t" }

    fun load() {
        try {
            val stringData = FileReader(configFile).use { it.readText() }
            config = json.decodeFromString(stringData)
        } catch (e: IOException) {
            LOGGER.info("No config Found! Making a new one.")
            save(defaultConfig)
        }
    }

    private fun save(config: ConfigData) = FileWriter(configFile).use { it.write(json.encodeToString(config)) }

    companion object {
        val INSTANCE = Config()
    }
}


@Serializable
data class ConfigData(val entries: Map<String, Int>)
