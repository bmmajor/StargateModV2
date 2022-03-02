package com.bmmajor20.stargatemod.blocks.multiblocks.zpm;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class StableSingularity extends Block {

    public StableSingularity() {
        super(Block.Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(50.0f, 1200.0f));
    }
}
