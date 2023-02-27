package fr.delta.notasword.enumAdder;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;

public class UseActionEnumAdder implements Runnable {

    /**
     * Using the Fabric-ASM early riser to add BLOCK_HIT mode to the UseAction enum
     */
    @Override
    public void run() {
        String actionUse = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_1839");
        ClassTinkerers.enumBuilder(actionUse).addEnum("BLOCK_HIT").build();
    }
}
