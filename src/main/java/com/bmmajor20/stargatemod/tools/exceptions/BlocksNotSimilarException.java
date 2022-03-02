package com.bmmajor20.stargatemod.tools.exceptions;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BlocksNotSimilarException extends IllegalArgumentException {
    private BlockState expected;
    private BlockState found;
    private BlockPos pos;

    public BlocksNotSimilarException(List<BlockState> blocks, BlockPos pos) {
        super();
        setBlocks(blocks);
        setPos(pos);
    }

    public BlocksNotSimilarException(List<BlockState> blocks, BlockPos pos, String s) {
        super(s);
        setBlocks(blocks);
        setPos(pos);
    }

    public BlocksNotSimilarException(List<BlockState> blocks, BlockPos pos, String message, Throwable cause) {
        super(message, cause);
        setBlocks(blocks);
        setPos(pos);
    }

    public BlocksNotSimilarException(List<BlockState> blocks, BlockPos pos, Throwable cause) {
        super(cause);
        setBlocks(blocks);
        setPos(pos);
    }

    private void setBlocks(List<BlockState> blocks) {
        this.expected = blocks.get(0);
        this.found = blocks.get(1);
    }

    private void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public final BlockState getExpected() {
        return expected;
    }

    public final BlockState getFound() {
        return found;
    }

    public final BlockPos getPos() {
        return pos;
    }
}
