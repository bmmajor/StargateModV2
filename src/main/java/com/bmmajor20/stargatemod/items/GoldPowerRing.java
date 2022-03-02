package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.StargateMod;
import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class GoldPowerRing extends Item {

    public GoldPowerRing() {
        super(new Item.Properties()
                .maxStackSize(64)
                .group(ModSetup.ITEM_GROUP));
    }
}
