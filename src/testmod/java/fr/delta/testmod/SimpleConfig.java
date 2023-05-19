package fr.delta.testmod;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SimpleConfig(String hello) {
    static public Codec<SimpleConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.optionalFieldOf("string", "default").forGetter(SimpleConfig::hello)
    ).apply(instance, SimpleConfig::new));
}
