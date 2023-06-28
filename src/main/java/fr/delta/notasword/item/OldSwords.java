package fr.delta.notasword.item;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static fr.delta.notasword.NotASword.MOD_ID;

public class OldSwords {
    public static SwordItem WOODEN_SWORD = create(Items.WOODEN_SWORD, new FabricItemSettings(), "item/wooden_sword");
    public static SwordItem GOLDEN_SWORD = create(Items.GOLDEN_SWORD, new FabricItemSettings(), "item/golden_sword");
    public static SwordItem STONE_SWORD = create(Items.STONE_SWORD, new FabricItemSettings(), "item/stone_sword");
    public static SwordItem IRON_SWORD = create(Items.IRON_SWORD, new FabricItemSettings(), "item/iron_sword");
    public static SwordItem DIAMOND_SWORD = create(Items.DIAMOND_SWORD, new FabricItemSettings(), "item/diamond_sword");
    public static SwordItem NETHERITE_SWORD = create(Items.NETHERITE_SWORD, new FabricItemSettings().fireproof(), "item/netherite_sword");

    private static SwordItem create(Item item, Item.Settings settings, String modelPath)
    {
        assert item instanceof SwordItem;
        var toolMaterial = ((SwordItem) item).getMaterial();
        return new OldSwordItem(toolMaterial, 3, settings, item, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(MOD_ID, modelPath)).value());
    }

    private static void registerItem(String name, Item item)
    {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void register()
    {
        registerItem("wooden_sword", WOODEN_SWORD);
        registerItem("golden_sword", GOLDEN_SWORD);
        registerItem("stone_sword", STONE_SWORD);
        registerItem("iron_sword", IRON_SWORD);
        registerItem("diamond_sword", DIAMOND_SWORD);
        registerItem("netherite_sword", NETHERITE_SWORD);

        PolymerItemGroupUtils.registerPolymerItemGroup(new Identifier(MOD_ID, "old_swords"), FabricItemGroup.builder()
                .icon(DIAMOND_SWORD::getDefaultStack)
                .displayName(Text.translatable("category.not_a_sword.title"))
                .entries((ctx, entries) -> {
                    entries.add(WOODEN_SWORD);
                    entries.add(GOLDEN_SWORD);
                    entries.add(STONE_SWORD);
                    entries.add(IRON_SWORD);
                    entries.add(DIAMOND_SWORD);
                    entries.add(NETHERITE_SWORD);
                })
                .build());
    }
}
