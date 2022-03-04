package com.bmmajor20.stargatemod.setup;

import com.bmmajor20.stargatemod.blocks.*;
import com.bmmajor20.stargatemod.blocks.machines.cosmicextractor.CosmicExtractor;
import com.bmmajor20.stargatemod.blocks.machines.cosmicextractor.CosmicExtractorContainer;
import com.bmmajor20.stargatemod.blocks.machines.cosmicextractor.CosmicExtractorTile;
import com.bmmajor20.stargatemod.blocks.machines.generators.FirstBlock;
import com.bmmajor20.stargatemod.blocks.machines.generators.FirstBlockContainer;
import com.bmmajor20.stargatemod.blocks.machines.generators.FirstBlockTile;
import com.bmmajor20.stargatemod.blocks.machines.singularityspecializer.SingularitySpecializer;
import com.bmmajor20.stargatemod.blocks.machines.singularityspecializer.SingularitySpecializerContainer;
import com.bmmajor20.stargatemod.blocks.machines.singularityspecializer.SingularitySpecializerTile;
import com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafter;
import com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafterContainer;
import com.bmmajor20.stargatemod.blocks.machines.zpmcrafter.ZPMCrafterTile;
import com.bmmajor20.stargatemod.blocks.multiblocks.observatory.ObservatoryDomeBlock;
import com.bmmajor20.stargatemod.blocks.multiblocks.observatory.ObservatoryFloorBlock;
import com.bmmajor20.stargatemod.blocks.multiblocks.observatory.ObservatoryHatchBlock;
import com.bmmajor20.stargatemod.blocks.multiblocks.observatory.ObservatoryWallBlock;
import com.bmmajor20.stargatemod.blocks.multiblocks.stargate.ChevronBlock;
import com.bmmajor20.stargatemod.blocks.multiblocks.stargate.RingSegment;
import com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystal;
import com.bmmajor20.stargatemod.blocks.multiblocks.zpm.LanteanCrystalTile;
import com.bmmajor20.stargatemod.blocks.multiblocks.zpm.StableSingularity;
import com.bmmajor20.stargatemod.items.*;
import com.bmmajor20.stargatemod.items.enums.ParticleContainerProperty;
import com.bmmajor20.stargatemod.items.enums.SingularityProperty;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.bmmajor20.stargatemod.StargateMod.MODID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    //private static final DeferredRegister<ModDimension> DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        //DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<GoldPowerRing> GOLD_POWER_RING = ITEMS.register("gold_power_ring", GoldPowerRing::new);
    public static final RegistryObject<Singularity> SINGULARITY = ITEMS.register("singularity", Singularity::new);
    public static final RegistryObject<Singularity> SINGULARITY_BH = ITEMS.register("singularity_bh_properties", () -> new Singularity(SingularityProperty.BLACK_HOLE_PROPERTIES));
    public static final RegistryObject<Singularity> SINGULARITY_WH = ITEMS.register("singularity_wh_properties", () -> new Singularity(SingularityProperty.WHITE_HOLE_PROPERTIES));
    public static final RegistryObject<Singularity> SINGULARITY_BOTH = ITEMS.register("singularity_both_properties", () -> new Singularity(SingularityProperty.BOTH_PROPERTIES));
    public static final RegistryObject<ZeroPointModule> ZERO_POINT_MODULE = ITEMS.register("zero_point_module", ZeroPointModule::new);
    public static final RegistryObject<CosmicParticlesContainer> BLACK_HOLE_PARTICLES = ITEMS.register("black_hole_particles", () -> new CosmicParticlesContainer(ParticleContainerProperty.BLACK_HOLE));
    public static final RegistryObject<CosmicParticlesContainer> WHITE_HOLE_PARTICLES = ITEMS.register("white_hole_particles", () -> new CosmicParticlesContainer(ParticleContainerProperty.WHITE_HOLE));

    // Hologram Block
    public static final RegistryObject<HologramBlock> HOLOGRAM_BLOCK = BLOCKS.register("hologram_block", HologramBlock::new);
    public static final RegistryObject<Item> HOLOGRAM_BLOCK_ITEM = ITEMS.register("hologram_block", () -> new BlockItem(HOLOGRAM_BLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<HologramBlockTile>> HOLOGRAM_BLOCK_TILE = TILES.register("hologram_block", () -> TileEntityType.Builder.create(HologramBlockTile::new, HOLOGRAM_BLOCK.get()).build(null));

    // Observatory BLocks
    public static final RegistryObject<ObservatoryFloorBlock> OBSERVATORY_FLOOR_BLOCK = BLOCKS.register("observatory_floor_block", ObservatoryFloorBlock::new);
    public static final RegistryObject<ObservatoryWallBlock> OBSERVATORY_WALL_BLOCK = BLOCKS.register("observatory_wall_block", ObservatoryWallBlock::new);
    public static final RegistryObject<ObservatoryDomeBlock> OBSERVATORY_DOME_BLOCK = BLOCKS.register("observatory_dome_block", ObservatoryDomeBlock::new);
    public static final RegistryObject<ObservatoryHatchBlock> OBSERVATORY_HATCH_BLOCK = BLOCKS.register("observatory_hatch_block", ObservatoryHatchBlock::new);

    // Stargate Blocks
    public static final RegistryObject<RingSegment> RING_SEGMENT = BLOCKS.register("ring_segment", RingSegment::new);
    public static final RegistryObject<ChevronBlock> CHEVRON_BLOCK = BLOCKS.register("chevron_block", ChevronBlock::new);

    // ZPM Blocks
    public static final RegistryObject<LanteanCrystal> LANTEAN_CRYSTAL = BLOCKS.register("lantean_crystal", LanteanCrystal::new);
    public static final RegistryObject<Item> LANTEAN_CRYSTAL_ITEM = ITEMS.register("lantean_crystal", () -> new BlockItem(LANTEAN_CRYSTAL.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<LanteanCrystalTile>> LANTEAN_CRYSTAL_TILE = TILES.register("lantean_crystal", () -> TileEntityType.Builder.create(LanteanCrystalTile::new, LANTEAN_CRYSTAL.get()).build(null));
    public static final RegistryObject<StableSingularity> STABLE_SINGULARITY = BLOCKS.register("stable_singularity", StableSingularity::new);

    // Blocks
    public static final RegistryObject<LanteanControlBlock> LANTEAN_CONTROL_BLOCK = BLOCKS.register("lantean_control_block", LanteanControlBlock::new);
    public static final RegistryObject<Item> LANTEAN_CONTROL_BLOCK_ITEM = ITEMS.register("lantean_control_block", () -> new BlockItem(LANTEAN_CONTROL_BLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));

    // Machines
    public static final RegistryObject<FirstBlock> FIRSTBLOCK = BLOCKS.register("firstblock", FirstBlock::new);
    public static final RegistryObject<Item> FIRSTBLOCK_ITEM = ITEMS.register("firstblock", () -> new BlockItem(FIRSTBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<FirstBlockTile>> FIRSTBLOCK_TILE = TILES.register("firstblock", () -> TileEntityType.Builder.create(FirstBlockTile::new, FIRSTBLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<FirstBlockContainer>> FIRSTBLOCK_CONTAINER = CONTAINERS.register("firstblock", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new FirstBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ZPMCrafter> ZPMCRAFTER = BLOCKS.register("zpmcrafter", ZPMCrafter::new);
    public static final RegistryObject<Item> ZPMCRAFTER_ITEM = ITEMS.register("zpmcrafter", () -> new BlockItem(ZPMCRAFTER.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<ZPMCrafterTile>> ZPMCRAFTER_TILE = TILES.register("zpmcrafter", () -> TileEntityType.Builder.create(ZPMCrafterTile::new, ZPMCRAFTER.get()).build(null));
    public static final RegistryObject<ContainerType<ZPMCrafterContainer>> ZPMCRAFTER_CONTAINER = CONTAINERS.register("zpmcrafter", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new ZPMCrafterContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<CosmicExtractor> COSMIC_EXTRACTOR = BLOCKS.register("cosmic_extractor", CosmicExtractor::new);
    public static final RegistryObject<Item> COSMIC_EXTRACTOR_ITEM = ITEMS.register("cosmic_extractor", () -> new BlockItem(COSMIC_EXTRACTOR.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<CosmicExtractorTile>> COSMIC_EXTRACTOR_TILE = TILES.register("cosmic_extractor", () -> TileEntityType.Builder.create(CosmicExtractorTile::new, COSMIC_EXTRACTOR.get()).build(null));
    public static final RegistryObject<ContainerType<CosmicExtractorContainer>> COSMIC_EXTRACTOR_CONTAINER = CONTAINERS.register("cosmic_extractor", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new CosmicExtractorContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<SingularitySpecializer> SINGULARITY_SPECIALIZER = BLOCKS.register("singularity_specializer", SingularitySpecializer::new);
    public static final RegistryObject<Item> SINGULARITY_SPECIALIZER_ITEM = ITEMS.register("singularity_specializer", () -> new BlockItem(SINGULARITY_SPECIALIZER.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<SingularitySpecializerTile>> SINGULARITY_SPECIALIZER_TILE = TILES.register("singularity_specializer", () -> TileEntityType.Builder.create(SingularitySpecializerTile::new, SINGULARITY_SPECIALIZER.get()).build(null));
    public static final RegistryObject<ContainerType<SingularitySpecializerContainer>> SINGULARITY_SPECIALIZER_CONTAINER = CONTAINERS.register("singularity_specializer", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new SingularitySpecializerContainer(windowId, world, pos, inv, inv.player);
    }));

    //public static final RegistryObject<Unnamed1> UNNAMED1 = BLOCKS.register("unnamed1", Unnamed1::new); // ZPM Machine Upgrade 1 (Auto-Break)
    //public static final RegistryObject<Unnamed2> UNNAMED2 = BLOCKS.register("unnamed2", Unnamed2::new); // ZPM Machine Upgrade 2 (Auto-Place)
    //public static final RegistryObject<Unnamed3> UNNAMED3 = BLOCKS.register("unnamed3", Unnamed3::new); // ZPM Machine Upgrade 3 (Auto-Break-And-Place)
    //public static final RegistryObject<Unnamed4> UNNAMED4 = BLOCKS.register("unnamed4", Unnamed4::new); // ZPM Machine Upgrade 4 (Auto-Place-And-Break)
    //public static final RegistryObject<Unnamed5> UNNAMED5 = BLOCKS.register("unnamed5", Unnamed5::new); // ZPM Machine Upgrade 3 (Auto-Break-And-Place-Enhanced)
    //public static final RegistryObject<Unnamed6> UNNAMED6 = BLOCKS.register("unnamed6", Unnamed6::new); // ZPM Machine Upgrade 4 (Auto-Place-And-Break-Enhanced)








    // --[[ EXAMPLES ]]-- \\

    //public static final RegistryObject<FancyBlock> FANCYBLOCK = BLOCKS.register("fancyblock", FancyBlock::new);
    //public static final RegistryObject<Item> FANCYBLOCK_ITEM = ITEMS.register("fancyblock", () -> new BlockItem(FANCYBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    //public static final RegistryObject<TileEntityType<FancyBlockTile>> FANCYBLOCK_TILE = TILES.register("fancyblock", () -> TileEntityType.Builder.create(FancyBlockTile::new, FANCYBLOCK.get()).build(null));

    //public static final RegistryObject<MagicBlock> MAGICBLOCK = BLOCKS.register("magicblock", MagicBlock::new);
    //public static final RegistryObject<Item> MAGICBLOCK_ITEM = ITEMS.register("magicblock", () -> new BlockItem(MAGICBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    //public static final RegistryObject<TileEntityType<MagicTile>> MAGICBLOCK_TILE = TILES.register("magicblock", () -> TileEntityType.Builder.create(MagicTile::new, MAGICBLOCK.get()).build(null));

    //public static final RegistryObject<ComplexMultipartBlock> COMPLEX_MULTIPART = BLOCKS.register("complex_multipart", ComplexMultipartBlock::new);
    //public static final RegistryObject<Item> COMPLEX_MULTIPART_ITEM = ITEMS.register("complex_multipart", () -> new BlockItem(COMPLEX_MULTIPART.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    //public static final RegistryObject<TileEntityType<ComplexMultipartTile>> COMPLEX_MULTIPART_TILE = TILES.register("complex_multipart", () -> TileEntityType.Builder.create(ComplexMultipartTile::new, COMPLEX_MULTIPART.get()).build(null));

    //public static final RegistryObject<FirstItem> FIRSTITEM = ITEMS.register("firstitem", FirstItem::new);
    //public static final RegistryObject<WeirdMobEggItem> WEIRDMOB_EGG = ITEMS.register("weirdmob_egg", WeirdMobEggItem::new);

    //public static final RegistryObject<EntityType<WeirdMobEntity>> WEIRDMOB = ENTITIES.register("weirdmob", () -> EntityType.Builder.create(WeirdMobEntity::new, EntityClassification.CREATURE)
            //.size(.5f, .5f)
            //.setShouldReceiveVelocityUpdates(false)
            //.build("weirdmob"));

    //public static final RegistryObject<TutorialModDimension> DIMENSION = DIMENSIONS.register("dimension", TutorialModDimension::new);

}
