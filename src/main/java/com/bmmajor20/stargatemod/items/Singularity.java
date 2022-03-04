package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.items.enums.SingularityProperty;
import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class Singularity extends Item {

    private SingularityProperty property;

    public Singularity(SingularityProperty property) {
        super(new Item.Properties()
                .maxStackSize(16)
                .group(ModSetup.ITEM_GROUP));
        this.property = property;
    }
    public Singularity() {
        this(SingularityProperty.BASIC);
    }

}
