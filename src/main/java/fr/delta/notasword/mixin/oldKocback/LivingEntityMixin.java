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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
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
        var gameSpace = GameSpaceManager.get().byWorld(this.getWorld());
        var livingAttacker = attacker instanceof LivingEntity ? (LivingEntity)attacker : null;
        if((source.isOf(DamageTypes.PLAYER_ATTACK) || source.isOf(DamageTypes.MOB_ATTACK)) && livingAttacker != null && gameSpace != null && gameSpace.getBehavior().testRule(NotASword.OLD_KNOCKBACK) == ActionResult.SUCCESS)
        {
            //get data
            var knockback = EnchantmentHelper.getKnockback(livingAttacker);
            var yaw = livingAttacker.getYaw();
            double pitch = -livingAttacker.getPitch();

            //bound the pitch
            //pitch = pitch > 0 ? Math.sqrt(pitch/90)* 0.8 * pitch : pitch; //pass it to a square root function if above 0 to make it more realistic
            pitch = pitch < 30 && this.isOnGround() ? 25 : pitch; //if the receiver is on the ground, the pitch is set to 35

            double strength = 0.3 //base strength
                    + (3 - distanceTo(attacker)) * 0.4 //closer the attacker is, the stronger the hit, max 1.5
                    + knockback * 0.2//+ 0.2 per level of knockback
                    + amount * 0.02; //+ 0.02 per damage point

            //use the velocity of the attacker to increase the strength of the hit
            var velocity = livingAttacker.getVelocity().length() * 0.5;
            strength += Math.min(velocity, 1); //restrict it to 1 max

            attacker.setSprinting(false);

            //todo: add some randomness to the yaw
            var vector = getUnitVector(pitch, yaw);
            vector = vector.multiply(strength * (1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE))); //strength of the hit

            if(((BlockHitEntity)this).isBlockHit())
                vector.multiply(0.9); //if the player is parrying, the knockback is reduced by 10%

            attacker.sendMessage(Text.literal("speed : " + attacker.getVelocity().length()).formatted(Formatting.AQUA));
            attacker.sendMessage(Text.literal("knockback : " + strength).formatted(Formatting.GREEN));
            this.sendMessage(Text.literal("knockback : " + strength).formatted(Formatting.RED));

            this.velocityDirty = true;
            this.addVelocity(vector);
        }
        else
        {
            entity.takeKnockback(strength_, x_, y_);
        }
    }

    private static Vec3d getUnitVector(double pitch, double yaw)
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
