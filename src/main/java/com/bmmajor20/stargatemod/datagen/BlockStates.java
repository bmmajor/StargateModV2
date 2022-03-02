package com.bmmajor20.stargatemod.datagen;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, StargateMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(Registration.ZPMCRAFTER.get());
        registerFirstBlock();
        registerZPMCrafter();
        //registerComplexMultipart();
    }

    private void registerFirstBlock() {
        ResourceLocation side = new ResourceLocation(StargateMod.MODID, "block/firstblock");
        ResourceLocation top = new ResourceLocation(StargateMod.MODID, "block/firstblock_top");
        ResourceLocation front = new ResourceLocation(StargateMod.MODID, "block/firstblock_front");
        ResourceLocation sideP = new ResourceLocation(StargateMod.MODID, "block/firstblock_powered");
        ResourceLocation topP = new ResourceLocation(StargateMod.MODID, "block/firstblock_powered_top");
        ResourceLocation frontP = new ResourceLocation(StargateMod.MODID, "block/firstblock_powered_front");
        BlockModelBuilder modelFirstblock = models().cube("firstblock", side, top, front, side, side, side);
        BlockModelBuilder modelFirstblockPowered = models().cube("firstblock_powered", sideP, topP, frontP, sideP, sideP, sideP);
        orientedBlock(Registration.FIRSTBLOCK.get(), state -> {
            if (state.get(BlockStateProperties.POWERED)) {
                return modelFirstblockPowered;
            } else {
                return modelFirstblock;
            }
        });
    }

    private void registerZPMCrafter() {
        ResourceLocation side = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter");
        ResourceLocation top = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter_top");
        ResourceLocation bottom = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter_bottom");
        ResourceLocation sideP = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter_powered");
        ResourceLocation topP = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter_powered_top");
        BlockModelBuilder modelZPMCrafter = models().cube("zpmcrafter", bottom, top, side, side, side, side);
        BlockModelBuilder modelZPMCrafterPowered = models().cube("zpmcrafter_powered", bottom, topP, sideP, sideP, sideP, sideP);
        orientedBlock(Registration.ZPMCRAFTER.get(), state -> {
            if (state.get(BlockStateProperties.POWERED)) {
                return modelZPMCrafterPowered;
            } else {
                return modelZPMCrafter;
            }
        });
    }

    /*private void registerComplexMultipart() {
        BlockModelBuilder dimCellFrame = models().getBuilder("block/complex/main");

        floatingCube(dimCellFrame, 0f, 0f, 0f, 1f, 16f, 1f);
        floatingCube(dimCellFrame, 15f, 0f, 0f, 16f, 16f, 1f);
        floatingCube(dimCellFrame, 0f, 0f, 15f, 1f, 16f, 16f);
        floatingCube(dimCellFrame, 15f, 0f, 15f, 16f, 16f, 16f);

        floatingCube(dimCellFrame, 1f, 0f, 0f, 15f, 1f, 1f);
        floatingCube(dimCellFrame, 1f, 15f, 0f, 15f, 16f, 1f);
        floatingCube(dimCellFrame, 1f, 0f, 15f, 15f, 1f, 16f);
        floatingCube(dimCellFrame, 1f, 15f, 15f, 15f, 16f, 16f);

        floatingCube(dimCellFrame, 0f, 0f, 1f, 1f, 1f, 15f);
        floatingCube(dimCellFrame, 15f, 0f, 1f, 16f, 1f, 15f);
        floatingCube(dimCellFrame, 0f, 15f, 1f, 1f, 16f, 15f);
        floatingCube(dimCellFrame, 15f, 15f, 1f, 16f, 16f, 15f);

        floatingCube(dimCellFrame, 1f, 1f, 1f, 15f, 15f, 15f);

        dimCellFrame.texture("window", modLoc("block/complex_window"));

        createDimensionalCellModel(Registration.COMPLEX_MULTIPART.get(), dimCellFrame);
    }*/

    /*private void floatingCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz) {
        builder.element().from(fx, fy, fz).to(tx, ty, tz).allFaces((direction, faceBuilder) -> faceBuilder.texture("#window")).end();
    }*/

    /*private void createDimensionalCellModel(Block block, BlockModelBuilder dimCellFrame) {
        BlockModelBuilder singleNone = models().getBuilder("block/complex/singlenone")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/complex"));
        BlockModelBuilder singleIn = models().getBuilder("block/complex/singlein")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/complex_in"));
        BlockModelBuilder singleOut = models().getBuilder("block/complex/singleout")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/complex_out"));

        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        bld.part().modelFile(dimCellFrame).addModel();

        BlockModelBuilder[] models = new BlockModelBuilder[] { singleNone, singleIn, singleOut };
        for (ComplexMultipartTile.Mode mode : ComplexMultipartTile.Mode.values()) {
            bld.part().modelFile(models[mode.ordinal()]).addModel().condition(ComplexMultipartBlock.DOWN, mode);
            bld.part().modelFile(models[mode.ordinal()]).rotationX(180).addModel().condition(ComplexMultipartBlock.UP, mode);
            bld.part().modelFile(models[mode.ordinal()]).rotationX(90).addModel().condition(ComplexMultipartBlock.SOUTH, mode);
            bld.part().modelFile(models[mode.ordinal()]).rotationX(270).addModel().condition(ComplexMultipartBlock.NORTH, mode);
            bld.part().modelFile(models[mode.ordinal()]).rotationY(90).rotationX(90).addModel().condition(ComplexMultipartBlock.WEST, mode);
            bld.part().modelFile(models[mode.ordinal()]).rotationY(270).rotationX(90).addModel().condition(ComplexMultipartBlock.EAST, mode);
        }
    }*/

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir.getAxis() == Direction.Axis.Y ?  dir.getAxisDirection().getOffset() * -90 : 0)
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
}
