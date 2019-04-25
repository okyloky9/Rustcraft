package com.oky.rustcraft.init;

import com.oky.rustcraft.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block RECYCLER = new BlockBase("recycler", Material.IRON, 1000.0f,SoundType.METAL, false);
    public static final Block RTABLE = new BlockBase("rtable", Material.IRON, 4.0f, SoundType.METAL, true);
    public static final Block WB1 = new BlockBase("wb1", Material.PISTON, 15.0f, SoundType.METAL, true);
    public static final Block WB2 = new BlockBase("wb2", Material.IRON, 40.0f, SoundType.METAL, true);
    public static final Block WB3 = new BlockBase("wb3", Material.ANVIL, 100.0f, SoundType.METAL, true);

}
