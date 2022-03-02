package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class ZeroPointModule extends Item {

    public ZeroPointModule() {
        super(new Item.Properties()
                .maxStackSize(1)
                .group(ModSetup.ITEM_GROUP));
    }
}
