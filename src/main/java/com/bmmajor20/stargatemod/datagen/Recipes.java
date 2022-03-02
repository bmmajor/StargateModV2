package com.bmmajor20.stargatemod.datagen;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.setup.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // Items
        ShapedRecipeBuilder.shapedRecipe(Registration.GOLD_POWER_RING.get(), 4)
                .patternLine("rgr")
                .patternLine("g g")
                .patternLine("rgr")
                .key('r', Items.REDSTONE)
                .key('g', Items.GOLD_INGOT)
                .setGroup(StargateMod.MODID + "recipes/gold_power_ring")
                .addCriterion("gold-redstone", InventoryChangeTrigger.Instance.
                        forItems(Items.GOLD_INGOT, Items.REDSTONE))
                .build(consumer);

        // Placeable Items
        ShapedRecipeBuilder.shapedRecipe(Registration.LANTEAN_CRYSTAL_ITEM.get(),  8)
                .patternLine("gQg")
                .patternLine("rQr")
                .patternLine("gQg")
                .key('g', Items.GLOWSTONE_DUST)
                .key('Q', Items.QUARTZ_PILLAR)
                .key('r', Items.REDSTONE)
                .setGroup(StargateMod.MODID + "recipes/lantean_crystal")
                .addCriterion("redstone-quartz-glowstone", InventoryChangeTrigger.Instance.
                        forItems(Items.REDSTONE, Items.QUARTZ, Items.GLOWSTONE_DUST))
                .build(consumer);



        // Blocks


        // Machines
        ShapedRecipeBuilder.shapedRecipe(Registration.FIRSTBLOCK.get())
                .setGroup(StargateMod.MODID + "recipes/firstblock")
                .patternLine("#X#")
                .patternLine("XxX")
                .patternLine("#X#")
                .key('#', Tags.Items.STORAGE_BLOCKS_LAPIS)
                .key('X', Registration.GOLD_POWER_RING.get())
                //.key('x', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .key('x', Registration.LANTEAN_CONTROL_BLOCK.get())
                .addCriterion("gold_power_ring-lantean_control_block", InventoryChangeTrigger.Instance.
                        forItems(Registration.GOLD_POWER_RING.get(), Registration.LANTEAN_CONTROL_BLOCK.get()))
                //.addCriterion("gold_power_ring-lantean_control_block", InventoryChangeTrigger.Instance.
                //        forItems(ModItems.GOLD_POWER_RING, ModBlocks.LANTEAN_CONTROL_BLOCK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(Registration.ZPMCRAFTER.get())
                .setGroup(StargateMod.MODID + "recipes/zpmcrafter")
                .patternLine("#X#")
                .patternLine("XxX")
                .patternLine("#X#")
                .key('#', Tags.Items.OBSIDIAN)
                .key('X', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('x', Registration.LANTEAN_CONTROL_BLOCK.get())
                //.key('X', Registration.SUPERMAGNETIC_STABILIZER.get())
                //.key('x', Registration.LANTEAN_CONTROL_BLOCK.get())
                .addCriterion("iron_obsidian_quartz", InventoryChangeTrigger.Instance.
                        forItems(Blocks.IRON_BLOCK, Blocks.OBSIDIAN, Blocks.QUARTZ_BLOCK))
                //.addCriterion("lantean_control_block", InventoryChangeTrigger.Instance.
                //        forItems(ModBlocks.LANTEAN_CONTROL_BLOCK))
                //.build(consumer);
                .build(consumer);
    }
}