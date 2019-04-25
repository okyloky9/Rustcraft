package com.oky.rustcraft.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){
        GameRegistry.addSmelting(ModItems.HQO, new ItemStack(ModItems.HQM, 1), 0f);
    }

}
