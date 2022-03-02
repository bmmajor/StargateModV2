package com.bmmajor20.stargatemod.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import static com.bmmajor20.stargatemod.setup.Registration.HOLOGRAM_BLOCK_TILE;

public class HologramBlockTile extends TileEntity {

    private BlockState hologramBlock;

    public HologramBlockTile(/*BlockState hologramBlock*/) {
        super(HOLOGRAM_BLOCK_TILE.get());
        //this.hologramBlock = hologramBlock;
        this.hologramBlock = Blocks.GLASS.getDefaultState();
    }

    public void setHologramBlock(BlockState state) {
        hologramBlock = state;
    }

    public BlockState getHologramBlock() {
        return hologramBlock;
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (hologramBlock!= null)
            tag.put("hologramBlock", NBTUtil.writeBlockState(hologramBlock));
        return super.write(tag);
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        hologramBlock = NBTUtil.readBlockState(tag.getCompound("hologramBlock"));
    }
}
