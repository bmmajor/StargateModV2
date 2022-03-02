package com.bmmajor20.stargatemod.blocks.machines.generators;

import com.bmmajor20.stargatemod.items.ZeroPointModule;
import com.bmmajor20.stargatemod.setup.Config;
import com.bmmajor20.stargatemod.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import static com.bmmajor20.stargatemod.setup.Registration.FIRSTBLOCK_TILE;

public class FirstBlockTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    private int counter;

    public FirstBlockTile() {
        super(FIRSTBLOCK_TILE.get());
    }

    @Override
    public void tick() {
        assert world != null;
        if (world.isRemote) {
            return;
        }
        if (counter > 0) {
            energyStorage.addEnergy((int)(Config.FIRSTBLOCK_GENERATE.get() * generationBooster()));
            counter--;
            markDirty();
        }
        if (counter <= 0) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            if (stack.getItem() == Items.DIAMOND && canGenerate()) {
                itemHandler.extractItem(0, 1, false);
                counter = Config.FIRSTBLOCK_TICKS.get();
            }
        }
        BlockState blockState = world.getBlockState(pos);
        if (blockState.get(BlockStateProperties.POWERED) != counter > 0) {
            world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, counter > 0), 3);
        }
        sendOutPower();
    }

    private boolean canGenerate() {
        return (energyStorage.getEnergyStored() + (Config.FIRSTBLOCK_GENERATE.get() * generationBooster() * Config.FIRSTBLOCK_TICKS.get()) <= Config.FIRSTBLOCK_MAXPOWER.get());
    }

    private double generationBooster() {
        int doubleZPMBoost = 8;
        double booster = 1.0;
        ItemStack stack = itemHandler.getStackInSlot(1);
        if (stack.getItem() instanceof ZeroPointModule) {
            booster *= Math.sqrt(doubleZPMBoost);
        }
        stack = itemHandler.getStackInSlot(2);
        if (stack.getItem() instanceof ZeroPointModule) {
            booster *= Math.sqrt(doubleZPMBoost);
        }
        return booster;
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                TileEntity te = world.getTileEntity(pos.offset(direction));
                if (te != null) {
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), 7900), false);
                            capacity.addAndGet(-received);
                            energyStorage.consumeEnergy(received);
                            markDirty();
                            return capacity.get() > 0;
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
    }

    @Nonnull
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0 && stack.getItem() == Items.DIAMOND) return true;
                if (slot == 1 && stack.getItem() instanceof ZeroPointModule) return true;
                if (slot == 2 && stack.getItem() instanceof ZeroPointModule) return true;
                return false;
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
        return new CustomEnergyStorage(Config.FIRSTBLOCK_MAXPOWER.get(), 0) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }

    @Override
    public void read(CompoundNBT tag) {
        /*TileEntity te = world.getTileEntity(pos);
        if (te != null) {
            LazyOptional<IEnergyStorage> capability = te.getCapability(CapabilityEnergy.ENERGY, Direction.UP);
            capability.ifPresent(handler -> handler.receiveEnergy(10, false));

            Integer energy = capability.map(IEnergyStorage::getEnergyStored).orElse(0);
        }*/

        itemHandler.deserializeNBT(tag.getCompound("inv"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));

        counter = tag.getInt("counter");
        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());

        tag.putInt("counter", counter);
        return super.write(tag);
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

    /*@Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(Objects.requireNonNull(getType().getRegistryName()).getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        assert world != null;
        return new FirstBlockContainer(i, world, pos, playerInventory, playerEntity);
    }*/
}
