package ihh.propertymodifier;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.function.Predicate;

public final class ParsingUtil {
    public static Boolean parseBoolean(String value, String entry, String config, Predicate<Boolean> predicate) {
        switch (value) {
            case "true":
                return true;
            case "false":
                return false;
        }
        ConfigUtil.logInvalid(value, entry, config);
        return null;
    }

    public static Integer parseInt(String value, String entry, String config, Predicate<Integer> predicate) {
        try {
            int result = Integer.parseInt(value);
            if (!predicate.test(result)) {
                ConfigUtil.logInvalid(value, entry, config);
            }
            else return result;
        } catch (NumberFormatException e) {
            ConfigUtil.logInvalid(value, entry, config);
        }
        return null;
    }

    public static Float parseFloat(String value, String entry, String config, Predicate<Float> predicate) {
        try {
            float result = Float.parseFloat(value);
            if (!predicate.test(result)) {
                ConfigUtil.logInvalid(value, entry, config);
            }
            else return result;
        } catch (NumberFormatException e) {
            ConfigUtil.logInvalid(value, entry, config);
        }
        return null;
    }

    public static Block parseBlock(String value, String entry, String config, Predicate<Block> predicate) {
        Block block = ConfigUtil.fromCollection(value, new ArrayList<>(ForgeRegistries.BLOCKS.getValues()));
        if (block == null || block.properties.isAir || !predicate.test(block)) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
        return block;
    }

    public static Attribute parseAttribute(String value, String entry, String config, Predicate<Attribute> predicate) {
        Attribute attribute = ConfigUtil.fromCollection(value, ForgeRegistries.ATTRIBUTES.getValues());
        if (attribute == null || !predicate.test(attribute)) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
        return attribute;
    }

    public static Enchantment parseEnchantment(String value, String entry, String config, Predicate<Enchantment> predicate) {
        Enchantment enchantment = ConfigUtil.fromCollection(value, ForgeRegistries.ENCHANTMENTS.getValues());
        if (enchantment == null || !predicate.test(enchantment)) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
        return enchantment;
    }

    public static Enchantment.Rarity parseEnchantmentRarity(String value, String entry, String config, Predicate<Enchantment.Rarity> predicate) {
        try {
            return Enchantment.Rarity.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException x) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
    }

    public static Rarity parseRarity(String value, String entry, String config, Predicate<Rarity> predicate) {
        try {
            return Rarity.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException x) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
    }

    public static CreativeModeTab parseCreativeModeTab(String value, String entry, String config, Predicate<CreativeModeTab> predicate) {
        CreativeModeTab creativeModeTab = null;
        for (CreativeModeTab i : CreativeModeTab.TABS) {
            if (i.getRecipeFolderName().equals(value)) {
                creativeModeTab = i;
            }
        }
        if (creativeModeTab == null) {
            ConfigUtil.logInvalid(value, entry, config);
        }
        return creativeModeTab;
    }

    public static Lazy<Ingredient> parseRepairMaterial(String value, String entry, String config, Predicate<Lazy<Ingredient>> predicate) {
        return Lazy.of(() -> value.startsWith("#") ? Ingredient.of(ItemTags.bind(value.substring(1))) : Ingredient.of(ConfigUtil.fromCollection(value, new ArrayList<>(ForgeRegistries.ITEMS.getValues()))));
    }

    public static SoundType parseSoundType(String value, String entry, String config, Predicate<SoundType> predicate) {
        SoundType soundtype = ParsingUtil.toSoundType(value);
        if (soundtype == null) {
            ConfigUtil.logInvalid(value, entry, config);
        }
        return soundtype;
    }

    public static String fromSoundType(SoundType s) {
        if (s == SoundType.WOOD) return "wood";
        if (s == SoundType.GRAVEL) return "gravel";
        if (s == SoundType.GRASS) return "grass";
        if (s == SoundType.LILY_PAD) return "lily_pad";
        if (s == SoundType.STONE) return "stone";
        if (s == SoundType.METAL) return "metal";
        if (s == SoundType.GLASS) return "glass";
        if (s == SoundType.WOOL) return "wool";
        if (s == SoundType.SAND) return "sand";
        if (s == SoundType.SNOW) return "snow";
        if (s == SoundType.LADDER) return "ladder";
        if (s == SoundType.ANVIL) return "anvil";
        if (s == SoundType.SLIME_BLOCK) return "slime_block";
        if (s == SoundType.HONEY_BLOCK) return "honey_block";
        if (s == SoundType.WET_GRASS) return "wet_grass";
        if (s == SoundType.CORAL_BLOCK) return "coral_block";
        if (s == SoundType.BAMBOO) return "bamboo";
        if (s == SoundType.BAMBOO_SAPLING) return "bamboo_sapling";
        if (s == SoundType.SCAFFOLDING) return "scaffolding";
        if (s == SoundType.SWEET_BERRY_BUSH) return "sweet_berry_bush";
        if (s == SoundType.CROP) return "crop";
        if (s == SoundType.HARD_CROP) return "hard_crop";
        if (s == SoundType.VINE) return "vine";
        if (s == SoundType.NETHER_WART) return "nether_wart";
        if (s == SoundType.LANTERN) return "lantern";
        if (s == SoundType.STEM) return "stem";
        if (s == SoundType.NYLIUM) return "nylium";
        if (s == SoundType.FUNGUS) return "fungus";
        if (s == SoundType.ROOTS) return "roots";
        if (s == SoundType.SHROOMLIGHT) return "shroomlight";
        if (s == SoundType.WEEPING_VINES) return "weeping_vines";
        if (s == SoundType.TWISTING_VINES) return "twisting_vines";
        if (s == SoundType.SOUL_SAND) return "soul_sand";
        if (s == SoundType.SOUL_SOIL) return "soul_soil";
        if (s == SoundType.BASALT) return "basalt";
        if (s == SoundType.WART_BLOCK) return "wart_block";
        if (s == SoundType.NETHERRACK) return "netherrack";
        if (s == SoundType.NETHER_BRICKS) return "nether_bricks";
        if (s == SoundType.NETHER_SPROUTS) return "nether_sprouts";
        if (s == SoundType.NETHER_ORE) return "nether_ore";
        if (s == SoundType.BONE_BLOCK) return "bone_block";
        if (s == SoundType.NETHERITE_BLOCK) return "netherite_block";
        if (s == SoundType.ANCIENT_DEBRIS) return "ancient_debris";
        if (s == SoundType.LODESTONE) return "lodestone";
        if (s == SoundType.CHAIN) return "chain";
        if (s == SoundType.NETHER_GOLD_ORE) return "nether_gold_ore";
        if (s == SoundType.GILDED_BLACKSTONE) return "gilded_blackstone";
        return null;
    }

    public static SoundType toSoundType(String s) {
        if (s.equals("wood")) return SoundType.WOOD;
        if (s.equals("gravel")) return SoundType.GRAVEL;
        if (s.equals("grass")) return SoundType.GRASS;
        if (s.equals("lily_pad")) return SoundType.LILY_PAD;
        if (s.equals("stone")) return SoundType.STONE;
        if (s.equals("metal")) return SoundType.METAL;
        if (s.equals("glass")) return SoundType.GLASS;
        if (s.equals("wool")) return SoundType.WOOL;
        if (s.equals("sand")) return SoundType.SAND;
        if (s.equals("snow")) return SoundType.SNOW;
        if (s.equals("ladder")) return SoundType.LADDER;
        if (s.equals("anvil")) return SoundType.ANVIL;
        if (s.equals("slime_block")) return SoundType.SLIME_BLOCK;
        if (s.equals("honey_block")) return SoundType.HONEY_BLOCK;
        if (s.equals("wet_grass")) return SoundType.WET_GRASS;
        if (s.equals("coral_block")) return SoundType.CORAL_BLOCK;
        if (s.equals("bamboo")) return SoundType.BAMBOO;
        if (s.equals("bamboo_sapling")) return SoundType.BAMBOO_SAPLING;
        if (s.equals("scaffolding")) return SoundType.SCAFFOLDING;
        if (s.equals("sweet_berry_bush")) return SoundType.SWEET_BERRY_BUSH;
        if (s.equals("crop")) return SoundType.CROP;
        if (s.equals("hard_crop")) return SoundType.HARD_CROP;
        if (s.equals("vine")) return SoundType.VINE;
        if (s.equals("nether_wart")) return SoundType.NETHER_WART;
        if (s.equals("lantern")) return SoundType.LANTERN;
        if (s.equals("stem")) return SoundType.STEM;
        if (s.equals("nylium")) return SoundType.NYLIUM;
        if (s.equals("fungus")) return SoundType.FUNGUS;
        if (s.equals("roots")) return SoundType.ROOTS;
        if (s.equals("shroomlight")) return SoundType.SHROOMLIGHT;
        if (s.equals("weeping_vines")) return SoundType.WEEPING_VINES;
        if (s.equals("twisting_vines")) return SoundType.TWISTING_VINES;
        if (s.equals("soul_sand")) return SoundType.SOUL_SAND;
        if (s.equals("soul_soil")) return SoundType.SOUL_SOIL;
        if (s.equals("basalt")) return SoundType.BASALT;
        if (s.equals("wart_block")) return SoundType.WART_BLOCK;
        if (s.equals("netherrack")) return SoundType.NETHERRACK;
        if (s.equals("nether_bricks")) return SoundType.NETHER_BRICKS;
        if (s.equals("nether_sprouts")) return SoundType.NETHER_SPROUTS;
        if (s.equals("nether_ore")) return SoundType.NETHER_ORE;
        if (s.equals("bone_block")) return SoundType.BONE_BLOCK;
        if (s.equals("netherite_block")) return SoundType.NETHERITE_BLOCK;
        if (s.equals("ancient_debris")) return SoundType.ANCIENT_DEBRIS;
        if (s.equals("lodestone")) return SoundType.LODESTONE;
        if (s.equals("chain")) return SoundType.CHAIN;
        if (s.equals("nether_gold_ore")) return SoundType.NETHER_GOLD_ORE;
        if (s.equals("gilded_blackstone")) return SoundType.GILDED_BLACKSTONE;
        return null;
    }
}
