package com.oky.rustcraft.items;

import com.oky.rustcraft.Rustcraft;
import com.oky.rustcraft.init.ModItems;
import com.oky.rustcraft.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBase extends Item implements IHasModel {

    public void registerItemRenderer(Item item, int meta, String id){

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }

    public ItemBase(String name){

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Rustcraft.COMPTAB);

        ModItems.ITEMS.add(this);

    }

    @Override
    public void registerModels() {

        registerItemRenderer(this, 0, "inventory");

    }
}
