package fr.delta.testmod;


import fr.delta.notasword.NotASword;
import fr.delta.notasword.OldAttackSpeed;
import fr.delta.notasword.item.OldSwords;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.map_templates.MapTemplate;
import xyz.nucleoid.plasmid.game.GameActivity;
import xyz.nucleoid.plasmid.game.GameOpenContext;
import xyz.nucleoid.plasmid.game.GameOpenProcedure;
import xyz.nucleoid.plasmid.game.event.GamePlayerEvents;
import xyz.nucleoid.plasmid.game.rule.GameRuleType;
import xyz.nucleoid.plasmid.game.world.generator.TemplateChunkGenerator;
import xyz.nucleoid.plasmid.util.Scheduler;

public class SimpleGame implements AutoCloseable {
    private boolean isRunning = true;
    public static GameOpenProcedure open(GameOpenContext<SimpleConfig> context)
    {
        MapTemplate template = MapTemplate.createEmpty();
        for(int x = -16; x <= 16; x++)
        {
            for(int z = -16; z <= 16; z++)
            {
                template.setBlockState(new BlockPos(x, 64, z), Blocks.STONE.getDefaultState());
            }
        }

        // create a chunk generator that will generate from this template that we just created
        TemplateChunkGenerator generator = new TemplateChunkGenerator(context.server(), template);

        // set up how the world that this minigame will take place in should be constructed
        RuntimeWorldConfig worldConfig = new RuntimeWorldConfig()
                .setGenerator(generator)
                .setTimeOfDay(6000);

        return context.openWithWorld(worldConfig, (activity, world) ->  new SimpleGame(world, context.config(), activity));
    }

    SimpleGame(ServerWorld world, SimpleConfig config, GameActivity activity)
    {
        activity.listen(GamePlayerEvents.OFFER, offer -> offer.accept(world, new Vec3d(0.5, 65, 0.5))
                .and(() ->{
                    var player = offer.player();
                    player.getInventory().offerOrDrop(OldSwords.DIAMOND_SWORD.getDefaultStack());
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, -1, 255, false, false, false));
                }));


        activity.allow(NotASword.FAST_ATTACK);
        activity.allow(NotASword.OLD_KNOCKBACK);
        activity.deny(NotASword.ATTACK_SOUND);
        activity.deny(NotASword.SWEEPING_EDGE);
        OldAttackSpeed.add(20D, activity);

        activity.deny(GameRuleType.HUNGER);
        activity.deny(GameRuleType.FALL_DAMAGE);
        activity.deny(GameRuleType.PORTALS);
        activity.addResource(this);

        if(config.zombieSpawn()) {
            Scheduler.INSTANCE.repeatWhile((server) -> {
                var entity = new HuskEntity(EntityType.HUSK, world);
                entity.setPosition(0, 65, 0);
                world.spawnEntity(entity);
            }, this::isRunning, 50, 100);
        }
    }

    private boolean isRunning(int i) {
        return isRunning;
    }


    @Override
    public void close()  {
        isRunning = false;
    }
}
