package satisfy.beachparty.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class CompostablesRegistry {
    
    public static void init(){
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DRY_BUSH.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DRY_BUSH_TALL.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PALM_SAPLING.get().asItem(), 0.6F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PALM_LEAVES.get().asItem(), 0.6F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BEACH_GRASS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCONUT.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCONUT_OPEN.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COOKED_MUSSEL_MEAT.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.RAW_MUSSEL_MEAT.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ICECREAM_MELON.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ICECREAM_CACTUS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ICECREAM_CHOCOLATE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ICECREAM_COCONUT.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ICECREAM_SWEETBERRIES.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHOCOLATE_ICECREAM.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCONUT_ICECREAM.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SWEETBERRY_ICECREAM.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.REFRESHING_DRINK.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHOCOLATE_MILKSHAKE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SWEETBERRY_MILKSHAKE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCONUT_MILKSHAKE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCONUT_COCKTAIL.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SWEETBERRIES_COCKTAIL.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.COCOA_COCKTAIL.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.PUMPKIN_COCKTAIL.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MELON_COCKTAIL.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HONEY_COCKTAIL.get().asItem(), 0.3F);
    }
}
