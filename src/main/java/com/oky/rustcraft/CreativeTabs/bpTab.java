package com.oky.rustcraft.CreativeTabs;
import com.oky.rustcraft.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class bpTab extends CreativeTabs {

    public bpTab(String label){
        super("bpTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.RECYCLER));
    }

}
