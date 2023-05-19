package fr.delta.notasword;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nucleoid.plasmid.game.GameActivity;
import xyz.nucleoid.plasmid.game.event.GamePlayerEvents;

import java.util.Objects;

public class OldAttackSpeed {

    public static void add(Double speed, GameActivity activity)
    {
        activity.listen(GamePlayerEvents.ADD, (player) -> onPlayerJoin(player, speed));
        activity.listen(GamePlayerEvents.LEAVE, OldAttackSpeed::onPlayerLeave);
        activity.listen(GamePlayerEvents.REMOVE, OldAttackSpeed::onPlayerLeave);
    }

    static private void onPlayerJoin(ServerPlayerEntity player, double speed)
    {
        EntityAttributeInstance attackSpeedInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (attackSpeedInstance != null) {
            attackSpeedInstance.setBaseValue(speed);
        }
    }

    static private void onPlayerLeave(ServerPlayerEntity player)
    {
        if (player.getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_SPEED) && player.getAttributes().getBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED) == 20D) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED)).setBaseValue(4D);
        }
    }
}
