package fr.delta.notasword.mixin.fastAttack;

import fr.delta.notasword.NotASword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyConstant(method = "damage", constant = @Constant(intValue = 20, ordinal = 0))
    private int modifyHitCoolDown(int original, DamageSource source, float amount)
    {
        var gameSpace = GameSpaceManager.get().byWorld((this).world);
        if(gameSpace != null && gameSpace.getBehavior().testRule(NotASword.FAST_ATTACK) == ActionResult.SUCCESS)
        {
            if (source.getAttacker() instanceof LivingEntity)
                return original - 2; // 0.9s
        }
        return original;
    }
}
