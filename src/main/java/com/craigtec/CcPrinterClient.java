package com.craigtec;

import com.craigtec.menu.ModMenuTypes;
import com.craigtec.menu.custom.PrintingPressScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class CcPrinterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.PRINTING_PRESS_MENU, PrintingPressScreen::new);
    }
}
