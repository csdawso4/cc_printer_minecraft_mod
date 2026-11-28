package com.craigtec.block;

import com.craigtec.peripheral.custom.PrintingPressPeripheral;
import com.craigtec.block.custom.PrintingPressBlock;
import com.craigtec.block.custom.entity.ModBlockEntities;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

import static com.craigtec.CcPrinter.id;

public class ModBlocks {
    public static final Block PRINTING_PRESS_BLOCK = registerBlock(
            "printing_press_block",
            properties -> new PrintingPressBlock(
                    properties.mapColor(MapColor.STONE).strength(3.5F).sound(SoundType.STONE)
            )
    );

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(
                BlockBehaviour.Properties.of().setId(
                        ResourceKey.create(
                                Registries.BLOCK,
                                id(name)
                        )
                )
        );
        registerBlockItem(name, toRegister);
        return Registry.register(
                BuiltInRegistries.BLOCK,
                id(name),
                toRegister
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                id(name),
                new BlockItem(
                        block,
                        new Item.Properties().useBlockDescriptionPrefix().setId(
                                ResourceKey.create(
                                        Registries.ITEM,
                                        id(name)
                                )
                        )
                )
        );
    }

    public static void registerModBlocks() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
            output.accept(PRINTING_PRESS_BLOCK);
        });
        PeripheralLookup.get().registerForBlockEntity((blockEntity, direction) ->
                new PrintingPressPeripheral(blockEntity), ModBlockEntities.PRINTING_PRESS_TYPE);
    }
}
