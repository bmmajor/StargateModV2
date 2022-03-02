package com.bmmajor20.stargatemod.datagen;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, StargateMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /*singleTexture(Registration.MAGICBLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(MyTutorial.MODID, "item/magicblock_item"));*/
        singleTexture(Registration.GOLD_POWER_RING.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(StargateMod.MODID, "item/gold_power_ring"));
        singleTexture(Registration.SINGULARITY.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(StargateMod.MODID, "item/singularity"));
        singleTexture(Registration.ZERO_POINT_MODULE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(StargateMod.MODID, "item/zero_point_module"));
        withExistingParent(Registration.FIRSTBLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(StargateMod.MODID, "block/firstblock"));
        withExistingParent(Registration.ZPMCRAFTER_ITEM.get().getRegistryName().getPath(), new ResourceLocation(StargateMod.MODID, "block/zpmcrafter"));
        withExistingParent(Registration.SINGULARITY_SPECIALIZER_ITEM.get().getRegistryName().getPath(), new ResourceLocation(StargateMod.MODID, "block/singularity_specializer"));

        /*
         * singleTexture(Registration.NAME_OF_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
         *      "layer0", new ResourceLocation(StargateMod.MODID, "item/name_of_item"));
         * withExistingParent(Registration.NAME_OF_BLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(StargateMod.MODID, "block/name_of_block_item"));
         */
    }
}