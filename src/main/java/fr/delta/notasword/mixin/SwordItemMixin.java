package fr.delta.notasword.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fr.delta.notasword.item.OldSwordItem;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMultimap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMultimap$Builder;", ordinal = 1, remap = false))
    public ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> cancelAttackSpeedModifier(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder, Object key, Object value, Operation<ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier>> original)
    {
        if((Object)this instanceof OldSwordItem)
            return null;
        else
            return original.call(builder, key, value);
    }
}
