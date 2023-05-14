package net.satisfyu.beachparty.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfyu.beachparty.networking.BeachpartyMessages;
import net.satisfyu.beachparty.registry.SoundEventRegistry;
import net.satisfyu.beachparty.util.BeachpartyUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RadioBlock extends Block {
    public static final BooleanProperty ON;
    public static final IntProperty CHANNEL;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty SEARCHING;
    public static final int CHANNELS = SoundEventRegistry.RADIO_SOUNDS.size();
    public static final int DELAY = 2 * 20;

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> VoxelShapes.cuboid(0.125, 0, 0.3125, 0.875, 0.5, 0.6875);

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
            map.put(direction, BeachpartyUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public RadioBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ON, false).with(CHANNEL, 0).with(SEARCHING, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        int channel = Random.create().nextBetween(0, CHANNELS - 1);
        Direction facing = Direction.NORTH;
        if (context.getPlayerFacing().getAxis() != Direction.Axis.Y) {
            facing = context.getPlayerFacing().getOpposite();
        }
        return this.getDefaultState().with(FACING, facing).with(CHANNEL, channel);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(SEARCHING)) {
            return ActionResult.CONSUME;
        }
        if (hand == Hand.MAIN_HAND) {
            boolean newState = !state.get(ON);
            if (newState) {
                turnON(state, world, pos, player);
                spawnParticles(world, pos);
            } else {
                turnOFF(state, world, pos, player);
            }
            if (!world.isClient) {
                if (newState) {
                    for (int i = 0; i < 5; i++) {
                        double x = pos.getX() + 0.5 + (world.random.nextDouble() - 0.5) * 0.5;
                        double y = pos.getY() + 0.5 + world.random.nextDouble() * 0.5;
                        double z = pos.getZ() + 0.5 + (world.random.nextDouble() - 0.5) * 0.5;
                        world.addParticle(ParticleTypes.NOTE, x, y, z, 0, 0, 0);
                        world.addParticle(ParticleTypes.NOTE, x, y, z, 0.1, 0, 0);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private void turnON(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        world.playSound(player, pos, SoundEventRegistry.RADIO_CLICK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.playSound(player, pos, SoundEventRegistry.RADIO_TUNE, SoundCategory.RECORDS, 1.0f, 1.0f);
        if (!world.isClient) {
            pressButton(state, world, pos, true);
            sendPacket(state, world, pos, true);
        }
    }

    private void turnOFF(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        world.playSound(player, pos, SoundEventRegistry.RADIO_CLICK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (!world.isClient) {
            pressButton(state, world, pos, false);
            sendPacket(state, world, pos, false);
        }
    }

    public int tune(World world, BlockState blockState, BlockPos blockPos, int scrollValue) {
        if (scrollValue % CHANNELS == 0) {
            return blockState.get(CHANNEL);
        }
        int currentChannel = blockState.get(CHANNEL);
        int newChannel = scrollValue < 0 ? (CHANNELS - (Math.abs(currentChannel + scrollValue) % CHANNELS)) % CHANNELS : (currentChannel + scrollValue) % CHANNELS;
        world.setBlockState(blockPos, blockState.with(CHANNEL, newChannel).with(SEARCHING, true), 3);
        world.createAndScheduleBlockTick(blockPos, this, DELAY);
        return newChannel;
    }

    private void pressButton(BlockState state, World world, BlockPos pos, boolean on) {
            world.setBlockState(pos, state.with(ON, on).with(SEARCHING, true), 3);
            world.createAndScheduleBlockTick(pos, this, DELAY);
    }

    private void sendPacket(BlockState state, World world, BlockPos pos, boolean on) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeBlockPos(pos);
        buffer.writeInt(state.get(CHANNEL));
        buffer.writeBoolean(on);
        for (PlayerEntity player : world.getPlayers()) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, BeachpartyMessages.TURN_RADIO_S2C, buffer);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if(!world.isClient) {
                sendPacket(state, world, pos, false);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(SEARCHING)) {
            world.setBlockState(pos, state.with(SEARCHING, false), 3);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ON)) {
            spawnParticles(world, pos);
        }
    }

    private static void spawnParticles(World world, BlockPos pos) {
        Random random = world.random;

        double d = (double) pos.getX() + random.nextDouble();
        double e = pos.getY() + 0.5 + random.nextDouble();
        double f = (double) pos.getZ() + random.nextDouble();

        world.addParticle(ParticleTypes.NOTE, d, e, f, 0.0, 0.0, 0.0);

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ON, CHANNEL, SEARCHING);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("tooltip.beachparty.canbeplaced").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.beachparty.radioturnon").formatted(Formatting.WHITE));
        tooltip.add(Text.translatable("tooltip.beachparty.radioswitch").formatted(Formatting.WHITE));
    }

    static {
        ON = BooleanProperty.of("on");
        CHANNEL = IntProperty.of("channel", 0, CHANNELS - 1);
        SEARCHING = BooleanProperty.of("searching");
    }

}