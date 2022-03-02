package com.bmmajor20.stargatemod.blocks.machines.zpmcrafter;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.blocks.multiblocks.MultiBlocks;
import com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystal;
import com.bmmajor20.stargatemod.setup.Config;
import com.bmmajor20.stargatemod.setup.Registration;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.*;

import static com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystalRenderer.LANTEAN_CRYSTAL_TEXTURE;

@OnlyIn(Dist.CLIENT)
public class ZPMCrafterRenderer extends TileEntityRenderer<ZPMCrafterTile> {

    public static final ResourceLocation ZPMCRAFTER_TEXTURE = new ResourceLocation(StargateMod.MODID, "block/zpmcrafter");
    private boolean zpmCrafting;
    private long craftingStartTime;

    public ZPMCrafterRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void renderModelBrightnessColorQuads(MatrixStack.Entry matrixEntry, IVertexBuilder builder, float red, float green, float blue, float alpha, List<BakedQuad> listQuads, int combinedLightsIn, int combinedOverlayIn) {
        for(BakedQuad bakedquad : listQuads) {
            float f;
            float f1;
            float f2;

            if (bakedquad.hasTintIndex()) {
                f = red;
                f1 = green;
                f2 = blue;
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
    public void render(@Nonnull ZPMCrafterTile tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(ZPMCRAFTER_TEXTURE);
        TextureAtlasSprite spriteCrystal = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(LANTEAN_CRYSTAL_TEXTURE);
        IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());

//        RenderType renderType = RenderType.makeType("test_sphere_1", DefaultVertexFormats.POSITION_COLOR_TEX, GL11.GL_QUADS, 256, RenderType.State.getBuilder()
//                .texture(new RenderState.TextureState(new ResourceLocation(StargateMod.MODID, "textures/models/block/gravity_shield_texture.png"), false, false))
//                //.transparency(RenderState.LIGHTNING_TRANSPARENCY)
//                //.writeMask(RenderState.COLOR_WRITE)
//                .texturing(new RenderState.TexturingState("lighting", RenderSystem::disableLighting
//                , SneakyUtils.none()))
//                .build(false)
//        );

        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);

//        IBakedModel ibakedmodel = blockRenderer.getModelForState(stateRsBlockTr);
//        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
//        int color = blockColors.getColor(stateRsBlockTr, null, null, 0);
//        float f = (float) (color >> 16 & 255) / 255.0F;
//        float f1 = (float) (color >> 8 & 255) / 255.0F;
//        float f2 = (float) (color & 255) / 255.0F;

        // ORB
        renderSphere(tileEntity);

        // CENTER
        if (zpmCrafting && !ZPMCrafterTile.getCraftingIDs().contains(tileEntity.getMachineID()))
            zpmCrafting = false;

        if (zpmCrafting || ZPMCrafterTile.getCraftingIDs().contains(tileEntity.getMachineID())) {
            if (!zpmCrafting)
                craftingStartTime = tileEntity.getWorld().getGameTime();
            zpmCrafting = true;
            showZPMCrafting(tileEntity, partialTicks, matrixStack, buffer, combinedLight, combinedOverlay, blockRenderer, itemRenderer);
        }
    }

    private void renderSphere(ZPMCrafterTile te) {
        double radius1, radius2;
        double radius = 5.5;  //radius of sphere
        double segments = 64; // the number of division in the sphere
        double angle;
        double dAngle = (Math.PI / segments);

        int teX = te.getPos().getX();
        int teY = te.getPos().getY();
        int teZ = te.getPos().getZ();
        float x;
        float y;
        //float z = 0;

        float red = 0f;
        float green = 0.25f;
        float blue = 1f;
        float alpha = 0.1f;

        GL11.glPushMatrix();
        GL11.glPushAttrib(8256);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture();

        ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        Vec3d projectedView = renderInfo.getProjectedView();
        GL11.glRotatef(renderInfo.getPitch(), 1, 0, 0); // Fixes camera rotation.
        GL11.glRotatef(renderInfo.getYaw() + 180, 0, 1, 0); // Fixes camera rotation.
        GL11.glTranslated(-projectedView.x, -projectedView.y, -projectedView.z);

        GL11.glTranslated(.5, 6.5, .5);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bufferBuilder = t.getBuffer();

        for (int i = 0; i < segments; i++) // loop latitude
        {
            angle = Math.PI / 2 - i * dAngle;
            radius1 = radius * Math.cos(angle);
            float z1 = (float) (radius * Math.sin(angle));

            angle = Math.PI / 2 - (i + 1) * dAngle;
            radius2 = radius * Math.cos(angle);
            float z2 = (float) (radius * Math.sin(angle));

            bufferBuilder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);

            for (int j = 0; j <= 2 * segments; j++) // loop longitude
            {
                double cda = Math.cos(j * dAngle);
                double sda = Math.sin(j * dAngle);

                x = (float) (radius1 * cda);
                y = (float) (radius1 * sda);
                bufferBuilder.pos(teX + x, teY + y, teZ + z1).color(red, green, blue, alpha).endVertex();
                x = (float) (radius2 * cda);
                y = (float) (radius2 * sda);
                bufferBuilder.pos(teX + x, teY + y, teZ + z2).color(red, green, blue, alpha).endVertex();
            }
            t.draw();
        }

        GlStateManager.disableBlend();
        GlStateManager.enableTexture();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private void showZPMCrafting(ZPMCrafterTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay, BlockRendererDispatcher blockRenderer, ItemRenderer itemRenderer) {
        ItemStack singularityItem = new ItemStack(Registration.SINGULARITY.get());
        IBakedModel singularityBM = itemRenderer.getItemModelWithOverrides(singularityItem, tileEntity.getWorld(), null);

        float scale = calculateValues(tileEntity, partialTicks);

        matrixStack.push();
        matrixStack.translate(0.5, 6.5, 0.5);
        matrixStack.scale(2, 2, 2);
        itemRenderer.renderItem(singularityItem, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, singularityBM);
        matrixStack.scale(.5f, .5f, .5f);
        Block[][][] zpmLayout = MultiBlocks.ZPM.getReversedZPMLayout();

        matrixStack.translate(-3.5*scale, -3.5*scale, -3.5*scale);
        matrixStack.scale(scale, scale, scale);
        for (Block[][] layer : zpmLayout) {
            for (Block[] line : layer) {
                for (Block block : line) {
                    if (block != null) {
                        if (block instanceof LanteanCrystal) {
                            //TODO: Render the Lantean Crystal (as TE?)
                            blockRenderer.renderBlock(Blocks.QUARTZ_PILLAR.getDefaultState(), matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
                        } else {
                            blockRenderer.renderBlock(block.getDefaultState(), matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
                        }
                    }
                    matrixStack.translate(1*scale, 0, 0);
                }
                matrixStack.translate(-7*scale, 0, 1*scale);
            }
            matrixStack.translate(0, 1*scale, -7*scale);
        }
        matrixStack.scale(1/scale, 1/scale, 1/scale);
        matrixStack.pop();
    }

    private float calculateValues(ZPMCrafterTile tileEntity, float partialTicks) {
        long worldTime = tileEntity.getWorld().getGameTime();
        int ticksPassed = (int)(worldTime - craftingStartTime);
        int ticksTotal = Config.ZPMCRAFTER_TICKS_CRAFTING.get();
        return 1 - ((6f*(float)ticksPassed)/(7f*(float)ticksTotal));
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Registration.ZPMCRAFTER_TILE.get(), ZPMCrafterRenderer::new);
    }

//    private void oldCode() {
//        matrixStack.push();
//        matrixStack.translate(0, 6, 0);
//        matrixStack.pop();
//
//        long time = System.currentTimeMillis();
//        int steps = 10;
//        float angle1 = 0; // = (time / steps) % 360;
//        float angle2 = 0; // = (time / steps) % 360;
//        float angle3 = 0;
//        //for (int i = 0; i < steps; i++) {
//            for (int j = 0; j < steps; j++) {
//                //angle1 = i * 360f / steps;
//                angle2 = j * 360f / steps;
//                angle3 = (time * steps) % 360;
//                //Quaternion q1 = Vector3f.XP.rotationDegrees(angle1);
//                Quaternion q2 = Vector3f.ZP.rotationDegrees(angle2);
//                Quaternion q3 = Vector3f.XP.rotationDegrees(angle3);
//                Quaternion q4 = Vector3f.YP.rotationDegrees(angle3);
//                Quaternion q5 = Vector3f.ZP.rotationDegrees(angle3);
//
//                matrixStack.push();
//                matrixStack.translate(0.5, 6.5, 0.5);
//                matrixStack.scale(7.5f, 7.5f, 7.5f);
//                //matrixStack.rotate(q1);
//                matrixStack.rotate(q2);
//                matrixStack.rotate(q3);
//                matrixStack.rotate(q4);
//                matrixStack.rotate(q5);
//                matrixStack.translate(-0.5, -0.5, -0.5);
//
//                add(builder, matrixStack, 0, 0, .5f, sprite.getMinU(), sprite.getMinV());
//                add(builder, matrixStack, 1, 0, .5f, sprite.getMaxU(), sprite.getMinV());
//                add(builder, matrixStack, 1, 1, .5f, sprite.getMaxU(), sprite.getMaxV());
//                add(builder, matrixStack, 0, 1, .5f, sprite.getMinU(), sprite.getMaxV());
//
//                add(builder, matrixStack, 0, 1, .5f, sprite.getMinU(), sprite.getMaxV());
//                add(builder, matrixStack, 1, 1, .5f, sprite.getMaxU(), sprite.getMaxV());
//                add(builder, matrixStack, 1, 0, .5f, sprite.getMaxU(), sprite.getMinV());
//                add(builder, matrixStack, 0, 0, .5f, sprite.getMinU(), sprite.getMinV());
//
//                matrixStack.pop();
//            }
//        //}
//        matrixStack.push();
//        matrixStack.translate(0, -6, 0);
//
//        BlockState lanteanCrystal = Registration.LANTEAN_CRYSTAL.get().getDefaultState();
//        BlockState glass = Blocks.GLASS.getDefaultState();
//        BlockState redstoneBlock = Blocks.REDSTONE_BLOCK.getDefaultState();
//        BlockState endRod = Blocks.END_ROD.getDefaultState();
//        BlockState singularity = Blocks.COAL_BLOCK.getDefaultState();
//        matrixStack.translate(0, 3, 0);
//        blockRenderer.renderBlock(redstoneBlock, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
//        matrixStack.translate(0, 1, 0);
//        blockRenderer.renderBlock(endRod, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
//        matrixStack.translate(0, 4, 0);
//        blockRenderer.renderBlock(endRod.with(BlockStateProperties.FACING, Direction.DOWN), matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
//        matrixStack.translate(0, 1, 0);
//        blockRenderer.renderBlock(redstoneBlock, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
//
//        matrixStack.pop();
//    }
}
