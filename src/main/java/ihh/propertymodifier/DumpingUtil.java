package ihh.propertymodifier;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.block.Block;

public final class DumpingUtil {
    public static void append(StringBuilder builder, String name, Object value) {
        builder.append(name).append(": ").append(value).append(", ");
    }

    public static void append(StringBuilder builder, String name, int value) {
        builder.append(name).append(": ").append(value).append(", ");
    }

    public static void append(StringBuilder builder, String name, float value) {
        builder.append(name).append(": ").append(value).append(", ");
    }

    public static void append(StringBuilder builder, String name, boolean value) {
        builder.append(name).append(": ").append(value).append(", ");
    }

    public static void appendHardness(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float hardness = block.properties.destroyTime;
        if (!onlyIfNonDefault || hardness != 0) {
            append(builder, "hardness", hardness);
        }
    }

    public static void appendResistance(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float resistance = block.getExplosionResistance();
        if (!onlyIfNonDefault || resistance != 0) {
            append(builder, "resistance", resistance);
        }
    }

    public static void appendRequiresTool(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        boolean requiresTool = block.properties.requiresCorrectToolForDrops;
        if (!onlyIfNonDefault || requiresTool) {
            append(builder, "requires tool", requiresTool);
        }
    }

    public static void appendLightLevel(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        int lightLevel = block.properties.lightEmission.applyAsInt(block.defaultBlockState());
        if (!onlyIfNonDefault || lightLevel != 0) {
            append(builder, "light level", lightLevel);
        }
    }

    public static void appendSlipperiness(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float slipperiness = block.getFriction();
        if (!onlyIfNonDefault || slipperiness != 0.6f) {
            append(builder, "slipperiness", slipperiness);
        }
    }

    public static void appendSpeedFactor(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float speedFactor = block.getSpeedFactor();
        if (!onlyIfNonDefault || speedFactor != 1) {
            append(builder, "speed factor", speedFactor);
        }
    }

    public static void appendJumpFactor(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float jumpFactor = block.getJumpFactor();
        if (!onlyIfNonDefault || jumpFactor != 1) {
            append(builder, "jump factor", jumpFactor);
        }
    }

    public static void appendSoundType(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        String soundType = ParsingUtil.fromSoundType(block.getSoundType(block.defaultBlockState()));
        if (!onlyIfNonDefault || soundType != null && !soundType.equals("stone")) {
            append(builder, "sound type", soundType);
        }
    }

    public static void appendMaxDamage(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int maxDamage = item.getMaxDamage();
        if (!onlyIfNonDefault || maxDamage != 0) {
            append(builder, "max damage", maxDamage);
        }
    }

    public static void appendMaxStackSize(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int maxStackSize = item.getMaxStackSize();
        if (!onlyIfNonDefault || maxStackSize != 64) {
            append(builder, "max stack size", maxStackSize);
        }
    }

    public static void appendCreativeModeTab(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        String CreativeModeTab = item.category == null ? "" : item.category.getRecipeFolderName();
        if (!onlyIfNonDefault || !CreativeModeTab.equals("")) {
            append(builder, "item group", CreativeModeTab);
        }
    }

    public static void appendIsImmuneToFire(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        boolean isImmuneToFire = item.isFireResistant();
        if (!onlyIfNonDefault || isImmuneToFire) {
            append(builder, "is immune to fire", isImmuneToFire);
        }
    }

    public static void appendRarity(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        String rarity = item.rarity.toString().toLowerCase();
        if (!onlyIfNonDefault || !rarity.equals("common")) {
            append(builder, "rarity", rarity);
        }
    }

    public static void appendEnchantmentValue(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int enchantmentValue = item.getEnchantmentValue();
        if (!onlyIfNonDefault || enchantmentValue != Config.DEFAULT_ENCHANTMENT_VALUE.get()) {
            append(builder, "enchantmentValue", enchantmentValue);
        }
    }

    public static void appendArmor(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        int armor = item.getDefense();
        if (!onlyIfNonDefault || armor != 0) {
            append(builder, "armor", armor);
        }
    }

    public static void appendToughness(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        float toughness = item.getToughness();
        if (!onlyIfNonDefault || toughness != 0) {
            append(builder, "toughness", toughness);
        }
    }

    public static void appendKnockbackResistance(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        float knockbackResistance = item.knockbackResistance;
        if (!onlyIfNonDefault || knockbackResistance != 0) {
            append(builder, "knockback resistance", knockbackResistance);
        }
    }

    public static void appendEfficiency(StringBuilder builder, DiggerItem item, boolean onlyIfNonDefault) {
        float efficiency = item.speed;
        if (!onlyIfNonDefault || efficiency != 0) {
            append(builder, "efficiency", efficiency + 1f);
        }
    }

    public static void appendDamage(StringBuilder builder, DiggerItem item, boolean onlyIfNonDefault) {
        float damage = item.getAttackDamage();
        if (!onlyIfNonDefault || damage != 0) {
            append(builder, "damage", damage + 1f);
        }
    }

    public static void appendSpeed(StringBuilder builder, DiggerItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) {
            append(builder, "speed", speed + 4f);
        }
    }

    public static void appendDamage(StringBuilder builder, SwordItem item, boolean onlyIfNonDefault) {
        float damage = item.getDamage();
        if (!onlyIfNonDefault || damage != 0) {
            append(builder, "damage", damage + 1f);
        }
    }

    public static void appendSpeed(StringBuilder builder, SwordItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) {
            append(builder, "speed", speed + 4f);
        }
    }

    public static void appendDamage(StringBuilder builder, TridentItem item, boolean onlyIfNonDefault) {
        AttributeModifier damageMod = item.defaultModifiers.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
        float damage = damageMod != null ? (float) damageMod.getAmount() : 0;
        if (!onlyIfNonDefault || damage != 0) {
            append(builder, "damage", damage + 1f);
        }
    }

    public static void appendSpeed(StringBuilder builder, TridentItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) {
            append(builder, "speed", speed + 4f);
        }
    }
}
