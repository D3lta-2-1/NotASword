package fr.delta.notasword.mixin.oldKocback;

import fr.delta.notasword.NotASword;
import fr.delta.notasword.mixinInterface.BlockHitEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin{
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    public void handleTakeKnockback(LivingEntity livingEntity, double speed, double xMovement, double zMovement)
    {
        var playerEntity = (PlayerEntity)(Object)this;
        var blockHitEntity = (BlockHitEntity)(Object)this;
        var gameSpace = GameSpaceManager.get().byWorld(playerEntity.world);

        if (gameSpace != null && gameSpace.getBehavior().testRule(NotASword.OLD_KNOCKBACK) == ActionResult.SUCCESS && !blockHitEntity.isBlockHit()) {
            speed = (float) ((double)speed * (1.0D - livingEntity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).getValue()));
            livingEntity.addVelocity(-(xMovement * speed), 0.1D, -(zMovement * speed));
        }
        else
        {
            livingEntity.takeKnockback(speed, xMovement, zMovement);
        }

    }
}
