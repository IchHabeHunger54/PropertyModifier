package ihh.propertymodifier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.Lazy;

public final class Properties {
    public static class Block {
        public Float DESTROY_TIME = null;
        public Float EXPLOSION_RESISTANCE = null;
        public Boolean REQUIRES_TOOL = null;
        public Integer LIGHT_EMISSION = null;
        public Float FRICTION = null;
        public Float SPEED_FACTOR = null;
        public Float JUMP_FACTOR = null;
        public SoundType SOUND_TYPE = null;
    }

    public static class Item {
        public Integer MAX_DAMAGE = null;
        public Integer MAX_STACK_SIZE = null;
        public CreativeModeTab GROUP = null;
        public Boolean IS_FIRE_RESISTANT = null;
        public Rarity RARITY = null;
        public Integer ENCHANTMENT_VALUE = null;
        public Lazy<Ingredient> REPAIR_MATERIAL = null;
    }

    public static class Armor extends Item {
        public Integer ARMOR = null;
        public Float TOUGHNESS = null;
        public Float KNOCKBACK_RESISTANCE = null;
    }

    public static class Tool extends Item {
        public Float ATTACK_DAMAGE = null;
        public Float ATTACK_SPEED = null;
        public Float EFFICIENCY = null;
    }

    public static class Enchantment {
        public net.minecraft.world.item.enchantment.Enchantment.Rarity RARITY = null;
    }
}
