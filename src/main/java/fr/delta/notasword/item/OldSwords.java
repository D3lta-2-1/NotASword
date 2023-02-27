package fr.delta.notasword.item;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import fr.delta.notasword.NotASword;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

public class OldSwords {
    public static SwordItem WOODEN_SWORD = new OldSword(ToolMaterials.WOOD, 3, new Item.Settings(), Items.WOODEN_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/wooden_sword")).value());
    public static SwordItem GOLDEN_SWORD = new OldSword(ToolMaterials.GOLD, 3, new Item.Settings(), Items.GOLDEN_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/golden_sword")).value());
    public static SwordItem STONE_SWORD = new OldSword(ToolMaterials.STONE, 3, new Item.Settings(), Items.STONE_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/stone_sword")).value());
    public static SwordItem IRON_SWORD = new OldSword(ToolMaterials.IRON, 3, new Item.Settings(), Items.IRON_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/iron_sword")).value());
    public static SwordItem DIAMOND_SWORD = new OldSword(ToolMaterials.DIAMOND, 3, new Item.Settings(), Items.DIAMOND_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/diamond_sword")).value());
    public static SwordItem NETHERITE_SWORD = new OldSword(ToolMaterials.NETHERITE, 3, new Item.Settings(), Items.NETHERITE_SWORD, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(NotASword.MOD_ID, "item/netherite_sword")).value());
}
