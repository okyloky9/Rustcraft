package com.oky.rustcraft.util.Handlers;

import com.oky.rustcraft.Rustcraft;
import com.oky.rustcraft.init.ModBlocks;
import com.oky.rustcraft.init.ModItems;
import com.oky.rustcraft.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){

        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){

        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityHandler.registerTileEntities();

    }

    public static void initRegistries(){

        NetworkRegistry.INSTANCE.registerGuiHandler(Rustcraft.instance, new GuiHandler());

    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){

        for (Item item : ModItems.ITEMS){
            if (item instanceof IHasModel){
                ((IHasModel)item).registerModels();
            }
        }

        for (Block block : ModBlocks.BLOCKS){
            if (block instanceof IHasModel){
                ((IHasModel)block).registerModels();
            }
        }

    }

}
