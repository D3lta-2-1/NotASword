package fr.delta.notasword.mixin.fastAttack;

import fr.delta.notasword.NotASword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 10.0F, ordinal = 0))
    private float modifyHitCoolDown(float original, DamageSource source, float amount)
    {
        var gameSpace = GameSpaceManager.get().byWorld(((Entity)(Object)this).world);
        if(gameSpace != null && gameSpace.getBehavior().testRule(NotASword.FAST_ATTACK) == ActionResult.SUCCESS)
        {
            if (source.getAttacker() instanceof LivingEntity)
                return 11.0F;
            return 8.0F;
        }
        return original;
    }
}
