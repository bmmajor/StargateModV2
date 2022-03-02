package com.bmmajor20.stargatemod.blocks.machines.zpmcrafter;

import com.bmmajor20.stargatemod.blocks.multiblocks.MultiBlocks;
import com.bmmajor20.stargatemod.setup.Config;
import com.bmmajor20.stargatemod.setup.Registration;
import com.bmmajor20.stargatemod.tools.CustomEnergyStorage;
import com.bmmajor20.stargatemod.tools.exceptions.BlocksNotSimilarException;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.*;

import static com.bmmajor20.stargatemod.setup.Registration.ZPMCRAFTER_TILE;

public class ZPMCrafterTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomEnergyStorage energyStorage = createEnergy();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    private int counter;
    private int previousBlockPlacedAt;
    private int singularityInsertedAt;
    private int singularityStabilizedAt;
    private int structureCompletedAt;
    private int singularityDeployedAt;
    private int zpmCraftedAt;
    private int singularityCollapsingAt;
    private ZPMCraftingStage stage;
    private ZPMCraftingStage stageBeforeCollapsing;

    // Trackable values
    private int stageNr;
    private int stageBeforeCollapsingNr;
    private int stabilizingProgress;
    private int constructionProgress;
    private int deploymentProgress;
    private int craftingProgress;
    private int collapsingProgress;
    private boolean zpmCrafting;
    private boolean zpmCrafted;
    private boolean criticalStateImminent;
    private boolean criticalState;

    private final Map<BlockPos, BlockState> levers = new HashMap<>(8);

    // (static) variables to fix the rendering bug (crafting rendering step happened to all machines instead of the 1 crafting)
    private int machineID;
    private static int lastID;
    private static final List<Integer> craftingIDs = new ArrayList<>();

    public ZPMCrafterTile() {
        super(ZPMCRAFTER_TILE.get());
        ZPMCrafterTile.lastID += 1;
        this.machineID = ZPMCrafterTile.lastID;
        this.stage = ZPMCraftingStage.AWAITING_RESOURCES;
    }

    public int getMachineID() {
        return machineID;
    }
    public static List<Integer> getCraftingIDs() {
        return craftingIDs;
    }
    private void addToCraftingIDs(int id) {
        if (!craftingIDs.contains(id))
            craftingIDs.add(id);
    }
    private void removeFromCraftingIDs(int id) {
        if (craftingIDs.contains(id))
            craftingIDs.remove((Integer) id);
    }

    public int getStageNr() {
        return stageNr;
    }
    public final void setStageNr(int stageNr) {
        this.stageNr = stageNr;
    }

    public int getStabilizingProgress() {
        if (stabilizingProgress == 0) {
            if (stage == ZPMCraftingStage.STABILIZING || stageBeforeCollapsing == ZPMCraftingStage.STABILIZING)
                return counter - singularityInsertedAt;
            else if (stage.ordinal() > ZPMCraftingStage.STABILIZING.ordinal())
                return Config.ZPMCRAFTER_TICKS_STABILIZING.get();
            else
                return 0;
        } else
            return stabilizingProgress;
    }
    public final void setStabilizingProgress(int progress) {
        this.stabilizingProgress = progress;
    }
    public int getConstructionProgress() {
        if (constructionProgress == 0) {
            if (stage == ZPMCraftingStage.STABILIZING || stage == ZPMCraftingStage.AWAITING_STRUCTURE || stageBeforeCollapsing == ZPMCraftingStage.STABILIZING || stageBeforeCollapsing == ZPMCraftingStage.AWAITING_STRUCTURE)
                return counter - singularityInsertedAt; // TODO: return the amount of blocks placed (0->128 + (0->4*1000))*/
            else if (stage.ordinal() > ZPMCraftingStage.AWAITING_STRUCTURE.ordinal())
                return Config.ZPMCRAFTER_TICKS_STABILIZING.get() * 2;
            else
                return 0;
        } else
            return constructionProgress;
    }
    public final void setConstructionProgress(int progress) {
        this.constructionProgress = progress;
    }
    public int getDeploymentProgress() {
        if (deploymentProgress == 0) {
            if (stage == ZPMCraftingStage.DEPLOYING_SINGULARITY || stageBeforeCollapsing == ZPMCraftingStage.DEPLOYING_SINGULARITY)
                return counter - structureCompletedAt;
            else if (stage.ordinal() > ZPMCraftingStage.DEPLOYING_SINGULARITY.ordinal())
                return Config.ZPMCRAFTER_TICKS_DEPLOYING.get();
            else
                return 0;
        } else
            return deploymentProgress;
    }
    public final void setDeploymentProgress(int progress) {
        this.deploymentProgress = progress;
    }
    public int getCraftingProgress() {
        if (craftingProgress == 0) {
            if (stage == ZPMCraftingStage.CRAFTING || stageBeforeCollapsing == ZPMCraftingStage.CRAFTING)
                return counter - singularityDeployedAt;
            else if (stage.ordinal() > ZPMCraftingStage.CRAFTING.ordinal())
                return Config.ZPMCRAFTER_TICKS_CRAFTING.get();
            else
                return 0;
        } else
            return craftingProgress;
    }
    public final void setCraftingProgress(int progress) {
        this.craftingProgress = progress;
    }
    public int getCollapsingProgress() {
        if (collapsingProgress == 0) {
            if (stage == ZPMCraftingStage.COLLAPSING_REVERSIBLE)
                return counter - singularityCollapsingAt;
            else if (stage.ordinal() > ZPMCraftingStage.COLLAPSING_REVERSIBLE.ordinal())
                return Config.ZPMCRAFTER_TICKS_DESTRUCTION.get();
            else
                return 0;
        } else
            return collapsingProgress;
    }
    public final void setCollapsingProgress(int progress) {
        this.collapsingProgress = progress;
    }

    public boolean isCriticalStateImminent() {
        return criticalStateImminent;
    }
    public boolean isCriticalState() {
        return criticalState;
    }
    public boolean isZPMCrafting() {
        return zpmCrafting;
    }
    public boolean isZPMCrafted() {
        if (zpmCrafted) {
            zpmCrafted = false;
            return true;
        }
        return false;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 7, 1));
    }

    @Override
    public void tick() {
        assert world != null;
        if (world.isRemote) {
            return;
        }
        switch (stage) {
            case COLLAPSING_IRREVERSIBLE:
                criticalState = true;
                criticalStateImminent = false;
                world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
                break;
            case COLLAPSING_REVERSIBLE:
                if (energyStorage.getEnergyStored() < Config.ZPMCRAFTER_CONSUMPTION_STABILIZING.get()) {
                    if (counter - singularityCollapsingAt >= Config.ZPMCRAFTER_TICKS_DESTRUCTION.get()) {
                        stage = ZPMCraftingStage.COLLAPSING_IRREVERSIBLE;
                        stageNr = ZPMCraftingStage.COLLAPSING_IRREVERSIBLE.ordinal();
                        markDirty();
                    }
                } else {
                    // Undo 1 collapsing tick, since 1 tick passed, it adds 2 to the "start" value, so the result is -1
                    singularityCollapsingAt += 2;
                    energyStorage.consumeEnergy(Config.ZPMCRAFTER_CONSUMPTION_STABILIZING.get());
                    if (counter <= singularityCollapsingAt) {
                        singularityCollapsingAt = 0;
                        stage = stageBeforeCollapsing;
                        stageNr = stageBeforeCollapsing.ordinal();
                        markDirty();
                    }
                }
                break;
            case CRAFTING:
                if (hasDeployedStableSingularity()) {
                    if (counter - singularityDeployedAt <= Config.ZPMCRAFTER_TICKS_CRAFTING.get()) {
                        tryConsumeEnergy1(Config.ZPMCRAFTER_CONSUMPTION_CRAFTING.get());
                    }
                    if (counter - singularityDeployedAt >= Config.ZPMCRAFTER_TICKS_CRAFTING.get()) {
                        zpmCraftedAt = counter;
                        world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 6, pos.getZ()), Blocks.AIR.getDefaultState());
                        world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 6.5, pos.getZ() + 0.5, new ItemStack(Registration.ZERO_POINT_MODULE.get(), 1)));

                        previousBlockPlacedAt = 0;
                        singularityInsertedAt = 0;
                        singularityStabilizedAt = 0;
                        structureCompletedAt = 0;
                        singularityDeployedAt = 0;
                        zpmCraftedAt = 0;
                        singularityCollapsingAt = 0;
                        stage = ZPMCraftingStage.AWAITING_RESOURCES;
                        stageNr = ZPMCraftingStage.AWAITING_RESOURCES.ordinal();
                        stageBeforeCollapsing = null;
                        criticalStateImminent = false;
                        criticalState = false;
                        zpmCrafting = false;
                        zpmCrafted = true;
                        removeFromCraftingIDs(this.machineID);
                        markDirty();
                        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                    }
                }
                break;
            case DEPLOYING_SINGULARITY:
                if (hasSingularity()) {
                    if (counter - structureCompletedAt <= Config.ZPMCRAFTER_TICKS_DEPLOYING.get()) {
                        tryConsumeEnergy2(Config.ZPMCRAFTER_CONSUMPTION_DEPLOYING.get());
                    }
                    if (counter - structureCompletedAt >= Config.ZPMCRAFTER_TICKS_DEPLOYING.get()) {
                        singularityDeployedAt = counter;
                        world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 6, pos.getZ()), Blocks.COAL_BLOCK.getDefaultState());
                        itemHandler.extractItem(0, 1, false);
                        stage = ZPMCraftingStage.CRAFTING;
                        stageNr = ZPMCraftingStage.CRAFTING.ordinal();
                        zpmCrafting = removeZPMBlocks();
                        if (zpmCrafting)
                            addToCraftingIDs(this.machineID);
                        markDirty();
                        world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                    }
                } else {
                    stage = ZPMCraftingStage.AWAITING_RESOURCES;
                    stageNr = ZPMCraftingStage.AWAITING_RESOURCES.ordinal();
                    markDirty();
                }
                break;
            case AWAITING_STRUCTURE:
                if (hasSingularity()) {
                    if (singularityStabilizedAt != 0) {
                        int powerWithPenalty = calculatePowerPenalty(Config.ZPMCRAFTER_CONSUMPTION_STABILIZED.get());
                        if (powerWithPenalty > Config.ZPMCRAFTER_RECEIVE.get())
                            criticalStateImminent = true;
                        tryConsumeEnergy2(powerWithPenalty);
                    }
                } else {
                    stage = ZPMCraftingStage.AWAITING_RESOURCES;
                    stageNr = ZPMCraftingStage.AWAITING_RESOURCES.ordinal();
                    markDirty();
                }
                break;
            case STABILIZING:
                if (hasSingularity()) {
                    if (counter - singularityInsertedAt <= Config.ZPMCRAFTER_TICKS_STABILIZING.get()) {
                        tryConsumeEnergy1(Config.ZPMCRAFTER_CONSUMPTION_STABILIZING.get());
                    }
                    if (counter - singularityInsertedAt >= Config.ZPMCRAFTER_TICKS_STABILIZING.get()) {
                        singularityStabilizedAt = counter;
                        stage = ZPMCraftingStage.AWAITING_STRUCTURE;
                        stageNr = ZPMCraftingStage.AWAITING_STRUCTURE.ordinal();
                        markDirty();
                    }
                } else {
                    stage = ZPMCraftingStage.AWAITING_RESOURCES;
                    stageNr = ZPMCraftingStage.AWAITING_RESOURCES.ordinal();
                    markDirty();
                }
                break;
            case AWAITING_RESOURCES:
                if (hasSingularity()) {
                    singularityInsertedAt = counter;
                    stage = ZPMCraftingStage.STABILIZING;
                    stageNr = ZPMCraftingStage.STABILIZING.ordinal();
                    markDirty();
                }
                break;
        }
        if (stage == ZPMCraftingStage.AWAITING_RESOURCES && counter > zpmCraftedAt) {
            //System.out.println("");
        }
        if (stage.ordinal() >= 1 && stage.ordinal() <= 2) {
            if (singularityInsertedAt > zpmCraftedAt && counter - previousBlockPlacedAt >= 10) {
                boolean zpmStructureFinished = buildZPM();
                if (zpmStructureFinished && stage.ordinal() == 2) {
                    structureCompletedAt = counter;
                    stage = ZPMCraftingStage.DEPLOYING_SINGULARITY;
                    stageNr = ZPMCraftingStage.DEPLOYING_SINGULARITY.ordinal();
                    markDirty();
                }
                previousBlockPlacedAt = counter;
            }
        }
        if (!hasSingularity() && (stage != ZPMCraftingStage.CRAFTING && stage != ZPMCraftingStage.COLLAPSING_REVERSIBLE && stage != ZPMCraftingStage.COLLAPSING_IRREVERSIBLE)) {
            previousBlockPlacedAt = 0;
            singularityInsertedAt = 0;
            singularityStabilizedAt = 0;
            structureCompletedAt = 0;
            singularityDeployedAt = 0;
            zpmCraftedAt = 0;
            singularityCollapsingAt = 0;
            stage = ZPMCraftingStage.AWAITING_RESOURCES;
            stageNr = ZPMCraftingStage.AWAITING_RESOURCES.ordinal();
            stageBeforeCollapsing = null;
            criticalStateImminent = false;
            criticalState = false;
            markDirty();
        }
        if (counter % 20 == 0) {
            checkUpgradeStructure();
        }
        counter++;
    }

    private void tryConsumeEnergy1(int energy) {
        if (energyStorage.getEnergyStored() == 0) {
            singularityCollapsingAt = counter;
            stageBeforeCollapsing = stage;
            stage = ZPMCraftingStage.COLLAPSING_REVERSIBLE;
            stageNr = ZPMCraftingStage.COLLAPSING_REVERSIBLE.ordinal();
            markDirty();
        }
        energyStorage.consumeEnergy(energy);
    }

    private void tryConsumeEnergy2(int energy) {
        if (energyStorage.getEnergyStored() == 0) {
            singularityCollapsingAt = counter;
            stageBeforeCollapsing = stage;
            stage = ZPMCraftingStage.COLLAPSING_IRREVERSIBLE;
            stageNr = ZPMCraftingStage.COLLAPSING_IRREVERSIBLE.ordinal();
            markDirty();
        }
        energyStorage.consumeEnergy(energy);
    }

    private int calculatePowerPenalty(int basePower) {
        if (counter - singularityInsertedAt <= 1280)
            return basePower;
        else
            return basePower + 15 * (counter - singularityInsertedAt - 1280);
    }

    private boolean hasSingularity() {
        return itemHandler.getStackInSlot(0).getItem() == Registration.SINGULARITY.get();
    }

    private boolean hasDeployedStableSingularity() {
        assert world != null;
        return world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 6, pos.getZ())) == Blocks.COAL_BLOCK.getDefaultState();
    }

    private boolean buildZPM() {
        System.out.println("Building ZPM");
        assert world != null;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        boolean zpmComplete = false;

        try {
            Block[][][] zpmLayout = MultiBlocks.ZPM.getZPMLayout();
            if (checkStructure(zpmLayout, x, y, z)) {
                zpmComplete = true;
            }
        } catch (BlocksNotSimilarException e) {
            BlockState nextBlock = e.getExpected();
            BlockPos pos = e.getPos();
            world.setBlockState(pos, nextBlock);
        }
        return zpmComplete;
    }

    public boolean removeZPMBlocks() {
        System.out.println("Removing ZPM Blocks!");
        assert world != null;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        boolean structureWasComplete;

        try {
            Block[][][] zpmLayout = MultiBlocks.ZPM.getZPMLayout();
            structureWasComplete = checkStructure(zpmLayout, x, y, z);
            if (structureWasComplete) {
                int machineAtLayer = 9;
                for (int i = 0; i < zpmLayout.length; i++) {
                    Block[][] layer = zpmLayout[i];
                    int layerSize = layer.length;
                    for (int j = 0; j < layer.length; j++) {
                        Block[] line = layer[j];
                        int lineSize = line.length;
                        for (int k = 0; k < line.length; k++) {
                            Block blockToBe = line[k];
                            if (blockToBe != null) {
                                int bX = x - ((lineSize - 1) / 2) + k;
                                int bY = y + machineAtLayer - i;
                                int bZ = z - ((layerSize - 1) / 2) + j;
                                BlockState blockState = world.getBlockState(new BlockPos(bX, bY, bZ));
                                if (blockState.getBlock().equals(blockToBe)) {
                                    //world.removeBlock(new BlockPos(bX, bY, bZ), false);
                                    world.setBlockState(new BlockPos(bX, bY, bZ), Blocks.AIR.getDefaultState());
                                } else if (blockState.getBlock().equals(Blocks.COAL_BLOCK) && blockToBe.equals(Blocks.AIR)) {
                                    //world.removeBlock(new BlockPos(bX, bY, bZ), false);
                                    //world.setBlockState(new BlockPos(bX, bY, bZ), Blocks.AIR.getDefaultState());
                                } else {
                                    String blockToBeClass = blockToBe.getClass().getSimpleName();
                                    String blockStateClass = blockState.getBlock().getClass().getSimpleName();
                                    throw new BlocksNotSimilarException(Arrays.asList(blockToBe.getDefaultState(), blockState), new BlockPos(bX, bY, bZ), String.format("Blocks are not the same! Found %s, should be %s%n", blockStateClass, blockToBeClass));
                                }
                            }
                        }
                    }
                }
            }
        } catch (BlocksNotSimilarException e) {
            System.err.println(e.getMessage());
            markDirty();
            return false;
        }
        markDirty();
        world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        return structureWasComplete;
    }

    private boolean checkStructure(Block[][][] structure, int x, int y, int z) {
        assert world != null;
        int machineAtLayer = (structure == MultiBlocks.ZPM.getZPMLayout()) ? 9 : -1;
        for (int i = 0; i < structure.length; i++) {
            Block[][] layer = structure[i];
            Block[] middleLine = layer[(layer.length - 1) / 2];
            Block block = middleLine[(middleLine.length - 1) / 2]; // block here should theoretically be the machine itself...
            if (block instanceof ZPMCrafter) {
                machineAtLayer = i;
            }
        }
        for (int i = 0; i < structure.length; i++) {
            Block[][] layer = structure[i];
            int layerSize = layer.length;
            for (int j = 0; j < layer.length; j++) {
                Block[] line = layer[j];
                int lineSize = line.length;
                for (int k = 0; k < line.length; k++) {
                    Block blockToBe = line[k];
                    if (blockToBe != null) {
                        int bX = x - ((lineSize - 1) / 2) + k;
                        int bY = y + machineAtLayer - i;
                        int bZ = z - ((layerSize - 1) / 2) + j;
                        BlockState blockState = world.getBlockState(new BlockPos(bX, bY, bZ));
                        if (blockState.getBlock().equals(blockToBe)) {
                            if (blockState.getBlock().equals(Blocks.LEVER)) {
                                levers.put(new BlockPos(bX, bY, bZ), blockState);
                            }
                        } else if (blockState.getBlock().equals(Blocks.COAL_BLOCK)) {
                            // Continue checking
                        } else {
                            String blockToBeClass = blockToBe.getClass().getSimpleName();
                            String blockStateClass = blockState.getBlock().getClass().getSimpleName();
                            throw new BlocksNotSimilarException(Arrays.asList(blockToBe.getDefaultState(), blockState), new BlockPos(bX, bY, bZ), String.format("Blocks are not the same! Found %s, should be %s%n", blockStateClass, blockToBeClass));
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkUpgradeStructure() {
        try {
            System.out.println("Checking Upgrade Structure");
            assert world != null;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            Block primaryUpgradeRoot = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
            Block secondaryUpgradeRoot = world.getBlockState(new BlockPos(x, y - 2, z)).getBlock();
            boolean puComplete = false;
            boolean suComplete = false;
            boolean enhanced = false;

            if (primaryUpgradeRoot == Blocks.GLOWSTONE) {
                if (secondaryUpgradeRoot == Blocks.SEA_LANTERN) {
                    Block[][][] autoBreakerAndPlacer = MultiBlocks.ZPMUpgrades.getAutoBreakerAndPlacerLayout();
                    if (checkStructure(autoBreakerAndPlacer, x, y, z)) {
                        puComplete = true;
                        suComplete = true;
                    }
                } else {
                    Block[][][] autoBreaker = MultiBlocks.ZPMUpgrades.getAutoBreakerLayout();
                    if (checkStructure(autoBreaker, x, y, z)) {
                        puComplete = true;
                    }
                }
                levers.forEach((k, v) -> ((LeverBlock)v.getBlock()).setPowered(v, world, k));
            } else if (primaryUpgradeRoot == Blocks.SEA_LANTERN) {
                if (secondaryUpgradeRoot == Blocks.GLOWSTONE) {
                    Block[][][] autoPlacerAndBreaker = MultiBlocks.ZPMUpgrades.getAutoPlacerAndBreakerLayout();
                    if (checkStructure(autoPlacerAndBreaker, x, y, z)) {
                        puComplete = true;
                        suComplete = true;
                    }
                } else {
                    Block[][][] autoPlacer = MultiBlocks.ZPMUpgrades.getAutoPlacerLayout();
                    if (checkStructure(autoPlacer, x, y, z)) {
                        puComplete = true;
                    }
                }
                levers.forEach((k, v) -> ((LeverBlock)v.getBlock()).setPowered(v, world, k));
            } else {
                // No upgrade present, or root block hasn't been placed yet...
            }
        } catch (BlocksNotSimilarException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /*private void receivePower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() < Config.ZPMCRAFTER_MAXPOWER.get()) {
            for (Direction direction : Direction.values()) {
                TileEntity te = world.getTileEntity(pos.offset(direction));
                if (te != null) {
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                        if (handler.canExtract()) {
                            int extracted = handler.extractEnergy(Math.max(capacity.get(), 100), false);
                            capacity.addAndGet(extracted);
                            energyStorage.addEnergy(extracted);
                            markDirty();
                            return capacity.get() < Config.ZPMCRAFTER_MAXPOWER.get();
                        } else {
                            return true;
                        }
                    }).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }*/

    @Nonnull
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0 && stack.getItem() == Registration.SINGULARITY.get()) return true;
                if (slot == 1 && stack.getItem() == Registration.LANTEAN_CRYSTAL_ITEM.get()) return true;
                if (slot == 2 && stack.getItem() == Items.GLASS) return true;
                if (slot == 3 && stack.getItem() == Items.REDSTONE_BLOCK) return true;
                return slot == 4 && stack.getItem() == Items.END_ROD;
            }

            /*@Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!(stack.getItem() instanceof FirstItem)Items.DIAMOND) {
                    return stack;
                }
                if (stack.getItem() != Items.DIAMOND) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }*/
        };
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(Config.ZPMCRAFTER_MAXPOWER.get(), Config.ZPMCRAFTER_RECEIVE.get()) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        };
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        writeForClient(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.readForClient(tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 69, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert world != null;
        handleUpdateTag(pkt.getNbtCompound());
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    @Override
    public void read(CompoundNBT tag) {
        if (tag.getInt("x") == this.pos.getX() && tag.getInt("y") == this.pos.getY() && tag.getInt("z") == this.pos.getZ()) {
            readForClient(tag);
            itemHandler.deserializeNBT(tag.getCompound("inv"));
            energyStorage.deserializeNBT(tag.getCompound("energy"));
            previousBlockPlacedAt = tag.getInt("previousBlockPlacedAt");
            singularityInsertedAt = tag.getInt("singularityInsertedAt");
            singularityStabilizedAt = tag.getInt("singularityStabilizedAt");
            structureCompletedAt = tag.getInt("structureCompletedAt");
            zpmCraftedAt = tag.getInt("zpmCraftedAt");
            singularityCollapsingAt = tag.getInt("singularityCollapsingAt");
        }
    }

    private void readForClient(CompoundNBT tag) {
        if (tag.getInt("x") == this.pos.getX() && tag.getInt("y") == this.pos.getY() && tag.getInt("z") == this.pos.getZ()) {
            super.read(tag);
            machineID = tag.getInt("machineID");
            stage = ZPMCraftingStage.values()[tag.getInt("stage")];
            stageNr = tag.getInt("stageNr");
            if (tag.contains("stageBeforeCollapsing"))
                stageBeforeCollapsing = ZPMCraftingStage.values()[tag.getInt("stageBeforeCollapsing")];
            stageBeforeCollapsingNr = tag.getInt("stageBeforeCollapsingNr");
            stabilizingProgress = tag.getInt("stabilizingProgress");
            constructionProgress = tag.getInt("constructionProgress");
            deploymentProgress = tag.getInt("deploymentProgress");
            craftingProgress = tag.getInt("craftingProgress");
            collapsingProgress = tag.getInt("collapsingProgress");
            counter = tag.getInt("counter");
            singularityDeployedAt = tag.getInt("singularityDeployedAt");
            zpmCrafting = tag.getBoolean("zpmCrafting");
            zpmCrafted = tag.getBoolean("zpmCrafted");
            criticalStateImminent = tag.getBoolean("criticalStateImminent");
            criticalState = tag.getBoolean("criticalState");
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("previousBlockPlacedAt", previousBlockPlacedAt);
        tag.putInt("singularityInsertedAt", singularityInsertedAt);
        tag.putInt("singularityStabilizedAt", singularityStabilizedAt);
        tag.putInt("structureCompletedAt", structureCompletedAt);
        tag.putInt("zpmCraftedAt", zpmCraftedAt);
        tag.putInt("singularityCollapsingAt", singularityCollapsingAt);
        writeForClient(tag);
        return super.write(tag);
    }

    private void writeForClient(@Nonnull CompoundNBT tag) {
        tag.putInt("machineID", machineID);
        tag.putInt("stage", stage.ordinal());
        tag.putInt("stageNr", stageNr);
        if (stageBeforeCollapsing != null)
            tag.putInt("stageBeforeCollapsing", stageBeforeCollapsing.ordinal());
        tag.putInt("stageBeforeCollapsingNr", stageBeforeCollapsingNr);
        tag.putInt("stabilizingProgress", stabilizingProgress);
        tag.putInt("constructionProgress", constructionProgress);
        tag.putInt("deploymentProgress", deploymentProgress);
        tag.putInt("craftingProgress", craftingProgress);
        tag.putInt("collapsingProgress", collapsingProgress);
        tag.putInt("counter", counter);
        tag.putInt("singularityDeployedAt", singularityDeployedAt);
        tag.putBoolean("zpmCrafting", zpmCrafting);
        tag.putBoolean("zpmCrafted", zpmCrafted);
        tag.putBoolean("criticalStateImminent", criticalStateImminent);
        tag.putBoolean("criticalState", criticalState);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }
}
