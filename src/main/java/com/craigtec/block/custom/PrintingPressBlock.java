package com.craigtec.block.custom;

import com.craigtec.block.custom.entity.custom.PrintingPressBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class PrintingPressBlock extends BaseEntityBlock {
    private static MapCodec<PrintingPressBlock> CODEC = simpleCodec(PrintingPressBlock::new);
    public PrintingPressBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new PrintingPressBlockEntity(worldPosition, blockState);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        if (level.getBlockEntity(pos) instanceof PrintingPressBlockEntity printingPressBlockEntity) {
            printingPressBlockEntity.drops();
            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof PrintingPressBlockEntity printingPressBlockEntity) {
            player.openMenu(printingPressBlockEntity);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
