package com.oky.rustcraft.blocks;

import com.oky.rustcraft.Rustcraft;
import com.oky.rustcraft.init.ModBlocks;
import com.oky.rustcraft.init.ModItems;
import com.oky.rustcraft.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public class BlockBase extends Block implements IHasModel {

    public void registerItemRenderer(Item item, int meta, String id){

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }

    public BlockBase(String name, Material material, float hardness, SoundType sound, boolean breakable){

        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Rustcraft.BLOCKTAB);
        setSoundType(sound);
        setHardness(hardness);

        if (breakable == true){
            ModBlocks.BLOCKS.add(this);
            ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        }else{
            setBlockUnbreakable();
            ModBlocks.BLOCKS.add(this);
            ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        }

    }

    @Override
    public void registerModels() {
        registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
