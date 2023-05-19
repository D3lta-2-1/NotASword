package fr.delta.notasword.item;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static fr.delta.notasword.NotASword.MOD_ID;

public class OldSwords {
    public static SwordItem WOODEN_SWORD = create(Items.WOODEN_SWORD, "item/wooden_sword");
    public static SwordItem GOLDEN_SWORD = create(Items.GOLDEN_SWORD, "item/golden_sword");
    public static SwordItem STONE_SWORD = create(Items.STONE_SWORD, "item/stone_sword");
    public static SwordItem IRON_SWORD = create(Items.IRON_SWORD, "item/iron_sword");
    public static SwordItem DIAMOND_SWORD = create(Items.DIAMOND_SWORD, "item/diamond_sword");
    public static SwordItem NETHERITE_SWORD = create(Items.NETHERITE_SWORD, "item/netherite_sword");


    private static SwordItem create(Item item, String modelPath)
    {
        assert item instanceof SwordItem;
        var toolMaterial = ((SwordItem) item).getMaterial();
        return new OldSwordItem(toolMaterial, 3, new Item.Settings(), item, PolymerResourcePackUtils.requestModel(Items.SHIELD, new Identifier(MOD_ID, modelPath)).value());
    }

    public static final ItemGroup ITEM_GROUP = PolymerItemGroupUtils.builder(new Identifier(MOD_ID, "old_swords"))
            .icon(DIAMOND_SWORD::getDefaultStack)
            .displayName(Text.translatable("category.not_a_sword.title"))
            .build();

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
