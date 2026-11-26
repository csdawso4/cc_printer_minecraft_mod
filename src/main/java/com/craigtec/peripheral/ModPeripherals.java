package com.craigtec.peripheral;

import com.craigtec.block.custom.entity.ModBlockEntities;
import com.craigtec.peripheral.custom.PrintingPressPeripheral;
import dan200.computercraft.api.peripheral.PeripheralLookup;

public class ModPeripherals {
    public static void registerPeripherals() {
        PeripheralLookup.get().registerForBlockEntity((blockEntity, direction) ->
                new PrintingPressPeripheral(blockEntity), ModBlockEntities.PRINTING_PRESS_TYPE);
    }
}
