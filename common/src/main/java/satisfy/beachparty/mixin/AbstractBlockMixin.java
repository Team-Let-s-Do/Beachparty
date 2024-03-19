package satisfy.beachparty.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.beachparty.registry.ObjectRegistry;
import satisfy.beachparty.util.BeachpartyTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class AbstractBlockMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void injected(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.getBlock() == Blocks.HAY_BLOCK) {
            if (world.getBiome(pos).is(BeachpartyTags.WARM_BIOME)) {
                world.setBlockAndUpdate(pos, ObjectRegistry.DRIED_WHEAT_BLOCK.get().defaultBlockState());

            }
        }
    }
}
