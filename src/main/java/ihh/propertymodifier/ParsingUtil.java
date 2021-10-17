package ihh.propertymodifier;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.ToolType;
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
            if (!predicate.test(result))
                ConfigUtil.logInvalid(value, entry, config);
            else return result;
        } catch (NumberFormatException e) {
            ConfigUtil.logInvalid(value, entry, config);
        }
        return null;
    }

    public static Float parseFloat(String value, String entry, String config, Predicate<Float> predicate) {
        try {
            float result = Float.parseFloat(value);
            if (!predicate.test(result))
                ConfigUtil.logInvalid(value, entry, config);
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

    public static ToolType parseToolType(String value, String entry, String config, Predicate<ToolType> predicate) {
        try {
            return ToolType.get(value);
        } catch (IllegalArgumentException x) {
            ConfigUtil.logInvalid(value, entry, config);
            return null;
        }
    }

    public static ItemGroup parseItemGroup(String value, String entry, String config, Predicate<ItemGroup> predicate) {
        ItemGroup itemgroup = null;
        for (ItemGroup i : ItemGroup.GROUPS) if (i.getPath().equals(value)) itemgroup = i;
        if (itemgroup == null)
            ConfigUtil.logInvalid(value, entry, config);
        return itemgroup;
    }

    public static LazyValue<Ingredient> parseRepairMaterial(String value, String entry, String config, Predicate<LazyValue<Ingredient>> predicate) {
        return new LazyValue<>(() -> value.startsWith("#") ? Ingredient.fromTag(ItemTags.makeWrapperTag(value.substring(1))) : Ingredient.fromItems(ConfigUtil.fromCollection(value, new ArrayList<>(ForgeRegistries.ITEMS.getValues()))));
    }

    public static SoundType parseSoundType(String value, String entry, String config, Predicate<SoundType> predicate) {
        SoundType soundtype = ParsingUtil.toSoundType(value);
        if (soundtype == null)
            ConfigUtil.logInvalid(value, entry, config);
        return soundtype;
    }

    public static String fromSoundType(SoundType s) {
        if (s == SoundType.WOOD) return "wood";
        if (s == SoundType.GROUND) return "ground";
        if (s == SoundType.PLANT) return "plant";
        if (s == SoundType.LILY_PADS) return "lily_pads";
        if (s == SoundType.STONE) return "stone";
        if (s == SoundType.METAL) return "metal";
        if (s == SoundType.GLASS) return "glass";
        if (s == SoundType.CLOTH) return "cloth";
        if (s == SoundType.SAND) return "sand";
        if (s == SoundType.SNOW) return "snow";
        if (s == SoundType.LADDER) return "ladder";
        if (s == SoundType.ANVIL) return "anvil";
        if (s == SoundType.SLIME) return "slime";
        if (s == SoundType.HONEY) return "honey";
        if (s == SoundType.WET_GRASS) return "wet_grass";
        if (s == SoundType.CORAL) return "coral";
        if (s == SoundType.BAMBOO) return "bamboo";
        if (s == SoundType.BAMBOO_SAPLING) return "bamboo_sapling";
        if (s == SoundType.SCAFFOLDING) return "scaffolding";
        if (s == SoundType.SWEET_BERRY_BUSH) return "sweet_berry_bush";
        if (s == SoundType.CROP) return "crop";
        if (s == SoundType.STEM) return "stem";
        if (s == SoundType.VINE) return "vine";
        if (s == SoundType.NETHER_WART) return "nether_wart";
        if (s == SoundType.LANTERN) return "lantern";
        if (s == SoundType.HYPHAE) return "hyphae";
        if (s == SoundType.NYLIUM) return "nylium";
        if (s == SoundType.FUNGUS) return "fungus";
        if (s == SoundType.ROOT) return "root";
        if (s == SoundType.SHROOMLIGHT) return "shroomlight";
        if (s == SoundType.NETHER_VINE) return "nether_vine";
        if (s == SoundType.NETHER_VINE_LOWER_PITCH) return "nether_vine_lower_pitch";
        if (s == SoundType.SOUL_SAND) return "soul_sand";
        if (s == SoundType.SOUL_SOIL) return "soul_soil";
        if (s == SoundType.BASALT) return "basalt";
        if (s == SoundType.WART) return "wart";
        if (s == SoundType.NETHERRACK) return "netherrack";
        if (s == SoundType.NETHER_BRICK) return "nether_brick";
        if (s == SoundType.NETHER_SPROUT) return "nether_sprout";
        if (s == SoundType.NETHER_ORE) return "nether_ore";
        if (s == SoundType.BONE) return "bone";
        if (s == SoundType.NETHERITE) return "netherite";
        if (s == SoundType.ANCIENT_DEBRIS) return "ancient_debris";
        if (s == SoundType.LODESTONE) return "lodestone";
        if (s == SoundType.CHAIN) return "chain";
        if (s == SoundType.NETHER_GOLD) return "nether_gold";
        if (s == SoundType.GILDED_BLACKSTONE) return "gilded_blackstone";
        return null;
    }

    public static SoundType toSoundType(String s) {
        if (s.equals("wood")) return SoundType.WOOD;
        if (s.equals("ground")) return SoundType.GROUND;
        if (s.equals("plant")) return SoundType.PLANT;
        if (s.equals("lily_pads")) return SoundType.LILY_PADS;
        if (s.equals("stone")) return SoundType.STONE;
        if (s.equals("metal")) return SoundType.METAL;
        if (s.equals("glass")) return SoundType.GLASS;
        if (s.equals("cloth")) return SoundType.CLOTH;
        if (s.equals("sand")) return SoundType.SAND;
        if (s.equals("snow")) return SoundType.SNOW;
        if (s.equals("ladder")) return SoundType.LADDER;
        if (s.equals("anvil")) return SoundType.ANVIL;
        if (s.equals("slime")) return SoundType.SLIME;
        if (s.equals("honey")) return SoundType.HONEY;
        if (s.equals("wet_grass")) return SoundType.WET_GRASS;
        if (s.equals("coral")) return SoundType.CORAL;
        if (s.equals("bamboo")) return SoundType.BAMBOO;
        if (s.equals("bamboo_sapling")) return SoundType.BAMBOO_SAPLING;
        if (s.equals("scaffolding")) return SoundType.SCAFFOLDING;
        if (s.equals("sweet_berry_bush")) return SoundType.SWEET_BERRY_BUSH;
        if (s.equals("crop")) return SoundType.CROP;
        if (s.equals("stem")) return SoundType.STEM;
        if (s.equals("vine")) return SoundType.VINE;
        if (s.equals("nether_wart")) return SoundType.NETHER_WART;
        if (s.equals("lantern")) return SoundType.LANTERN;
        if (s.equals("hyphae")) return SoundType.HYPHAE;
        if (s.equals("nylium")) return SoundType.NYLIUM;
        if (s.equals("fungus")) return SoundType.FUNGUS;
        if (s.equals("root")) return SoundType.ROOT;
        if (s.equals("shroomlight")) return SoundType.SHROOMLIGHT;
        if (s.equals("nether_vine")) return SoundType.NETHER_VINE;
        if (s.equals("nether_vine_lower_pitch")) return SoundType.NETHER_VINE_LOWER_PITCH;
        if (s.equals("soul_sand")) return SoundType.SOUL_SAND;
        if (s.equals("soul_soil")) return SoundType.SOUL_SOIL;
        if (s.equals("basalt")) return SoundType.BASALT;
        if (s.equals("wart")) return SoundType.WART;
        if (s.equals("netherrack")) return SoundType.NETHERRACK;
        if (s.equals("nether_brick")) return SoundType.NETHER_BRICK;
        if (s.equals("nether_sprout")) return SoundType.NETHER_SPROUT;
        if (s.equals("nether_ore")) return SoundType.NETHER_ORE;
        if (s.equals("bone")) return SoundType.BONE;
        if (s.equals("netherite")) return SoundType.NETHERITE;
        if (s.equals("ancient_debris")) return SoundType.ANCIENT_DEBRIS;
        if (s.equals("lodestone")) return SoundType.LODESTONE;
        if (s.equals("chain")) return SoundType.CHAIN;
        if (s.equals("nether_gold")) return SoundType.NETHER_GOLD;
        if (s.equals("gilded_blackstone")) return SoundType.GILDED_BLACKSTONE;
        return null;
    }
}
