package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.items.enums.EntityFocusType;
import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class CosmicEntityFocusser extends Item {

    private EntityFocusType type;

    public CosmicEntityFocusser(EntityFocusType type) {
        super(new Item.Properties()
                .maxStackSize(16)
                .group(ModSetup.ITEM_GROUP));
        this.type = type;
    }
    public CosmicEntityFocusser() {
        this(EntityFocusType.BLACK_HOLE);
    }

}