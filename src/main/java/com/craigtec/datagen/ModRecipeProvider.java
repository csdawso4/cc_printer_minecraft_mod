package com.craigtec.datagen;

import com.craigtec.block.ModBlocks;
import dan200.computercraft.shared.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.REDSTONE, ModBlocks.PRINTING_PRESS_BLOCK, 1)
                        .pattern("SSS")
                        .pattern("SRS")
                        .pattern("SIS")
                        .define('S', Items.STONE)
                        .define('R', Items.REDSTONE)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy(getHasName(ModRegistry.Items.COMPUTER_NORMAL.get()), has(ModRegistry.Items.COMPUTER_NORMAL.get()))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "CC-Printer Recipes";
    }
}
