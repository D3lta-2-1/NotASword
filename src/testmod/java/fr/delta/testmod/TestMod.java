package fr.delta.testmod;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.util.Identifier;
import xyz.nucleoid.plasmid.game.GameType;

public class TestMod implements DedicatedServerModInitializer {

    public static String ID = "testmod";

    @Override
    public void onInitializeServer()
    {
        GameType.register(new Identifier(ID, ID), SimpleConfig.CODEC, SimpleGame::open);
    }
}
