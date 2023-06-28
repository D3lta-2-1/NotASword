package fr.delta.notasword.mixin.sweepingEdge;

import fr.delta.notasword.NotASword;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z", ordinal = 1))
    boolean disableSweepAnimation(PlayerEntity instance)
    {
        var gameSpace = GameSpaceManager.get().byWorld(this.getWorld());
        if(gameSpace == null) return instance.isOnGround();
        return (gameSpace.getBehavior().testRule(NotASword.SWEEPING_EDGE) != ActionResult.FAIL) && instance.isOnGround(); // returning always false will prevent the swiping edge evaluation and disable the sweep attack
    }
}
