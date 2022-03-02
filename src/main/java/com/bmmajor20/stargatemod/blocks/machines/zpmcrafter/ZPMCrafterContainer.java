package com.bmmajor20.stargatemod.blocks.machines.zpmcrafter;

import com.bmmajor20.stargatemod.items.Singularity;
import com.bmmajor20.stargatemod.setup.Registration;
import com.bmmajor20.stargatemod.tools.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.bmmajor20.stargatemod.setup.Registration.ZPMCRAFTER;
import static com.bmmajor20.stargatemod.setup.Registration.ZPMCRAFTER_CONTAINER;

public class ZPMCrafterContainer extends Container {

    private TileEntity tileEntity;
    private IItemHandler playerInventory;
    private PlayerEntity playerEntity;

    public ZPMCrafterContainer(int windowID, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ZPMCRAFTER_CONTAINER.get(), windowID);
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 108, 67));    // 120, 68
                addSlot(new SlotItemHandler(h, 1, 28, 34));     // 86, 34
                addSlot(new SlotItemHandler(h, 2, 28, 56));     // 86, 102
                addSlot(new SlotItemHandler(h, 3, 28, 78));     // 154, 34
                addSlot(new SlotItemHandler(h, 4, 28, 100));    // 154, 102
            });
        }
        layoutPlayerInventorySlots(36, 150);
        trackPower();
        trackStage();
        trackProgress();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ZPMCRAFTER.get());
    }

    private void trackPower() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value << 16));
                });
            }
        });
    }

    private void trackStage() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getStageNr();
            }

            @Override
            public void set(int value) {
                ((ZPMCrafterTile)tileEntity).setStageNr(value);
            }
        });
    }

    private void trackProgress() {
        trackIntArray(new IIntArray() {
            @Override
            public int get(int i) {
                switch (i) {
                    case 0:
                        return getStabilizingProgress();
                    case 1:
                        return getConstructionProgress();
                    case 2:
                        return getDeploymentProgress();
                    case 3:
                        return getCraftingProgress();
                    case 4:
                        return getCollapsingProgress();
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int i, int v) {
                switch (i) {
                    case 0: // Stabilizing progress
                        ((ZPMCrafterTile)tileEntity).setStabilizingProgress(v);
                        break;
                    case 1: // Structure completion progress
                        ((ZPMCrafterTile)tileEntity).setConstructionProgress(v);
                        break;
                    case 2: // Deployment progress
                        ((ZPMCrafterTile)tileEntity).setDeploymentProgress(v);
                        break;
                    case 3: // Crafting progress
                        ((ZPMCrafterTile)tileEntity).setCraftingProgress(v);
                        break;
                    case 4: // Reversible collapsing progress
                        ((ZPMCrafterTile)tileEntity).setCollapsingProgress(v);
                        break;
                }
            }

            @Override
            public int size() {
                return 5;
            }
        });
    }

    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    public int getMaxEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
    public int getStageNr() {
        return ((ZPMCrafterTile)tileEntity).getStageNr();
    }
    public int getStabilizingProgress() {
        return ((ZPMCrafterTile)tileEntity).getStabilizingProgress();
    }
    public int getConstructionProgress() {
        return ((ZPMCrafterTile)tileEntity).getConstructionProgress();
    }
    public int getDeploymentProgress() {
        return ((ZPMCrafterTile)tileEntity).getDeploymentProgress();
    }
    public int getCraftingProgress() {
        return ((ZPMCrafterTile)tileEntity).getCraftingProgress();
    }
    public int getCollapsingProgress() {
        return ((ZPMCrafterTile)tileEntity).getCollapsingProgress();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        int tileInvSize = 5;
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if (index < tileInvSize) {
                if (!this.mergeItemStack(stack, tileInvSize, tileInvSize+36, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemStack);
            } else {
                if (stack.getItem() == Registration.SINGULARITY.get()) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == Registration.LANTEAN_CRYSTAL_ITEM.get()) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == Items.GLASS) {
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == Items.REDSTONE_BLOCK) {
                    if (!this.mergeItemStack(stack, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == Items.END_ROD) {
                    if (!this.mergeItemStack(stack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < tileInvSize+27) {
                    if (!this.mergeItemStack(stack, tileInvSize+27, tileInvSize+36, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < tileInvSize+36) {
                    if (!this.mergeItemStack(stack, tileInvSize, tileInvSize+27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemStack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
