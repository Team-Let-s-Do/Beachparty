package satisfy.beachparty.util;

import com.google.gson.JsonArray;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfy.beachparty.Beachparty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BeachpartyUtil {
	public static void registerColorArmor(Item item, int defaultColor) {
		ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> tintIndex > 0 ? -1 : getColor(stack, defaultColor), item);
	}

	private static int getColor(ItemStack itemStack, int defaultColor) {
		CompoundTag displayTag = itemStack.getTagElement("display");
		if (displayTag != null && displayTag.contains("color", Tag.TAG_ANY_NUMERIC))
			return displayTag.getInt("color");
		return defaultColor;
	}
	
	public static boolean matchesRecipe(Container inventory, NonNullList<Ingredient> recipe, int startIndex, int endIndex) {
		final List<ItemStack> validStacks = new ArrayList<>();
		for (int i = startIndex; i <= endIndex; i++) {
			final ItemStack stackInSlot = inventory.getItem(i);
			if (!stackInSlot.isEmpty())
				validStacks.add(stackInSlot);
		}
		for (Ingredient entry : recipe) {
			boolean matches = false;
			for (ItemStack item : validStacks) {
				if (entry.test(item)) {
					matches = true;
					validStacks.remove(item);
					break;
				}
			}
			if (!matches) {
				return false;
			}
		}
		return true;
	}

	public static FriendlyByteBuf createPacketBuf(){
		return new FriendlyByteBuf(Unpooled.buffer());
	}
	
	public static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (int i = 0; i < json.size(); i++) {
			Ingredient ingredient = Ingredient.fromJson(json.get(i));
			if (!ingredient.isEmpty()) {
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}

	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
		
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1],
					Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX),
					BooleanOp.OR
			                                                                                            ));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}
		return buffer[0];
	}
}