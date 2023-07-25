package satisfyu.beachparty.item.armor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.beachparty.client.ClientUtil;
import satisfyu.beachparty.item.IBeachpartyArmorSet;

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

            /*
            BlockState blockState2 = world.getBlockState(entity.getOnPos().above());
            if (blockState2.isAir()) {
                entity.onAboveBubbleCol(blockState.getValue(DRAG_DOWN));
                if (!level.isClientSide) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    for (int i = 0; i < 2; ++i) {
                        serverLevel.sendParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + level.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + level.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                        serverLevel.sendParticles(ParticleTypes.BUBBLE, (double)blockPos.getX() + level.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + level.random.nextDouble(), 1, 0.0, 0.01, 0.0, 0.2);
                    }
                }
            } else {
                entity.onInsideBubbleColumn(blockState.getValue(DRAG_DOWN));
            }

             */

        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ClientUtil.appendTooltip(tooltip);
        }
    }
}
