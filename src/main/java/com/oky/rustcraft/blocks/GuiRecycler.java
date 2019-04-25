package com.oky.rustcraft.blocks;

import com.oky.rustcraft.Rustcraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRecycler extends GuiContainer {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Rustcraft.MODID + ":assets/textures/gui/recyclergui.png");
    private final InventoryPlayer player;
    private final TileEntityRecycler tileentity;

    public GuiRecycler(InventoryPlayer player, TileEntityRecycler tileentity)
    {
        super(new ContainerRecycler(player, tileentity));
        this.player = player;
        this.tileentity = tileentity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String tileName = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int l = this.getRecProgressScaled(133);
        this.drawTexturedModalRect(this.guiLeft + 21, this.guiTop + 72, 1, 169, l + 1, 16);
    }

    private int getRecProgressScaled(int pixels)
    {
        int i = this.tileentity.getField(2);
        int j = this.tileentity.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

}
