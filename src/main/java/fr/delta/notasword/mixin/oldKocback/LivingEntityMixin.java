package fr.delta.notasword.mixin.oldKocback;

import fr.delta.notasword.NotASword;
import fr.delta.notasword.mixinInterface.BlockHitEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow
    public abstract double getAttributeValue(EntityAttribute attribute);

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    void takeKnockback(LivingEntity entity, double strength_, double x_, double y_, DamageSource source, float amount)
    {
        var attacker = source.getAttacker();
        var gameSpace = GameSpaceManager.get().byWorld(world);
        var livingAttacker = attacker instanceof LivingEntity ? (LivingEntity)attacker : null;
        if((source.isOf(DamageTypes.PLAYER_ATTACK) || source.isOf(DamageTypes.MOB_ATTACK)) && livingAttacker != null && gameSpace != null && gameSpace.getBehavior().testRule(NotASword.OLD_KNOCKBACK) == ActionResult.SUCCESS)
        {
            var knockback = EnchantmentHelper.getKnockback(livingAttacker);
            var yaw = livingAttacker.getYaw();
            var pitch = -livingAttacker.getPitch();
            pitch = pitch < 30 && this.isOnGround() ? 30 : pitch; //if the player is on the ground, the pitch is set to 35

            var vector = getVector(pitch, yaw);

            double strength = 0.7 //base strength
                    + knockback / 3D //+ 0.33 per level of knockback
                    + amount * 0.01; //+ 0.01 per damage point

            var velocity = livingAttacker.getVelocity().length() * 0.6;
            strength += velocity > 1 ? 1 : velocity; //typical sprinting speed is 0.04

            attacker.setSprinting(false);

            if(((BlockHitEntity)this).isBlockHit())
                strength *= 0.75;

            vector = vector.multiply(strength * (1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE))); //strength of the hit

            /*attacker.sendMessage(Text.literal("speed : " + attacker.getVelocity().length()).formatted(Formatting.AQUA));
            attacker.sendMessage(Text.literal("knockback : " + strength).formatted(Formatting.GREEN));
            this.sendMessage(Text.literal("knockback : " + strength).formatted(Formatting.RED));*/

            vector = vector.multiply(1, 0.8, 1);

            this.velocityDirty = true;
            this.addVelocity(vector);
        }
        else
        {
            entity.takeKnockback(strength_, x_, y_);
        }
    }

    private static Vec3d getVector(float pitch, float yaw)
    {
        double pitchRad = Math.toRadians(pitch);
        double yawRad = Math.toRadians(yaw);

        double horizontal = -Math.cos(pitchRad);
        return new Vec3d(
                Math.sin(yawRad) * horizontal,
                Math.sin(pitchRad),
                -Math.cos(yawRad) * horizontal
        );
    }

}
