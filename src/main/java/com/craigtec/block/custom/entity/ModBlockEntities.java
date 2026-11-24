package com.craigtec.block.custom.entity;

import com.craigtec.block.ModBlocks;
import com.craigtec.block.custom.entity.custom.PrintingPressBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static com.craigtec.CcPrinter.id;

public class ModBlockEntities {
    public static final BlockEntityType<PrintingPressBlockEntity> PRINTING_PRESS_TYPE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("printing_press_type"),
                    FabricBlockEntityTypeBuilder.create(PrintingPressBlockEntity::new, ModBlocks.PRINTING_PRESS_BLOCK).build());

    public static void registerBlockEntities() {

    }
}
