package com.oky.rustcraft.CreativeTabs;
import com.oky.rustcraft.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class blockTab extends CreativeTabs {

    public blockTab(String label){
        super("blockTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.RECYCLER);
    }

}
