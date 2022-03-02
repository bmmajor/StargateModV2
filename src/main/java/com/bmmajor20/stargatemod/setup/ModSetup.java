package com.bmmajor20.stargatemod.setup;

import com.bmmajor20.stargatemod.network.Networking;
import com.bmmajor20.stargatemod.StargateMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = StargateMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("stargatemod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.GOLD_POWER_RING.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        //CapabilityEntityCharge.register();

        /*MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ChargeEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(ChargeEventHandler::onAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(ChargeEventHandler::onDeathEvent);

        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(Registration.WEIRDMOB.get(), WeirdMobEntity.prepareAttributes().build());

            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(StargateMod.MODID, "chunkgen"),
                    TutorialChunkGenerator.CODEC);
            Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(StargateMod.MODID, "biomes"),
                    TutorialBiomeProvider.CODEC);
        });*/
    }

    /*@SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }*/
}