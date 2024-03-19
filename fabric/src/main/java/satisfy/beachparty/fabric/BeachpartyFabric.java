package satisfy.beachparty.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import satisfy.beachparty.Beachparty;
import satisfy.beachparty.fabric.registry.VillagersFabric;
import satisfy.beachparty.fabric.world.BeachpartyBiomeModification;
import satisfy.beachparty.registry.CompostablesRegistry;

import java.util.Set;

public class BeachpartyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Beachparty.init();
        CompostablesRegistry.init();
        Beachparty.commonSetup();
        BeachpartyBiomeModification.init();
        VillagersFabric.init();
        registerLootTable();
    }





    protected static void registerLootTable() {
        Set<ResourceLocation> chestsId = Set.of(
                BuiltInLootTables.BURIED_TREASURE);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            ResourceLocation injectId = new ResourceLocation(Beachparty.MOD_ID, "inject/" + id.getPath());
            if (chestsId.contains(id)) {
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(injectId).setWeight(1).setQuality(0)).build());
            }
        });

    }

}
