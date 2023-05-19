package fr.delta.notasword;
import com.chocohead.mm.api.ClassTinkerers;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import fr.delta.notasword.item.OldSwords;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.UseAction;
import xyz.nucleoid.plasmid.game.rule.GameRuleType;

public class NotASword implements ModInitializer {
    public static String MOD_ID = "not_a_sword";
    public static UseAction BLOCk_HIT_ACTION;
    public static final GameRuleType ATTACK_SOUND = GameRuleType.create();
    public static final GameRuleType OLD_KNOCKBACK = GameRuleType.create();
    public static final GameRuleType FAST_ATTACK = GameRuleType.create();
    public static final GameRuleType SWEEPING_EDGE = GameRuleType.create();
    @Override
    public void onInitialize() {
        BLOCk_HIT_ACTION = ClassTinkerers.getEnum(UseAction.class, "BLOCK_HIT");
        PolymerResourcePackUtils.addModAssets(MOD_ID);

        OldSwords.register();
    }
}
