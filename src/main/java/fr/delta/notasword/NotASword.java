package fr.delta.notasword;
import com.chocohead.mm.api.ClassTinkerers;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import fr.delta.notasword.item.OldSwords;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import xyz.nucleoid.plasmid.game.rule.GameRuleType;

public class NotASword implements ModInitializer {
    public static String MOD_ID = "not_a_sword";
    public static UseAction BLOCk_HIT_ACTION;
    public static final GameRuleType ATTACK_SOUND = GameRuleType.create();
    public static final GameRuleType OLD_KNOCKBACK = GameRuleType.create();
    @Override
    public void onInitialize() {
        BLOCk_HIT_ACTION = ClassTinkerers.getEnum(UseAction.class, "BLOCK_HIT");
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "wooden_sword"), OldSwords.WOODEN_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "golden_sword"), OldSwords.GOLDEN_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "stone_sword"), OldSwords.STONE_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "iron_sword"), OldSwords.IRON_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "diamond_sword"), OldSwords.DIAMOND_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "netherite_sword"), OldSwords.NETHERITE_SWORD);
    }
}
