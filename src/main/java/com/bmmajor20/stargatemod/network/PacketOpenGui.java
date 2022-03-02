package com.bmmajor20.stargatemod.network;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenGui {

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        //ctx.get().enqueueWork(SpawnerScreen::open);
        return true;
    }

}