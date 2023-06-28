package fr.delta.notasword.item;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
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

    static final Identifier GROUP_ID = new Identifier(MOD_ID, "old_swords");

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, GROUP_ID);


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

        PolymerItemGroupUtils.registerPolymerItemGroup(GROUP_ID, FabricItemGroup.builder()
                .icon(DIAMOND_SWORD::getDefaultStack)
                .displayName(Text.translatable("category.not_a_sword.title"))
                .build());

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
            content.add(WOODEN_SWORD);
            content.add(GOLDEN_SWORD);
            content.add(STONE_SWORD);
            content.add(IRON_SWORD);
            content.add(DIAMOND_SWORD);
            content.add(NETHERITE_SWORD);
        });
    }
}
