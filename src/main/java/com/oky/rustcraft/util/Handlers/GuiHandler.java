package com.oky.rustcraft.util.Handlers;

import com.oky.rustcraft.Rustcraft;
import com.oky.rustcraft.blocks.ContainerRecycler;
import com.oky.rustcraft.blocks.GuiRecycler;
import com.oky.rustcraft.blocks.TileEntityRecycler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Rustcraft.GUI_RECYCLER) return new ContainerRecycler(player.inventory, (TileEntityRecycler)world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Rustcraft.GUI_RECYCLER) return new GuiRecycler(player.inventory, (TileEntityRecycler)world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }
}
