package com.oky.rustcraft.util.Handlers;

import com.oky.rustcraft.blocks.TileEntityRecycler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

    public static void registerTileEntities(){

        GameRegistry.registerTileEntity(TileEntityRecycler.class, "recycler");

    }

}
