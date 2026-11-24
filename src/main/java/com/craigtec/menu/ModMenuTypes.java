package com.craigtec.menu;

import com.craigtec.menu.custom.PrintingPressMenu;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

import static com.craigtec.CcPrinter.id;

public class ModMenuTypes {
    public static final MenuType<PrintingPressMenu> PRINTING_PRESS_MENU =
            Registry.register(BuiltInRegistries.MENU, id("printing_press_menu"),
                    new ExtendedMenuType<>(PrintingPressMenu::new, BlockPos.STREAM_CODEC));

    public static void registerModMenuTypes() {

    }
}
