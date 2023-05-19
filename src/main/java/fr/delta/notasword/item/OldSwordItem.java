package fr.delta.notasword.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import fr.delta.notasword.NotASword;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
public class OldSwordItem extends SwordItem implements PolymerItem {

    final Item vanillaItem;
    final int customModelData;

    public OldSwordItem(ToolMaterial toolMaterial, int attackDamage, Settings settings, Item vanillaItem, int customModelData) {
        super(toolMaterial, attackDamage, -1, settings);
        this.vanillaItem = vanillaItem;
        this.customModelData = customModelData;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if(player == null) return vanillaItem;
        //if(!PolymerResourcePackUtils.hasPack(player)) return vanillaItem;
        return Items.SHIELD;
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return customModelData;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        //block mechanics from the shield
        //return UseAction.BLOCK;
        return NotASword.BLOCk_HIT_ACTION;
    }
}
