package satisfy.beachparty.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfy.beachparty.Beachparty;
import satisfy.beachparty.BeachpartyIdentifier;
import satisfy.beachparty.entity.CoconutEntity;

import java.util.function.Supplier;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Beachparty.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CoconutEntity>> COCONUT = create("coconut", () -> EntityType.Builder.<CoconutEntity>of(CoconutEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new BeachpartyIdentifier("coconut").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new BeachpartyIdentifier(path), type);
    }

    public static void init(){
        Beachparty.LOGGER.debug("Registering Mod Entities for " + Beachparty.MOD_ID);
        ENTITY_TYPES.register();
    }
}
