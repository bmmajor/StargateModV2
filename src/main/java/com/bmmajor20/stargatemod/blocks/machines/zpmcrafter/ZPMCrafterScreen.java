package com.bmmajor20.stargatemod.blocks.machines.zpmcrafter;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.blocks.multiblocks.MultiBlocks;
import com.bmmajor20.stargatemod.setup.Config;
import com.bmmajor20.stargatemod.setup.Registration;
import com.bmmajor20.stargatemod.tools.Calculations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class ZPMCrafterScreen extends ContainerScreen<ZPMCrafterContainer> {

    private ResourceLocation GUI = new ResourceLocation(StargateMod.MODID, "textures/gui/zpmcrafter_gui.png");
    private Map<XYCoordinate, DirectionalLength> containmentFieldData = new HashMap<>();

    public ZPMCrafterScreen(ZPMCrafterContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 232;
        this.ySize = 232;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGUI = "ZPM Crafter";
        this.font.drawString(titleGUI, (this.xSize / 2.0f) - (Calculations.getGUIStringLength(titleGUI) / 2.0f), 6.0F, 4210752); //this.title.getFormattedText()
        this.itemRenderer.renderItemIntoGUI(new ItemStack(Registration.LANTEAN_CRYSTAL_ITEM.get()), 28, 34);            // 198, 38
        this.itemRenderer.renderItemIntoGUI(new ItemStack(Items.GLASS), 28, 56);                                        // 198, 58
        this.itemRenderer.renderItemIntoGUI(new ItemStack(Items.REDSTONE_BLOCK), 28, 78);                               // 198, 78
        this.itemRenderer.renderItemIntoGUI(new ItemStack(Items.END_ROD), 28, 100);                                     // 198, 98

        //this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 9.0F, (float)(this.ySize - 108 + 2), 4210752);
        String energyGUI = "Energy: " + this.container.getEnergy();
        String stageGUI = "Stage: " + ZPMCraftingStage.values()[this.container.getStageNr()];
        String progressNumbers = String.format("Progress: %d/%d | %d/%d | %d/%d | %d/%d | %d/%d",
                this.container.getStabilizingProgress(),
                Config.ZPMCRAFTER_TICKS_STABILIZING.get(),
                this.container.getConstructionProgress(),
                MultiBlocks.ZPM.getZPMBlocksAmount(),
                this.container.getDeploymentProgress(),
                Config.ZPMCRAFTER_TICKS_DEPLOYING.get(),
                this.container.getCraftingProgress(),
                Config.ZPMCRAFTER_TICKS_CRAFTING.get(),
                this.container.getCollapsingProgress(),
                Config.ZPMCRAFTER_TICKS_DESTRUCTION.get());
        drawString(Minecraft.getInstance().fontRenderer, energyGUI, 18, 18, 0xffffff);
        drawString(Minecraft.getInstance().fontRenderer, stageGUI, 18, 128, 0xffffff);
        drawString(Minecraft.getInstance().fontRenderer, progressNumbers, 4 , 140, 0xffffff);
        //drawString(Minecraft.getInstance().fontRenderer, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 3 , 130, 0xffffff);
        //drawString(Minecraft.getInstance().fontRenderer, "1234567890+-_=*/[]{}()", 3 , 140, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bindTexture(GUI);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.blit(i, j, 0, 0, this.xSize, this.ySize);

            int maxEnergy = this.container.getMaxEnergy(), height = 100;
            if (maxEnergy > 0) {
                int remaining = (this.container.getEnergy() * height) / maxEnergy;
                this.blit(i + 8, j + 125 - remaining, 233, 125 - remaining, 12, remaining);
            }

            renderStabilizingProgress(i, j);
        }

        //int i = this.guiLeft;
        //int j = this.guiTop;
        //int l = this.container.getCookProgressionScaled();
        //this.blit(i + 90, j + 67, 2, 234, l, 16);
    }

    private void renderStabilizingProgress(int i, int j) {
        int step = Math.round((float)this.container.getStabilizingProgress() / (float)Config.ZPMCRAFTER_TICKS_STABILIZING.get() * 8f);
        populateContainmentFieldData();
        containmentFieldData.forEach((xyCoord, dirLength) -> {
            if (dirLength.direction == Dir.HORIZONTAL)
                this.blit(i + xyCoord.x, j + xyCoord.y, 232, step, dirLength.length, 1);
            else
                this.blit(i + xyCoord.x, j + xyCoord.y, 240 + step, 0, 1, dirLength.length);
        });
    }

    private void populateContainmentFieldData() {
        // Quadrant I
        containmentFieldData.put(new XYCoordinate(121, 32), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(121, 33), new DirectionalLength(6, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(125, 34), new DirectionalLength(5, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(129, 35), new DirectionalLength(4, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(132, 36), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(134, 37), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(136, 38), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(138, 39), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(139, 40), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(141, 41), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(142, 42), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(143, 43), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(144, 44), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(145, 45), new DirectionalLength(1, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(146, 45), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(147, 46), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(148, 47), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(149, 48), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(150, 49), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(151, 51), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(152, 52), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(153, 54), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(154, 56), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(155, 58), new DirectionalLength(4, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(156, 61), new DirectionalLength(5, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(157, 64), new DirectionalLength(6, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(158, 68), new DirectionalLength(2, Dir.VERTICAL));

        // Quadrant II
        containmentFieldData.put(new XYCoordinate(73, 68), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(74, 64), new DirectionalLength(6, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(75, 61), new DirectionalLength(5, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(76, 58), new DirectionalLength(4, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(77, 56), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(78, 54), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(79, 52), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(80, 51), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(81, 49), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(82, 48), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(83, 47), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(84, 46), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(85, 45), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(86, 45), new DirectionalLength(1, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(86, 44), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(87, 43), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(88, 42), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(89, 41), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(90, 40), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(92, 39), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(93, 38), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(95, 37), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(97, 36), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(99, 35), new DirectionalLength(4, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(102, 34), new DirectionalLength(5, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(105, 33), new DirectionalLength(6, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(109, 32), new DirectionalLength(2, Dir.HORIZONTAL));

        // Quadrant III //TODO FIX COORDS
        containmentFieldData.put(new XYCoordinate(73, 80), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(74, 80), new DirectionalLength(6, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(75, 84), new DirectionalLength(5, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(76, 88), new DirectionalLength(4, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(77, 91), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(78, 93), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(79, 95), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(80, 97), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(81, 98), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(82, 100), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(83, 101), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(84, 102), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(85, 103), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(86, 104), new DirectionalLength(1, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(86, 105), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(87, 106), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(88, 107), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(89, 108), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(90, 109), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(92, 110), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(93, 111), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(95, 112), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(97, 113), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(99, 114), new DirectionalLength(4, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(102, 115), new DirectionalLength(5, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(105, 116), new DirectionalLength(6, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(109, 117), new DirectionalLength(2, Dir.HORIZONTAL));

        // Quadrant IV //TODO FIX COORDS
        containmentFieldData.put(new XYCoordinate(121, 117), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(121, 116), new DirectionalLength(6, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(125, 115), new DirectionalLength(5, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(129, 114), new DirectionalLength(4, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(132, 113), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(134, 112), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(136, 111), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(138, 110), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(139, 109), new DirectionalLength(3, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(141, 108), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(142, 107), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(143, 106), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(144, 105), new DirectionalLength(2, Dir.HORIZONTAL));
        containmentFieldData.put(new XYCoordinate(145, 104), new DirectionalLength(1, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(146, 103), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(147, 102), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(148, 101), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(149, 100), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(150, 98), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(151, 97), new DirectionalLength(2, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(152, 95), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(153, 93), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(154, 91), new DirectionalLength(3, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(155, 88), new DirectionalLength(4, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(156, 84), new DirectionalLength(5, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(157, 80), new DirectionalLength(6, Dir.VERTICAL));
        containmentFieldData.put(new XYCoordinate(158, 80), new DirectionalLength(2, Dir.VERTICAL));
    }

    private static class XYCoordinate {
        private final int x;
        private final int y;
        private XYCoordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private static class DirectionalLength {
        private final int length;
        private final Dir direction;
        private DirectionalLength(int length, Dir direction) {
            this.length = length;
            this.direction = direction;
        }
    }
    private enum Dir {
        VERTICAL, HORIZONTAL
    }
}