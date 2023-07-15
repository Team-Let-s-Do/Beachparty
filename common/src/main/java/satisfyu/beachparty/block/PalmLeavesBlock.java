package satisfyu.beachparty.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import satisfyu.beachparty.registry.ObjectRegistry;


public class PalmLeavesBlock extends LeavesBlock {
    public static final IntegerProperty DISTANCE_9 = IntegerProperty.create("distance_9", 1, 9);

    public PalmLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE_9, 9).setValue(PERSISTENT, false).setValue(DISTANCE, 7).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE_9) == 9 && !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.decaying(blockState)) {
            LeavesBlock.dropResources(blockState, serverLevel, blockPos);
            serverLevel.removeBlock(blockPos, false);
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(DISTANCE_9);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, true).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return updateDistance(blockstate, pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    protected boolean decaying(BlockState pState) {
        return !pState.getValue(PERSISTENT) && pState.getValue(DISTANCE_9) == 9;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(DISTANCE_9) == 9 && pFacingState.getBlock() != this && pFacing != null && pFacing.getAxis().isHorizontal()) {
            return pState.setValue(DISTANCE_9, pState.getValue(DISTANCE_9) - 1);
        } else {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    private static BlockState updateDistance(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        int i = 9;
        BlockPos.MutableBlockPos blockpos$autocloseable = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$autocloseable.setWithOffset(pPos, direction);
            BlockState neighborState = pLevel.getBlockState(blockpos$autocloseable);

            if (neighborState.getBlock() == ObjectRegistry.PALM_LOG.get()) {
                return pState.setValue(DISTANCE_9, 9);
            }

            if (neighborState.getBlock() instanceof PalmLeavesBlock) {
                i = Math.min(i, neighborState.getValue(DISTANCE_9) + 1);

                if (i == 1) {
                    break;
                }
            }
        }

        return pState.setValue(DISTANCE_9, i);
    }



    private static int getDistanceAt(BlockState pNeighbor) {
        Block block = pNeighbor.getBlock();
        if (block == ObjectRegistry.PALM_LOG.get()) {
            return 9;
        } else if (block instanceof PalmLeavesBlock) {
            return pNeighbor.getValue(DISTANCE_9);
        } else if (pNeighbor.getBlock() == ObjectRegistry.PALM_LOG.get()) {
            return 0;
        } else {
            return 9;
        }
    }
}