package fr.delta.notasword.mixin.oldKocback;

import fr.delta.notasword.NotASword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import xyz.nucleoid.plasmid.game.manager.GameSpaceManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", name = "i", at = @At(value = "LOAD", ordinal = 0), ordinal = 0)
    public int level(int i, Entity target)
    {
        var gameSpace = GameSpaceManager.get().byWorld(world);
        return gameSpace != null && gameSpace.getBehavior().testRule(NotASword.OLD_KNOCKBACK) == ActionResult.SUCCESS ? Integer.MIN_VALUE : 0;
    }
}
