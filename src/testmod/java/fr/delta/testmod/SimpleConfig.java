package fr.delta.testmod;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SimpleConfig(boolean zombieSpawn) {
    static public Codec<SimpleConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.BOOL.optionalFieldOf("spawnZombies", true).forGetter(SimpleConfig::zombieSpawn)
    ).apply(instance, SimpleConfig::new));
}
