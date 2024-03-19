package satisfy.beachparty.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import satisfy.beachparty.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;

public class SeashellItem extends BlockItem {
    public SeashellItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide) {
            if (user.isCrouching()) {
                return super.use(world, user, hand);
            } else {
                ItemStack itemStack = user.getItemInHand(hand);
                RandomSource random = world.random;
                int randomNumber = random.nextInt(100);
                ItemStack spawnedItem;

                if (randomNumber < 2) {
                    spawnedItem = new ItemStack(Items.DIAMOND);
                } else if (randomNumber < 7) {
                    spawnedItem = new ItemStack(Items.REDSTONE);
                } else if (randomNumber < 17) {
                    spawnedItem = new ItemStack(Items.EMERALD);
                } else if (randomNumber < 47) {
                    spawnedItem = new ItemStack(Items.GOLD_NUGGET);
                } else if (randomNumber < 97) {
                    spawnedItem = new ItemStack(Items.LAPIS_LAZULI);
                } else {
                    spawnedItem = new ItemStack(Items.FLINT);
                }

                ItemEntity entity = new ItemEntity(world, user.getX(), user.getY(), user.getZ(), spawnedItem);
                world.addFreshEntity(entity);

                ItemStack musselMeatStack = new ItemStack(ObjectRegistry.RAW_MUSSEL_MEAT.get(), 1);
                ItemEntity musselMeatEntity = new ItemEntity(world, user.getX(), user.getY(), user.getZ(), musselMeatStack);
                world.addFreshEntity(musselMeatEntity);

                if (!user.isCreative()) {
                    itemStack.shrink(1);
                }

                return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
            }
        }
        return InteractionResultHolder.pass(user.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("tooltip.beachparty.message").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
