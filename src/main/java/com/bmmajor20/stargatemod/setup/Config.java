package com.bmmajor20.stargatemod.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_POWER = "power";
    public static final String SUBCATEGORY_FIRSTBLOCK = "firstblock";
    public static final String SUBCATEGORY_ZPMCRAFTER = "zpmcrafter";
    public static final String SUBCATEGORY_SINGULARITY_SPECIALIZER = "singularity_specializer";
    public static final String SUBCATEGORY_COSMIC_EXTRACTOR = "cosmic_extractor";

    //private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    //private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue FIRSTBLOCK_MAXPOWER;                                                         // Max Power the machine is capable of holding
    public static ForgeConfigSpec.IntValue FIRSTBLOCK_GENERATE;                                                         // Energy generation per tick
    public static ForgeConfigSpec.IntValue FIRSTBLOCK_TICKS;                                                            // Amount of ticks to stabilize a Singularity
    public static ForgeConfigSpec.IntValue FIRSTBLOCK_DOUBLE_ZPM_BOOST;                                                 // Energy multiplier when 2 ZPM's are present, 1 ZPM present will be the Square Root of this value
    public static ForgeConfigSpec.IntValue FIRSTBLOCK_SEND;                                                             // The amount of Energy the machine can send out per tick

    /*
     * The ZPM Crafter is not like the other machines, it has 4 stages, and requires a 7*7 MultiBlock structure above it
     * Whenever the machine contains a block required to build the ZPM, and step 1 or 2 is active,
     *     it will place them in the world using a bit of power. They can also be placed manually by the player.
     * 1) Stabilize the Singularity you put inside.
     *     Power Usage: High
     * 2) Keep the Singularity stable whilst the MultiBlock ZPM above it is not complete yet.
     *     Power Usage: Very Low
     *     WARNING: This keeps using power until the MultiBlock is complete, so it can end up taking a lot of power!
     *     After some time the power usage will start increasing, even beyond the amount the machine can accept,
     *     when that happens, salvage what you can and run!
     * 3) The singularity gets deployed, aka teleported, to the center of the structure.
     *     Power Usage: VERY HIGH
     *     WARNING: Losing power on this step is FATAL! Expect most of your hard work to be gone
     * 4) The Singularity will slowly "suck in" the MultiBlock Structure, forming the ZPM in the progress.
     *     Power Usage: Medium
     * WARNING: When the Singularity is stuck in Phase 2 for too long, or the machine runs out of power,
     *     it'll become very unstable, eventually destroying the machine and all blocks already placed!
     *     DO NOT CRAFT A ZPM WHEN YOU DON'T HAVE THE RESOURCES!!!!
     */
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_MAXPOWER;                                                         // Max Power the machine is capable of holding
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_CONSUMPTION_PLACE_BLOCK;                                          // Energy Consumption per block placed
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_CONSUMPTION_STABILIZING;                                          // Energy consumption per tick when stabilizing a Singularity
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_CONSUMPTION_STABILIZED;                                           // Energy consumption per tick to keep the Singularity stable
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_INCREASE_STABILIZED;                                              // Energy increase per tick after the first 640 ticks to keep the Singularity stable
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_CONSUMPTION_DEPLOYING;                                            // Energy consumption per tick to deploy the stabilized Singularity
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_CONSUMPTION_CRAFTING;                                             // Energy consumption per tick to craft the ZPM
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_TICKS_PLACE_BLOCK;                                                // Minimum amount of ticks between the placement of 2 blocks
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_TICKS_STABILIZING;                                                // Amount of ticks to stabilize a Singularity
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_TICKS_DEPLOYING;                                                  // Amount of ticks to deploy the stabilized Singularity
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_TICKS_CRAFTING;                                                   // Amount of ticks to craft the ZPM
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_TICKS_DESTRUCTION;                                                // Amount of ticks before a Singularity becomes very unstable
    public static ForgeConfigSpec.IntValue ZPMCRAFTER_RECEIVE;                                                          // The amount of Energy the machine can receive per tick

    /*
     *
     */
    public static ForgeConfigSpec.IntValue SINGULARITY_SPECIALIZER_MAXPOWER;
    public static ForgeConfigSpec.IntValue SINGULARITY_SPECIALIZER_RECEIVE;

    /*
     *
     */
    public static ForgeConfigSpec.IntValue COSMIC_EXTRACTOR_MAXPOWER;
    public static ForgeConfigSpec.IntValue COSMIC_EXTRACTOR_RECEIVE;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        SERVER_BUILDER.comment("Power settings").push(CATEGORY_POWER);
        setupFirstBlockConfig(SERVER_BUILDER, CLIENT_BUILDER);
        setupZPMCrafterConfig(SERVER_BUILDER, CLIENT_BUILDER);
        setupSingularitySpecializerConfig(SERVER_BUILDER, CLIENT_BUILDER);
        setupCosmicExtractorConfig(SERVER_BUILDER, CLIENT_BUILDER);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("FirstBlock settings").push(SUBCATEGORY_FIRSTBLOCK);

        FIRSTBLOCK_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock Generator")
                .defineInRange("maxPower", 1000000, 0, Integer.MAX_VALUE);
        FIRSTBLOCK_GENERATE = SERVER_BUILDER.comment("Power generation per Diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        FIRSTBLOCK_TICKS = SERVER_BUILDER.comment("Ticks per Diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        FIRSTBLOCK_SEND = SERVER_BUILDER.comment("Power to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    private static void setupZPMCrafterConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("ZPMCrafter settings").push(SUBCATEGORY_ZPMCRAFTER);

        ZPMCRAFTER_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the ZPM Crafter")
                .defineInRange("maxPower", 10000000, 0, Integer.MAX_VALUE);
        ZPMCRAFTER_RECEIVE = SERVER_BUILDER.comment("The amount of Energy the machine can receive per tick")
                .defineInRange("receive", 32000, 0, Integer.MAX_VALUE);

        ZPMCRAFTER_CONSUMPTION_PLACE_BLOCK = SERVER_BUILDER.comment("Energy consumption per block placed")
                .defineInRange("consumptionPlaceBlock", 2500, 0, Integer.MAX_VALUE);    // 2.500 ppb
        ZPMCRAFTER_CONSUMPTION_STABILIZING = SERVER_BUILDER.comment("Energy consumption per tick to stabilize a Singularity")
                .defineInRange("consumptionStabilizing", 16000, 0, Integer.MAX_VALUE);  // 320.000 pps
        ZPMCRAFTER_CONSUMPTION_STABILIZED = SERVER_BUILDER.comment("Energy consumption per tick to keep the Singularity stable")
                .defineInRange("consumptionStabilized", 500, 0, Integer.MAX_VALUE);     // 10.000 pps
        ZPMCRAFTER_INCREASE_STABILIZED = SERVER_BUILDER.comment("Energy increase per tick after the first 640 ticks to keep the Singularity stable")
                .defineInRange("increaseStabilized", 15, 0, Integer.MAX_VALUE);         // 15 pps per tick added, so the 500 will become 515, 530, 545 etc...
        ZPMCRAFTER_CONSUMPTION_DEPLOYING = SERVER_BUILDER.comment("Energy consumption per tick to deploy the stabilized Singularity")
                .defineInRange("consumptionDeploying", 32000, 0, Integer.MAX_VALUE);    // 640.000 pps
        ZPMCRAFTER_CONSUMPTION_CRAFTING = SERVER_BUILDER.comment("Energy consumption per tick to craft the ZPM")
                .defineInRange("consumptionCrafting", 8000, 0, Integer.MAX_VALUE);      // 160.000 pps

        ZPMCRAFTER_TICKS_PLACE_BLOCK = SERVER_BUILDER.comment("Minimum amount of ticks between the placement of 2 blocks")
                .defineInRange("ticksPlaceBlock", 10, 0, Integer.MAX_VALUE);            // 2 blocks/s or 128 blocks in 64 seconds, since there are 132 blocks, at least 4 blocks have to be placed manually
        ZPMCRAFTER_TICKS_STABILIZING = SERVER_BUILDER.comment("Amount of ticks to stabilize a Singularity")
                .defineInRange("ticksStabilizing", 640, 0, Integer.MAX_VALUE);          // 32 seconds
        ZPMCRAFTER_TICKS_DEPLOYING = SERVER_BUILDER.comment("Amount of ticks to deploy the stabilized Singularity")
                .defineInRange("ticksDeploying", 80, 0, Integer.MAX_VALUE);             // 4 seconds
        ZPMCRAFTER_TICKS_CRAFTING = SERVER_BUILDER.comment("Amount of ticks to craft the ZPM")
                .defineInRange("ticksCrafting", 320, 0, Integer.MAX_VALUE);             // 16 seconds
        ZPMCRAFTER_TICKS_DESTRUCTION = SERVER_BUILDER.comment("Amount of ticks before a Singularity becomes very unstable")
                .defineInRange("ticksDestruction", 2400, 0, Integer.MAX_VALUE);         // 2 minutes

        SERVER_BUILDER.pop();
    }

    private static void setupSingularitySpecializerConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Singularity Specializer settings").push(SUBCATEGORY_SINGULARITY_SPECIALIZER);

        SINGULARITY_SPECIALIZER_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the Singularity Specializer")
                .defineInRange("maxPower", 10000000, 0, Integer.MAX_VALUE);
        SINGULARITY_SPECIALIZER_RECEIVE = SERVER_BUILDER.comment("The amount of Energy the machine can receive per tick")
                .defineInRange("receive", 32000, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    private static void setupCosmicExtractorConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Cosmic Extractor settings").push(SUBCATEGORY_COSMIC_EXTRACTOR);

        COSMIC_EXTRACTOR_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the Cosmic Extractor")
                .defineInRange("maxPower", 10000000, 0, Integer.MAX_VALUE);
        COSMIC_EXTRACTOR_RECEIVE = SERVER_BUILDER.comment("The amount of Energy the machine can receive per tick")
                .defineInRange("receive", 32000, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    /*public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }*/
}