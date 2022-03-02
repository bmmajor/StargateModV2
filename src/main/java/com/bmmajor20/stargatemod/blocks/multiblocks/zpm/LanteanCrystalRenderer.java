package com.bmmajor20.stargatemod.blocks.multiblocks.zpm;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.setup.Registration;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.List;

public class LanteanCrystalRenderer extends TileEntityRenderer<LanteanCrystalTile> {

    public static final ResourceLocation LANTEAN_CRYSTAL_TEXTURE = new ResourceLocation(StargateMod.MODID, "block/lantean_crystal");

    public LanteanCrystalRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void renderModelBrightnessColorQuads(MatrixStack.Entry matrixEntry, IVertexBuilder builder, float red, float green, float blue, float alpha, List<BakedQuad> listQuads, int combinedLightsIn, int combinedOverlayIn) {
        for(BakedQuad bakedquad : listQuads) {
            float f;
            float f1;
            float f2;

            if (bakedquad.hasTintIndex()) {
                f = red * 1f;
                f1 = green * 1f;
                f2 = blue * 1f;
            } else {
                f = 1f;
                f1 = 1f;
                f2 = 1f;
            }

            builder.addVertexData(matrixEntry, bakedquad, f, f1, f2, alpha, combinedLightsIn, combinedOverlayIn);
        }
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    @Override
    public void render(LanteanCrystalTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(LANTEAN_CRYSTAL_TEXTURE);
        IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());

        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);

        matrixStack.push();

        boolean needsTop = !tileEntity.hasLanteanCrystalOnTop();
        boolean needsBottom = !tileEntity.hasLanteanCrystalOnBottom();

        float p = 0.0625f;
        float i = 2 * p;
        float j = 2 * p;
        float k = 2 * p;
        float iT = 0.5f;
        float jT = 1-j + 0.5f;
        float kT = 0.5f;

        if (needsTop && needsBottom) {
            //Render Center
            renderDoubleSquare(builder, matrixStack, i, i, j, 1-j, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, 1-i, 1-i, j, 1-j, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, j, 1-j, k, k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, j, 1-j, 1-k, 1-k, sprite);
            //Render Top
            renderDoubleTriangleTop(builder, matrixStack, i, i, 1-j, 1-j, k, 1-k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, 1-i, 1-i, 1-j, 1-j, k, 1-k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, i, 1-i, 1-j, 1-j, k, k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, i, 1-i, 1-j, 1-j, 1-k, 1-k, iT, jT, kT, sprite);
            //Render Bottom
            renderDoubleTriangleBottom(builder, matrixStack, i, i, j, j, k, 1-k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, 1-i, 1-i, j, j, k, 1-k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, i, 1-i, j, j, k, k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, i, 1-i, j, j, 1-k, 1-k, iT, 1-jT, kT, sprite);
        } else if (needsTop && !needsBottom) {
            //Render Center
            renderDoubleSquare(builder, matrixStack, i, i, 0f, 1-j, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, 1-i, 1-i, 0f, 1-j, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, 0f, 1-j, k, k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, 0f, 1-j, 1-k, 1-k, sprite);
            //Render Top
            renderDoubleTriangleTop(builder, matrixStack, i, i, 1-j, 1-j, k, 1-k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, 1-i, 1-i, 1-j, 1-j, k, 1-k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, i, 1-i, 1-j, 1-j, k, k, iT, jT, kT, sprite);
            renderDoubleTriangleTop(builder, matrixStack, i, 1-i, 1-j, 1-j, 1-k, 1-k, iT, jT, kT, sprite);
        } else if (!needsTop && needsBottom) {
            //Render Center
            renderDoubleSquare(builder, matrixStack, i, i, j, 1f, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, 1-i, 1-i, j, 1f, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, j, 1f, k, k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, j, 1f, 1-k, 1-k, sprite);
            //Render Bottom
            renderDoubleTriangleBottom(builder, matrixStack, i, i, j, j, k, 1-k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, 1-i, 1-i, j, j, k, 1-k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, i, 1-i, j, j, k, k, iT, 1-jT, kT, sprite);
            renderDoubleTriangleBottom(builder, matrixStack, i, 1-i, j, j, 1-k, 1-k, iT, 1-jT, kT, sprite);
        } else if (!needsTop && !needsBottom) {
            //Render Center
            renderDoubleSquare(builder, matrixStack, i, i, 0f, 1f, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, 1-i, 1-i, 0f, 1f, k, 1-k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, 0f, 1f, k, k, sprite);
            renderDoubleSquare(builder, matrixStack, i, 1-i, 0f, 1f, 1-k, 1-k, sprite);
        }
        matrixStack.pop();
    }

    private void renderDoubleSquare(IVertexBuilder builder, MatrixStack matrixStack, float i1, float i2, float j1, float j2, float k1, float k2, TextureAtlasSprite sprite) {
        add(builder, matrixStack, i1, j1, k1, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStack, i2, j1, k2, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, i1, j2, k1, sprite.getMinU(), sprite.getMaxV());

        add(builder, matrixStack, i1, j2, k1, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStack, i2, j1, k2, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStack, i1, j1, k1, sprite.getMaxU(), sprite.getMinV());
    }

    private void renderDoubleTriangleTop(IVertexBuilder builder, MatrixStack matrixStack, float i1, float i2, float j1, float j2, float k1, float k2, float topX, float topY, float topZ, TextureAtlasSprite sprite) {
        add(builder, matrixStack, i1, j1, k1, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMinU(), sprite.getMaxV());

        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStack, i1, j1, k1, sprite.getMaxU(), sprite.getMinV());
    }

    private void renderDoubleTriangleBottom(IVertexBuilder builder, MatrixStack matrixStack, float i1, float i2, float j1, float j2, float k1, float k2, float topX, float topY, float topZ, TextureAtlasSprite sprite) {
        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, i1, j1, k1, sprite.getMinU(), sprite.getMaxV());

        add(builder, matrixStack, i1, j1, k1, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, i2, j2, k2, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, topX, topY, topZ, sprite.getMaxU(), sprite.getMinV());
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Registration.LANTEAN_CRYSTAL_TILE.get(), LanteanCrystalRenderer::new);
    }
}
