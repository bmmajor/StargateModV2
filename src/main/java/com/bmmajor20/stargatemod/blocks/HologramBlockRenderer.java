package com.bmmajor20.stargatemod.blocks;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.client.MyRenderType;
import com.bmmajor20.stargatemod.setup.Registration;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.List;
import java.util.Random;

public class HologramBlockRenderer extends TileEntityRenderer<HologramBlockTile> {

    public static final ResourceLocation HOLOGRAM_BLOCK_TEXTURE = new ResourceLocation(StargateMod.MODID, "block/hologram_block");

    public HologramBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
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

    @Override
    public void render(HologramBlockTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(HOLOGRAM_BLOCK_TEXTURE);
        IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());

        matrixStack.push();

//        tileEntity.addToHologramBlocks();
        BlockState renderState = tileEntity.getHologramBlock();
        // We're checking here as sometimes the tile can not have a render block as it's yet to be synced
        if( renderState == null )
            return;

        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
        IBakedModel ibakedmodel = blockRenderer.getModelForState(renderState);
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        int color = blockColors.getColor(renderState, tileEntity.getWorld(), tileEntity.getPos(), 0);
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;

        for (Direction direction : Direction.values()) {
            if (!(tileEntity.getWorld().getBlockState(tileEntity.getPos().offset(direction)).getBlock() instanceof HologramBlock)) {
                renderModelBrightnessColorQuads(matrixStack.getLast(), buffer.getBuffer(MyRenderType.HologramBlock), f, f1, f2, 0.64f, ibakedmodel.getQuads(renderState, direction, new Random(MathHelper.getPositionRandom(tileEntity.getPos())), EmptyModelData.INSTANCE), combinedLight, combinedOverlay);
            }
        }

        matrixStack.pop();
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Registration.HOLOGRAM_BLOCK_TILE.get(), HologramBlockRenderer::new);
    }
}

/*




 */