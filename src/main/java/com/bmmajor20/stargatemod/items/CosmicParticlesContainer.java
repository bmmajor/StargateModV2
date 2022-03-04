package com.bmmajor20.stargatemod.items;

import com.bmmajor20.stargatemod.items.enums.ParticleContainerProperty;
import com.bmmajor20.stargatemod.setup.ModSetup;
import net.minecraft.item.Item;

public class CosmicParticlesContainer extends Item {

    private ParticleContainerProperty property;

    public CosmicParticlesContainer(ParticleContainerProperty property) {
        super(new Item.Properties()
                .maxStackSize(16)
                .group(ModSetup.ITEM_GROUP));
        this.property = property;
    }
    public CosmicParticlesContainer() {
        this(ParticleContainerProperty.BLACK_HOLE);
    }

}
