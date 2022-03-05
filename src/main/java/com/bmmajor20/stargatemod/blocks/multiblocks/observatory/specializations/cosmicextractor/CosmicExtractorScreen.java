package com.bmmajor20.stargatemod.blocks.multiblocks.observatory.specializations.cosmicextractor;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.tools.Calculations;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CosmicExtractorScreen extends ContainerScreen<CosmicExtractorContainer> {

    private ResourceLocation GUI = new ResourceLocation(StargateMod.MODID, "textures/gui/cosmic_extractor_gui.png");

    public CosmicExtractorScreen(CosmicExtractorContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 176;
        this.ySize = 156;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGUI = "Cosmic Extracter";
        this.font.drawString(titleGUI, (this.xSize / 2.0f) - (Calculations.getGUIStringLength(titleGUI) / 2.0f), 10.0F, 4210752); //this.title.getFormattedText()
        String energyGUI = "Energy: " + container.getEnergy();

        drawString(Minecraft.getInstance().fontRenderer, energyGUI, this.xSize / 2 - ((energyGUI.length() * 6 - 1) / 2), 52, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bindTexture(GUI);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.blit(i, j, 0, 0, this.xSize, this.ySize);

            int maxEnergy = this.container.getMaxEnergy(), height = 60;
            if (maxEnergy > 0) {
                int remaining = (this.container.getEnergy() * height) / maxEnergy;
                this.blit(i + 8, j + 68 - remaining, 176, 68 - remaining, 16, remaining);
            }
        }
    }
}
