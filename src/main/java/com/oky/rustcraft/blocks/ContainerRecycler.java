package com.oky.rustcraft.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRecycler extends Container {

    private final TileEntityRecycler tileentity;
    private int recyclingTime, totalRecyclingTime, currentRecTime, recTime;

    public ContainerRecycler(InventoryPlayer player, TileEntityRecycler tileentity){

        this.tileentity = tileentity;
        IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new SlotItemHandler(handler, 0, 23, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 46, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 2, 69, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 3, 92, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 4, 115, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 5, 138, 18));
        this.addSlotToContainer(new SlotItemHandler(handler, 6, 23, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 7, 46, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 8, 69, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 9, 92, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 10, 115, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 11, 138, 43));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener)this.listeners.get(i);

            if(this.recyclingTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
            if(this.recTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            if(this.currentRecTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
            if(this.totalRecyclingTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
        }

        this.recyclingTime = this.tileentity.getField(2);
        this.recTime = this.tileentity.getField(0);
        this.currentRecTime = this.tileentity.getField(1);
        this.totalRecyclingTime = this.tileentity.getField(3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileentity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileentity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if(index == 3)
            {
                if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
                slot.onSlotChange(stack1, stack);
            }
            else if(index != 2 && index != 1 && index != 0)
            {
                Slot slot1 = (Slot)this.inventorySlots.get(index + 1);
            }
            else if(!this.mergeItemStack(stack1, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();

            }
            if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }
}
