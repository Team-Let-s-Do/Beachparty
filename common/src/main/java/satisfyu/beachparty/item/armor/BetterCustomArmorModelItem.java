package satisfyu.beachparty.item.armor;

import de.cristelknight.doapi.common.item.CustomHatItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.beachparty.client.ClientUtil;

import java.util.List;

public class BetterCustomArmorModelItem extends CustomHatItem {

    private ResourceLocation textureLocation;

    private float offset;

    public BetterCustomArmorModelItem(ArmorMaterial material, Type slot, Properties settings, ResourceLocation textureLocation, float offset){
        super(material, slot, settings);
        this.textureLocation = textureLocation;
        this.offset = offset;
    }
    @Override
    public ResourceLocation getTexture() {
        return textureLocation;
    }

    @Override
    public Float getOffset() {
        return offset;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ClientUtil.appendTooltip(tooltip);
        }
    }
}
