package com.craigtec;

import com.craigtec.block.ModBlocks;
import com.craigtec.block.custom.entity.ModBlockEntities;
import com.craigtec.menu.ModMenuTypes;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcPrinter implements ModInitializer {
	public static final String MOD_ID = "cc-printer";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModMenuTypes.registerModMenuTypes();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
