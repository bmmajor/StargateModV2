package com.bmmajor20.stargatemod.setup;

import com.bmmajor20.stargatemod.blocks.machines.generators.FirstBlockScreen;
import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.blocks.HologramBlockRenderer;
import com.bmmajor20.stargatemod.blocks.machines.singularityspecializer.SingularitySpecializerScreen;
import com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafterRenderer;
import com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafterScreen;
import com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystalRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafterRenderer.ZPMCRAFTER_TEXTURE;
import static com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystalRenderer.LANTEAN_CRYSTAL_TEXTURE;

@Mod.EventBusSubscriber(modid = StargateMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registration.FIRSTBLOCK_CONTAINER.get(), FirstBlockScreen::new);
        ScreenManager.registerFactory(Registration.ZPMCRAFTER_CONTAINER.get(), ZPMCrafterScreen::new);
        ScreenManager.registerFactory(Registration.SINGULARITY_SPECIALIZER_CONTAINER.get(), SingularitySpecializerScreen::new);
        //RenderingRegistry.registerEntityRenderingHandler(Registration.WEIRDMOB.get(), WeirdMobRenderer::new);
        //ModelLoaderRegistry.registerLoader(new ResourceLocation(MyTutorial.MODID, "fancyloader"), new FancyModelLoader());

        ZPMCrafterRenderer.register();
        LanteanCrystalRenderer.register();
        HologramBlockRenderer.register();
        //MinecraftForge.EVENT_BUS.addListener(InWorldRenderer::render);
        //MinecraftForge.EVENT_BUS.addListener(AfterLivingRenderer::render);

        //RenderTypeLookup.setRenderLayer(Registration.COMPLEX_MULTIPART.get(), RenderType.getTranslucent());
        //RenderTypeLookup.setRenderLayer(Registration.FANCYBLOCK.get(), (RenderType) -> true);

        //Minecraft.getInstance().getBlockColors().register(new FancyBlockColor(), Registration.FANCYBLOCK.get());
    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        //event.getItemColors().register((stack, i) -> 0xff0000, Registration.WEIRDMOB_EGG.get());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }

        event.addSprite(ZPMCRAFTER_TEXTURE);
        event.addSprite(LANTEAN_CRYSTAL_TEXTURE);
    }

    @SubscribeEvent
    public void onTooltipPre(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        if (item.getRegistryName().getNamespace().equals(StargateMod.MODID)) {
            event.setMaxWidth(200);
        }
    }
}