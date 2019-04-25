package com.oky.rustcraft.CreativeTabs;

import com.oky.rustcraft.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class compTab extends CreativeTabs {

    public compTab(String label){
        super("compTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.FUSE);
    }

}
