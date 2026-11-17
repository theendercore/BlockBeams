package com.theendercore.block_beams

import com.theendercore.block_beams.BlockBeams.MODID
import com.theendercore.block_beams.BlockBeams.config
import com.theendercore.block_beams.BlockBeams.id
import com.theendercore.block_beams.BlockBeams.parseId
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.util.Translatable
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedRegistryType
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor.ColorHolder
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum.WidgetType
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import java.awt.Color
import kotlin.jvm.optionals.getOrNull

class BlockBeamsConfig : Config(id(MODID)) {

    @Translatable.Desc("A List of Blocks and the beam colors")
    var blockBeams = ValidatedMap.Builder<Block, ColorHolder>()
        .keyHandler(ValidatedRegistryType.of(BuiltInRegistries.BLOCK))
        .valueHandler(ValidatedColor())
        .defaults(DEFAULT_BEAMS)
        .build()


    @Translatable.Desc(
        "The way the mod check what blocks can be passed through. " +
                "\n[SERVER_ENFORCED] - (Block list is gotten from the server only, not 'cheaty' option), " +
                "\n[CLIENT_ENFORCED] - (Block list is gotten from the client only, the 'cheaty' option), ",
    )
    var blockCheckType = ValidatedEnum(BlockCheckType.SERVER_ENFORCED, WidgetType.CYCLING)

    @Translatable.Desc("The list of blocks that be passed through on the client")
    var clientTag = ValidatedSet.ofString(DEFAULT_TAG).toCondition(
        { blockCheckType.get().isClient() },
        { setOf() },
        Component.literal("Block Check Type must be set to \"CLIENT_ENFORCED\" to edit!"),
    )

    override fun onUpdateClient() {
        staticReload()
    }


    enum class BlockCheckType {
        SERVER_ENFORCED, CLIENT_ENFORCED;

        fun isClient(): Boolean = this == CLIENT_ENFORCED
    }

    companion object {

        var particles: Map<Block, DustParticleOptions> = mapOf()
            private set
        var blockSet: HolderSet<Block> = HolderSet.direct()
            private set

        fun staticReload() {
            particles = config.blockBeams.mapValues { (_, value) -> DustParticleOptions(value.argb(), 1f) }
            blockSet = if (config.blockCheckType.get().isClient())
                HolderSet.direct(
                    *config.clientTag.get().mapNotNull(::parseBlockOrTag).flatten().toTypedArray<Holder<Block>>()
                )
            else
                HolderSet.direct()
        }

        fun parseBlockOrTag(id: String): List<Holder<Block>>? {
            return if (id.startsWith("#")) {
                BuiltInRegistries.BLOCK.getTagOrEmpty(TagKey.create(Registries.BLOCK, parseId(id.removePrefix("#"))))
                    .toList()
            } else {
                BuiltInRegistries.BLOCK.get(parseId(id)).getOrNull()?.let(::listOf)
            }
        }

        val DEFAULT_BEAMS: Map<Block, ColorHolder> = mapOf(
            Blocks.NETHER_QUARTZ_ORE to color("#ffffff"),
            Blocks.COAL_ORE to color("#000000"),
            Blocks.DEEPSLATE_COAL_ORE to color("#000000"),
            Blocks.COPPER_ORE to color("#d16d5a"),
            Blocks.DEEPSLATE_COPPER_ORE to color("#d16d5a"),
            Blocks.LAPIS_ORE to color("#426ad1"),
            Blocks.DEEPSLATE_LAPIS_ORE to color("#426ad1"),
            Blocks.IRON_ORE to color("#e3c0aa"),
            Blocks.DEEPSLATE_IRON_ORE to color("#e3c0aa"),
            Blocks.REDSTONE_ORE to color("#f21b1b"),
            Blocks.DEEPSLATE_REDSTONE_ORE to color("#f21b1b"),
            Blocks.GOLD_ORE to color("#ebde46"),
            Blocks.DEEPSLATE_GOLD_ORE to color("#ebde46"),
            Blocks.NETHER_GOLD_ORE to color("#ebde46"),
            Blocks.EMERALD_ORE to color("#15b73f"),
            Blocks.DEEPSLATE_EMERALD_ORE to color("#15b73f"),
            Blocks.DIAMOND_ORE to color("#1cc2c7"),
            Blocks.DEEPSLATE_DIAMOND_ORE to color("#1cc2c7"),
        )

        fun color(hex: String): ColorHolder {
            val col = Color.decode(hex)
            return ColorHolder(col.red, col.green, col.blue, 255, false)
        }

        val DEFAULT_TAG: Set<String> = setOf(
            "air",
            "minecraft:water",
            "minecraft:pointed_dripstone",
            "minecraft:tripwire",
            "lever",
            "lightning_rod",
            "slime_block",
            "honey_block",
            "glow_lichen",
            "cobweb",
            "iron_bars",
            "conduit",
            "end_rod",
            "tripwire_hook",
            "ice",
            "torch",
            "wall_torch",
            "soul_torch",
            "soul_wall_torch",
            "redstone_torch",
            "redstone_wall_torch",

            "amethyst_cluster",
            "small_amethyst_bud",
            "medium_amethyst_bud",
            "large_amethyst_bud",

            "glass",
            "white_stained_glass",
            "orange_stained_glass",
            "magenta_stained_glass",
            "light_blue_stained_glass",
            "yellow_stained_glass",
            "lime_stained_glass",
            "pink_stained_glass",
            "gray_stained_glass",
            "light_gray_stained_glass",
            "cyan_stained_glass",
            "purple_stained_glass",
            "blue_stained_glass",
            "brown_stained_glass",
            "green_stained_glass",
            "red_stained_glass",
            "black_stained_glass",

            "glass_pane",
            "black_stained_glass_pane",
            "white_stained_glass_pane",
            "gray_stained_glass_pane",
            "light_gray_stained_glass_pane",
            "blue_stained_glass_pane",
            "light_blue_stained_glass_pane",
            "cyan_stained_glass_pane",
            "lime_stained_glass_pane",
            "green_stained_glass_pane",
            "orange_stained_glass_pane",
            "yellow_stained_glass_pane",
            "red_stained_glass_pane",
            "magenta_stained_glass_pane",
            "brown_stained_glass_pane",
            "purple_stained_glass_pane",
            "pink_stained_glass_pane",

            "#lanterns",
            "#chains",
            "#bars",
            "#wooden_shelves",
            "#minecraft:leaves",
            "#minecraft:all_signs",
            "#minecraft:banners",
            "#minecraft:buttons",
            "#minecraft:cave_vines",
            "#minecraft:candles",
            "#minecraft:climbable",
            "#minecraft:corals",
            "#minecraft:flower_pots",
            "#minecraft:fences",
            "#minecraft:fence_gates",
            "#minecraft:rails"
        )

    }
}