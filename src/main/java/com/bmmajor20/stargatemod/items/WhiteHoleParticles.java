package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class WhiteHoleParticles extends Item {

    public WhiteHoleParticles() {
        super(new Item.Properties()
                .maxStackSize(16)
                .group(ModSetup.ITEM_GROUP));
    }
}
