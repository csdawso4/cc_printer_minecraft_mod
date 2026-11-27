package com.craigtec.block.custom.entity.custom;

import com.craigtec.peripheral.custom.PrintingPressPeripheral;
import com.craigtec.block.custom.entity.ModBlockEntities;
import com.craigtec.menu.custom.PrintingPressMenu;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.craigtec.CcPrinter.MOD_ID;

public class PrintingPressBlockEntity extends RandomizableContainerBlockEntity implements ExtendedMenuProvider<BlockPos> {
    public NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    protected final PrintingPressPeripheral peripheral = new PrintingPressPeripheral(this);
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public PrintingPressBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.PRINTING_PRESS_TYPE, worldPosition, blockState);
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.inventory);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        inventory = items;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        Item item = itemStack.getItem();
        return slot == 0 && item == Items.PAPER
                || slot == 1 && item == Items.INK_SAC
                || slot == 2 && item == Items.LEATHER
                || slot == 3 && item == Items.STRING;
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    /* PERIPHERAL RELATED FUNCTIONS */
    public boolean addPage() {
        if (!inventory.get(0).isEmpty() && !inventory.get(1).isEmpty()) {
            safeShrink(0);
            safeShrink(1);
            setChanged();
            return true;
        } else {
            return false;
        }
    }

    public boolean finishBook(ArrayList<String> pages, String title, String author) {
        if (!inventory.get(2).isEmpty() && !inventory.get(3).isEmpty()) {
            safeShrink(2);
            safeShrink(3);
            setChanged();
        } else {
            return false;
        }

        ItemStack writtenBook = new ItemStack(Items.WRITTEN_BOOK, 1);
        List<Filterable<Component>> filteredPages = pages.stream().map(s -> {
            Filterable<Component> filterable =  Filterable.passThrough(Component.literal(s));
            return filterable;
        }).toList();
        DataComponentMap components = DataComponentMap.builder().set(DataComponents.WRITTEN_BOOK_CONTENT,
                new WrittenBookContent(
                        Filterable.passThrough(title),
                        author,
                        0,
                        filteredPages,
                        true
                )
        ).build();
        writtenBook.applyComponents(components);
        ItemEntity bookEntity = new ItemEntity(
                level,
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5,
                writtenBook
        );
        level.addFreshEntity(bookEntity);

        return true;
    }

    /* BLOCK ENTITY SYNC */
    @Override
    public void setChanged() {
        super.setChanged();
        level.setBlockAndUpdate(this.worldPosition, this.getBlockState());
        if (!level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    /* SAVE and LOAD */

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.inventory);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.inventory);
    }

    /* MENU */
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.cc-printer.printing_press_block");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new PrintingPressMenu(containerId, inventory, this);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return this.worldPosition;
    }

    private void safeShrink(int index) {
        inventory.get(index).shrink(1);
        if (inventory.get(index).isEmpty()) {
            LOGGER.info("stack had count of 0, replacing with empty");
            inventory.set(index, ItemStack.EMPTY);
        }
    }
}
