package com.craigtec.peripheral.custom;

import com.craigtec.block.custom.entity.custom.PrintingPressBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jspecify.annotations.Nullable;

public class PrintingPressPeripheral implements IPeripheral {
    private final PrintingPressBlockEntity printingPress;
    private final AttachedComputerSet computers = new AttachedComputerSet();

    public PrintingPressPeripheral(PrintingPressBlockEntity printingPress) {
        this.printingPress = printingPress;
    }

    @Override
    public String getType() {
        return "printing_press";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof PrintingPressPeripheral o && o.printingPress == printingPress;
    }

    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }

    @LuaFunction(mainThread = true)
    public void test() {
        printingPress.test();
    }
}
