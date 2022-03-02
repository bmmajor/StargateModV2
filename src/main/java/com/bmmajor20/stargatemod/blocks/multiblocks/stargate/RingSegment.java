package com.bmmajor20.stargatemod.blocks.multiblocks.stargate;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RingSegment extends Block {

    public RingSegment() {
        super(Block.Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(32.0f));
    }
}
