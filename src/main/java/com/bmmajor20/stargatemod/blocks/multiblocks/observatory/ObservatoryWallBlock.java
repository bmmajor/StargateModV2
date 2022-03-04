package com.bmmajor20.stargatemod.blocks.multiblocks.observatory;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ObservatoryWallBlock extends Block {

    public ObservatoryWallBlock() {
        super(Block.Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(32.0f));
    }
}
