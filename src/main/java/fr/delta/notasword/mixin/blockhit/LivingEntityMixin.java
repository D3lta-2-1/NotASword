package fr.delta.notasword.mixin.blockhit;


import fr.delta.notasword.NotASword;
import fr.delta.notasword.mixinInterface.BlockHitEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements BlockHitEntity
{
    @Shadow
    public abstract boolean isUsingItem();
    @Shadow
    protected ItemStack activeItemStack;

    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    void applyDamage(Args args)
    {
        DamageSource source = args.get(0);
        float amount = args.get(1);
        if(blockedByBlockHit(source) && isBlockHit())
        {
            amount *= 0.5;
        }
        args.set(1, amount);
    }

    boolean blockedByBlockHit(DamageSource source)
    {
        return !source.bypassesArmor();
    }

    public boolean isBlockHit()
    {
        if (!this.isUsingItem() || this.activeItemStack.isEmpty()) return false;
        Item item = this.activeItemStack.getItem();
        return item.getUseAction(this.activeItemStack) == NotASword.BLOCk_HIT_ACTION;
    }
}
