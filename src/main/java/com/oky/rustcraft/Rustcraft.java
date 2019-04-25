package com.oky.rustcraft;

import com.oky.rustcraft.CreativeTabs.blockTab;
import com.oky.rustcraft.CreativeTabs.bpTab;
import com.oky.rustcraft.CreativeTabs.compTab;
import com.oky.rustcraft.init.ModRecipes;
import com.oky.rustcraft.util.Handlers.RegistryHandler;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Rustcraft.MODID, name = Rustcraft.NAME, version = Rustcraft.VERSION)
public class Rustcraft
{
    @Mod.Instance
    public static Rustcraft instance;

    public static final String MODID = "rustcraft";
    public static final String NAME = "Rustcraft";
    public static final String VERSION = "@VERSION@";
    public static final int GUI_RECYCLER = 0;
    public static final CreativeTabs COMPTAB = new compTab("compTab");
    public static final CreativeTabs BLOCKTAB = new blockTab("blockTab");
    public static final CreativeTabs BPTAB = new bpTab("bpTab");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModRecipes.init();
    }
}
