package com.oky.rustcraft.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityRecycler extends TileEntity implements ITickable
{
    private ItemStackHandler handler = new ItemStackHandler(11);
    private String customName;
    private ItemStack recycling = ItemStack.EMPTY;

    private int recTime;
    private int currentRecTime;
    private int recyclingTime;
    private int totalRecyclingTime = 200;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        else return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
        return super.getCapability(capability, facing);
    }

    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.recycler");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.recTime = compound.getInteger("RecTime");
        this.recyclingTime = compound.getInteger("RecyclingTime");
        this.totalRecyclingTime = compound.getInteger("RecyclingTimeTotal");
        //this.currentRecTime = getItemBurnTime((ItemStack)this.handler.getStackInSlot(2));

        if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("RecTime", (short)this.recTime);
        compound.setInteger("RecyclingTime", (short)this.recyclingTime);
        compound.setInteger("RecyclingTimeTotal", (short)this.totalRecyclingTime);
        compound.setTag("Inventory", this.handler.serializeNBT());

        if(this.hasCustomName()) compound.setString("CustomName", this.customName);
        return compound;
    }

    public boolean isRecycling()
    {
        return this.recTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isRecycling(TileEntityRecycler te)
    {
        return te.getField(0) > 0;
    }

    public void update()
    {
        if(this.isRecycling())
        {
            --this.recTime;
            Recycler.setState(true, world, pos);
        }

        ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
        //ItemStack fuel = this.handler.getStackInSlot(2);

        if(this.isRecycling() && !this.handler.getStackInSlot(0).isEmpty() || this.handler.getStackInSlot(1).isEmpty())
        {
            if(!this.isRecycling() && this.canRec())
            {
                //this.recyclingTime = getItemBurnTime(fuel);
                this.currentRecTime = recTime;
            }
        }

        if(this.isRecycling() && this.canRec() && recyclingTime > 0)
        {
            recyclingTime++;
            if(recyclingTime == totalRecyclingTime)
            {
                if(handler.getStackInSlot(3).getCount() > 0)
                {
                    handler.getStackInSlot(3).grow(1);
                }
                else
                {
                    handler.insertItem(3, recycling, false);
                }

                recycling = ItemStack.EMPTY;
                recyclingTime = 0;
                return;
            }
        }
        else
        {
            if(this.canRec() && this.isRecycling())
            {
                ItemStack output = RecyclerRecipes.getInstance().getRecyclerResult(inputs[0], inputs[1]);
                if(!output.isEmpty())
                {
                    recycling = output;
                    recyclingTime++;
                    inputs[0].shrink(1);
                    inputs[1].shrink(1);
                    handler.setStackInSlot(0, inputs[0]);
                    handler.setStackInSlot(1, inputs[1]);
                }
            }
        }
    }

    private boolean canRec()
    {
        if(((ItemStack)this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(1)).isEmpty()) return false;
        else
        {
            ItemStack result = RecyclerRecipes.getInstance().getRecyclerResult((ItemStack)this.handler.getStackInSlot(0), (ItemStack)this.handler.getStackInSlot(1));
            if(result.isEmpty()) return false;
            else
            {

                for (int i=0; i < 4; i++){
                    ItemStack output = (ItemStack)this.handler.getStackInSlot(i);
                    if(output.isEmpty()) return true;
                    if(!output.isItemEqual(result)) return false;
                    int res = output.getCount() + result.getCount();
                    return res <= 64 && res <= output.getMaxStackSize();
                }
                return true;
            }
        }
    }

    /*public static int getItemBurnTime(ItemStack fuel)
    {
        if(fuel.isEmpty()) return 0;
        else
        {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) return 150;
                if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
                if (block == Blocks.COAL_BLOCK) return 16000;
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;

            return GameRegistry.getFuelValue(fuel);
        }
    }

    public static boolean isItemFuel(ItemStack fuel)
    {
        return getItemBurnTime(fuel) > 0;
    }
    */

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.recTime;
            case 1:
                return this.currentRecTime;
            case 2:
                return this.recTime;
            case 3:
                return this.totalRecyclingTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.recTime = value;
                break;
            case 1:
                this.currentRecTime = value;
                break;
            case 2:
                this.recyclingTime = value;
                break;
            case 3:
                this.totalRecyclingTime = value;
        }
    }
}
