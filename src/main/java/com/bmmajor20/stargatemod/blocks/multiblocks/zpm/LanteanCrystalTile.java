package com.bmmajor20.stargatemod.blocks.multiblocks.zpm;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import static com.bmmajor20.stargatemod.setup.Registration.LANTEAN_CRYSTAL_TILE;

public class LanteanCrystalTile extends TileEntity implements ITickableTileEntity {

    private int counter;
    private boolean lanteanCrystalOnTop;
    private boolean lanteanCrystalOnBottom;

    public LanteanCrystalTile() {
        super(LANTEAN_CRYSTAL_TILE.get());
    }

    @Override
    public void tick() {
        assert world != null;
        if (world.isRemote) {
            return;
        }
        if (counter % 20 == 0) {
//            BlockState blockStateTop = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
//            BlockState blockStateBottom = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
//            lanteanCrystalOnTop = blockStateTop.getBlock() instanceof LanteanCrystal;
//            lanteanCrystalOnBottom = blockStateBottom.getBlock() instanceof LanteanCrystal;
        }
        counter++;
    }

    boolean hasLanteanCrystalOnTop() {
        assert world != null;
        return world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() instanceof LanteanCrystal;
    }

    boolean hasLanteanCrystalOnBottom() {
        assert world != null;
        return world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof LanteanCrystal;
    }
}
