package com.craigtec.peripheral.custom;

import com.craigtec.block.custom.entity.custom.PrintingPressBlockEntity;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;

public class PrintingPressPeripheral implements IPeripheral {
    private final PrintingPressBlockEntity printingPress;
    private final AttachedComputerSet computers = new AttachedComputerSet();

    private Boolean bookInProgress = false;
    private final ArrayList<String> pages = new ArrayList<>();

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
    public void startBook() throws LuaException {
        if (bookInProgress) {
            throw new LuaException("A book is already in progress. Cannot start a new one.");
        }
        bookInProgress = true;
    }

    @LuaFunction(mainThread = true)
    public void addPage(String string) throws LuaException {
        if (!bookInProgress) {
            throw new LuaException("There is no book in progress. Start a new book before adding pages.");
        }
        boolean inkAndPaperConsumed = printingPress.addPage();
        if (!inkAndPaperConsumed) {
            throw new LuaException("There is not enough paper or ink in the inventory.");
        }
        pages.add(string);
    }

    @LuaFunction(mainThread = true)
    public void finishBook(String title, String author) throws LuaException {
        if (!bookInProgress) {
            throw new LuaException("There is no book in progress. Start a new book.");
        }
        boolean leatherAndStringConsumed = printingPress.finishBook(pages, title, author);
        if (!leatherAndStringConsumed) {
            throw new LuaException("There is not enough leather or string in the inventory.");
        }
        bookInProgress = false;
        pages.clear();
    }
}
