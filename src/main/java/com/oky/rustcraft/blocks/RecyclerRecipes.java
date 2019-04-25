package com.oky.rustcraft.blocks;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.oky.rustcraft.init.ModItems;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class RecyclerRecipes {

    private static final RecyclerRecipes INSTANCE = new RecyclerRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> recList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static RecyclerRecipes getInstance()
    {
        return INSTANCE;
    }

    private RecyclerRecipes()
    {
        addRecyclerRecipe(new ItemStack(ModItems.FUSE), new ItemStack(ModItems.FUSE), new ItemStack(ModItems.SCRAP), 0F);
    }


    public void addRecyclerRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience)
    {
        if(getRecyclerResult(input1, input2) != ItemStack.EMPTY) return;
        this.recList.put(input1, input2, result);
        this.experienceList.put(result, Float.valueOf(experience));
    }

    public ItemStack getRecyclerResult(ItemStack input1, ItemStack input2)
    {
        for(Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.recList.columnMap().entrySet())
        {
            if(this.compareItemStacks(input1, (ItemStack)entry.getKey()))
            {
                for(Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
                {
                    if(this.compareItemStacks(input2, (ItemStack)ent.getKey()))
                    {
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList()
    {
        return this.recList;
    }

    public float getRecExperience(ItemStack stack)
    {
        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if(this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return ((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0F;
    }

}
