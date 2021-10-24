package ihh.propertymodifier;

import net.minecraft.block.SoundType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.ToolType;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import java.util.HashSet;

@SuppressWarnings("unused")
public final class Properties {
    public static class Block {
        public Float HARDNESS = null;
        public Float RESISTANCE = null;
        public Integer HARVEST_LEVEL = null;
        public ToolType HARVEST_TOOL = null;
        public Boolean REQUIRES_TOOL = null;
        public Integer LIGHT_LEVEL = null;
        public Float SLIPPERINESS = null;
        public Float SPEED_FACTOR = null;
        public Float JUMP_FACTOR = null;
        public SoundType SOUND_TYPE = null;
    }

    public static class Item {
        public Integer MAX_DAMAGE = null;
        public Integer MAX_STACK_SIZE = null;
        public ItemGroup GROUP = null;
        public Boolean IS_IMMUNE_TO_FIRE = null;
        public Rarity RARITY = null;
        public Integer ENCHANTABILITY = null;
        public LazyValue<Ingredient> REPAIR_MATERIAL = null;
    }

    public static class Armor extends Item {
        public Integer ARMOR = null;
        public Float TOUGHNESS = null;
        public Float KNOCKBACK_RESISTANCE = null;
    }

    public static class Tool extends Item {
        public Float ATTACK_DAMAGE = null;
        public Float ATTACK_SPEED = null;
        public Integer HARVEST_LEVEL = null;
        public Float EFFICIENCY = null;
    }

    public static class Enchantment {
        public Integer MAX_LEVEL = null;
        public Pair<Integer, Integer> MIN_ENCHANTABILITY = null;
        public Triple<Integer, Integer, Boolean> MAX_ENCHANTABILITY = null;
        public Boolean IS_TREASURE = null;
        public Boolean CAN_VILLAGER_TRADE = null;
        public Boolean CAN_GENERATE_IN_LOOT = null;
        public net.minecraft.enchantment.Enchantment.Rarity RARITY = null;
        public HashSet<net.minecraft.enchantment.Enchantment> INCOMPATIBLES = null;
    }
}
