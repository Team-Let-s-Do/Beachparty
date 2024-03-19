package satisfy.beachparty.item.armor;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.beachparty.client.ClientUtil;
import satisfy.beachparty.item.IBeachpartyArmorSet;
import satisfy.beachparty.registry.ArmorRegistry;

import java.util.List;

public class BeachpartyCustomArmorItem extends BetterCustomArmorModelItem implements IBeachpartyArmorSet {


    public BeachpartyCustomArmorItem(ArmorMaterial material, Type slot, Properties settings, ResourceLocation texture) {
        super(material, slot, settings, texture, -0.7f);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                checkForSet(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ArmorRegistry.appendTooltip(tooltip);
        }
    }
}
