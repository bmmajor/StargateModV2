package com.bmmajor20.stargatemod.blocks.machines.cosmicextractor;

import com.bmmajor20.stargatemod.items.CosmicEntityFocusser;
import com.bmmajor20.stargatemod.items.CosmicParticlesContainer;
import com.bmmajor20.stargatemod.setup.Config;
import com.bmmajor20.stargatemod.setup.Registration;
import com.bmmajor20.stargatemod.tools.CustomEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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

import static com.bmmajor20.stargatemod.setup.Registration.COSMIC_EXTRACTOR_TILE;

public class CosmicExtractorTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomEnergyStorage energyStorage = createEnergy();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public CosmicExtractorTile() {
        super(COSMIC_EXTRACTOR_TILE.get());
    }

    @Override
    public void tick() {

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
                if (slot == 0 && stack.getItem() instanceof CosmicEntityFocusser) return true;
                if (slot == 1 && stack.getItem() instanceof CosmicParticlesContainer) return true;
//                if (slot == 2 && (stack.getItem() == Registration.SINGULARITY_BH.get() || stack.getItem() == Registration.SINGULARITY_WH.get())) return true;
//                if (slot == 3 && stack.getItem() == Items.REDSTONE_BLOCK) return true;
//                return slot == 4 && stack.getItem() == Items.END_ROD;
                return false;
            }
        };
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(Config.COSMIC_EXTRACTOR_MAXPOWER.get(), Config.COSMIC_EXTRACTOR_RECEIVE.get()) {
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
        }
    }

    private void readForClient(CompoundNBT tag) {
        if (tag.getInt("x") == this.pos.getX() && tag.getInt("y") == this.pos.getY() && tag.getInt("z") == this.pos.getZ()) {
            super.read(tag);
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        writeForClient(tag);
        return super.write(tag);
    }

    private void writeForClient(@Nonnull CompoundNBT tag) {

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

