package ihh.propertymodifier;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraftforge.common.ToolType;

import java.util.Collection;

@SuppressWarnings("deprecation")
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
        float hardness = block.properties.hardness;
        if (!onlyIfNonDefault || hardness != 0) append(builder, "hardness", hardness);
    }

    public static void appendResistance(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float resistance = block.getExplosionResistance();
        if (!onlyIfNonDefault || resistance != 0) append(builder, "resistance", resistance);
    }

    public static void appendHarvestLevel(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        int harvestLevel = block.getHarvestLevel(block.getDefaultState());
        if (!onlyIfNonDefault || harvestLevel != 0) append(builder, "harvest level", harvestLevel);
    }

    public static void appendHarvestTool(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        ToolType harvestTool = block.getHarvestTool(block.getDefaultState());
        if (!onlyIfNonDefault || harvestTool != null)
            append(builder, "harvest tool", harvestTool == null ? "none" : harvestTool.getName());
    }

    public static void appendRequiresTool(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        boolean requiresTool = block.properties.requiresTool;
        if (!onlyIfNonDefault || requiresTool) append(builder, "requires tool", requiresTool);
    }

    public static void appendLightLevel(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        int lightLevel = block.properties.lightLevel.applyAsInt(block.getDefaultState());
        if (!onlyIfNonDefault || lightLevel != 0) append(builder, "light level", lightLevel);
    }

    public static void appendSlipperiness(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float slipperiness = block.getSlipperiness();
        if (!onlyIfNonDefault || slipperiness != 0.6f) append(builder, "slipperiness", slipperiness);
    }

    public static void appendSpeedFactor(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float speedFactor = block.getSpeedFactor();
        if (!onlyIfNonDefault || speedFactor != 1) append(builder, "speed factor", speedFactor);
    }

    public static void appendJumpFactor(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        float jumpFactor = block.getJumpFactor();
        if (!onlyIfNonDefault || jumpFactor != 1) append(builder, "jump factor", jumpFactor);
    }

    public static void appendSoundType(StringBuilder builder, Block block, boolean onlyIfNonDefault) {
        String soundType = ParsingUtil.fromSoundType(block.getSoundType(block.getDefaultState()));
        if (!onlyIfNonDefault || soundType != null && !soundType.equals("stone"))
            append(builder, "sound type", soundType);
    }

    public static void appendMaxDamage(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int maxDamage = item.getMaxDamage();
        if (!onlyIfNonDefault || maxDamage != 0) append(builder, "max damage", maxDamage);
    }

    public static void appendMaxStackSize(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int maxStackSize = item.getMaxStackSize();
        if (!onlyIfNonDefault || maxStackSize != 64) append(builder, "max stack size", maxStackSize);
    }

    public static void appendItemGroup(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        String itemGroup = item.group == null ? "" : item.group.getPath();
        if (!onlyIfNonDefault || !itemGroup.equals("")) append(builder, "item group", itemGroup);
    }

    public static void appendIsImmuneToFire(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        boolean isImmuneToFire = item.isImmuneToFire();
        if (!onlyIfNonDefault || isImmuneToFire) append(builder, "is immune to fire", isImmuneToFire);
    }

    public static void appendRarity(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        String rarity = item.rarity.toString().toLowerCase();
        if (!onlyIfNonDefault || !rarity.equals("common")) append(builder, "rarity", rarity);
    }

    public static void appendEnchantability(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        int enchantability = item.getItemEnchantability();
        if (!onlyIfNonDefault || enchantability != Config.DEFAULT_ENCHANTABILITY.get())
            append(builder, "enchantability", enchantability);
    }

    public static void appendToolType(StringBuilder builder, Item item, boolean onlyIfNonDefault) {
        Collection<ToolType> types = item.getToolTypes(null);
        StringBuilder typeBuilder = new StringBuilder();
        if (!types.isEmpty()) for (ToolType type : types)
            typeBuilder.append(type.getName()).append(" (harvest level: ").append(item.getHarvestLevel(null, type, null, null)).append("), ");
        if (!onlyIfNonDefault && typeBuilder.length() > 0)
            append(builder, "tool types", typeBuilder.substring(0, typeBuilder.length() - 2));
    }

    public static void appendArmor(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        int armor = item.getDamageReduceAmount();
        if (!onlyIfNonDefault || armor != 0) append(builder, "armor", armor);
    }

    public static void appendToughness(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        float toughness = item.getToughness();
        if (!onlyIfNonDefault || toughness != 0) append(builder, "toughness", toughness);
    }

    public static void appendKnockbackResistance(StringBuilder builder, ArmorItem item, boolean onlyIfNonDefault) {
        float knockbackResistance = item.knockbackResistance;
        if (!onlyIfNonDefault || knockbackResistance != 0) append(builder, "knockback resistance", knockbackResistance);
    }

    public static void appendEfficiency(StringBuilder builder, ToolItem item, boolean onlyIfNonDefault) {
        float efficiency = item.efficiency;
        if (!onlyIfNonDefault || efficiency != 0) append(builder, "efficiency", efficiency + 1f);
    }

    public static void appendDamage(StringBuilder builder, ToolItem item, boolean onlyIfNonDefault) {
        float damage = item.getAttackDamage();
        if (!onlyIfNonDefault || damage != 0) append(builder, "damage", damage + 1f);
    }

    public static void appendSpeed(StringBuilder builder, ToolItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.toolAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) append(builder, "speed", speed + 4f);
    }

    public static void appendDamage(StringBuilder builder, SwordItem item, boolean onlyIfNonDefault) {
        float damage = item.getAttackDamage();
        if (!onlyIfNonDefault || damage != 0) append(builder, "damage", damage + 1f);
    }

    public static void appendSpeed(StringBuilder builder, SwordItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.attributeModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) append(builder, "speed", speed + 4f);
    }

    public static void appendDamage(StringBuilder builder, TridentItem item, boolean onlyIfNonDefault) {
        AttributeModifier damageMod = item.tridentAttributes.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
        float damage = damageMod != null ? (float) damageMod.getAmount() : 0;
        if (!onlyIfNonDefault || damage != 0) append(builder, "damage", damage + 1f);
    }

    public static void appendSpeed(StringBuilder builder, TridentItem item, boolean onlyIfNonDefault) {
        AttributeModifier speedMod = item.tridentAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
        float speed = speedMod != null ? (float) speedMod.getAmount() : 0;
        if (!onlyIfNonDefault || speed != 0) append(builder, "speed", speed + 4f);
    }
}
