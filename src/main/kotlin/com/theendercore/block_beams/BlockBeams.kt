package com.theendercore.block_beams


import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BlockBeams {

    const val MODID = "block_beams"

    val log: Logger = LoggerFactory.getLogger(MODID)

    var config = ConfigApi.registerAndLoadConfig(::BlockBeamsConfig, RegisterType.CLIENT)

    val PASSABLE_BLOCKS: TagKey<Block> = TagKey.create(Registries.BLOCK, id("passable_blocks"))

    @Suppress("unused")
    fun onInitialize() {
        log.info("Entering the Blocktrix...")
        BlockBeamsConfig.staticReload()
    }

    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MODID, path)
    fun parseId(path: String): Identifier = Identifier.parse(path)

}