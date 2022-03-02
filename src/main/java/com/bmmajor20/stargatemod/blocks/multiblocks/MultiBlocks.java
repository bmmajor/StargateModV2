package com.bmmajor20.stargatemod.blocks.multiblocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.Arrays;

import static com.bmmajor20.stargatemod.setup.Registration.*;
import static net.minecraft.block.Blocks.*;

// --[[ INFORMATION ]]--
// null = Block doesn't get checked
// AIR = There isn't allowed to be any block there, space must be empty (a.k.a. Air) in-game
public class MultiBlocks {
    public static class Stargate {
        private final Block[][] stargateLayout = {
                {AIR,                   RING_SEGMENT.get(),     RING_SEGMENT.get(),     CHEVRON_BLOCK.get(),    RING_SEGMENT.get(),     RING_SEGMENT.get(),     AIR},
                {RING_SEGMENT.get(),    CHEVRON_BLOCK.get(),    AIR,                    AIR,                    AIR,                    CHEVRON_BLOCK.get(),    RING_SEGMENT.get()},
                {RING_SEGMENT.get(),    AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    RING_SEGMENT.get()},
                {CHEVRON_BLOCK.get(),   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    CHEVRON_BLOCK.get()},
                {RING_SEGMENT.get(),    AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    RING_SEGMENT.get()},
                {RING_SEGMENT.get(),    CHEVRON_BLOCK.get(),    AIR,                    AIR,                    AIR,                    CHEVRON_BLOCK.get(),    RING_SEGMENT.get()},
                {AIR,                   RING_SEGMENT.get(),     CHEVRON_BLOCK.get(),    RING_SEGMENT.get(),     CHEVRON_BLOCK.get(),    RING_SEGMENT.get(),     AIR},
        };

        public Stargate() { }

        public Block[][] getStargateLayout() {
            return stargateLayout;
        }
    }
    public static class ZPM {
        private static final Block[][][] zpmLayout = {
                {
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  LANTEAN_CRYSTAL.get(),  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  GLASS,                  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {LANTEAN_CRYSTAL.get(), GLASS,                  GLASS,                  GLASS,                  GLASS,                  GLASS,                  LANTEAN_CRYSTAL.get()},
                        {LANTEAN_CRYSTAL.get(), GLASS,                  GLASS,                  REDSTONE_BLOCK,         GLASS,                  GLASS,                  LANTEAN_CRYSTAL.get()},
                        {LANTEAN_CRYSTAL.get(), GLASS,                  GLASS,                  GLASS,                  GLASS,                  GLASS,                  LANTEAN_CRYSTAL.get()},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  GLASS,                  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  LANTEAN_CRYSTAL.get(),  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   GLASS,                  AIR,                    END_ROD,                AIR,                    GLASS,                  AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   GLASS,                  AIR,                    AIR,                    AIR,                    GLASS,                  AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   GLASS,                  AIR,                    AIR,                    AIR,                    GLASS,                  AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   GLASS,                  AIR,                    AIR,                    AIR,                    GLASS,                  AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   GLASS,                  AIR,                    END_ROD,                AIR,                    GLASS,                  AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  AIR,                    GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
                {
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    LANTEAN_CRYSTAL.get(),  AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   LANTEAN_CRYSTAL.get(),  GLASS,                  REDSTONE_BLOCK,         GLASS,                  LANTEAN_CRYSTAL.get(),  AIR},
                        {AIR,                   AIR,                    LANTEAN_CRYSTAL.get(),  GLASS,                  LANTEAN_CRYSTAL.get(),  AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    LANTEAN_CRYSTAL.get(),  AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                },
        }; //TODO: All instances of "END_ROD,                " will become "GOLD_POWER_ROD.get(),   " (name not definitive yet)

        public ZPM() { }

        public static Block[][][] getZPMLayout() {
            return zpmLayout;
        }
        public static Block[][][] getReversedZPMLayout() {
            Block[][][] reversed = new Block[7][7][7];
            for (int i = 0; i < zpmLayout.length; i++) {
                reversed[i] = zpmLayout[zpmLayout.length - 1 - i];
            }
            return reversed;
        }

        public static int getZPMBlocksAmount() {
            int amount = 0;
            for (Block[][] layer : zpmLayout) {
                for (Block[] line : layer) {
                    for (Block block : line) {
                        if (block != null && block != AIR)
                            amount++;
                    }
                }
            }
            return amount;
        }
    }
    public static class ZPMUpgrades {
        private static final Block[][][] autoBreakerLayout = {
                {
                        {null,                  LEVER,                  null,                   LEVER,                  null},
                        {LEVER,                 AIR,                    null,                   AIR,                    LEVER},
                        {null,                  null,                   ZPMCRAFTER.get(),       null,                   null},
                        {LEVER,                 AIR,                    null,                   AIR,                    LEVER},
                        {null,                  LEVER,                  null,                   LEVER,                  null},
                },
                {
                        {null,                  CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  null},
                        {CHISELED_QUARTZ_BLOCK, STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK},
                        {CHISELED_STONE_BRICKS, NETHER_BRICK_STAIRS,    GLOWSTONE,              NETHER_BRICK_STAIRS,    CHISELED_STONE_BRICKS},
                        {CHISELED_QUARTZ_BLOCK, STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK},
                        {null,                  CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  null},
                },
                {
                        {null,                  null,                   null,                   null,                   null},
                        {null,                  AIR,                    NETHER_BRICK_STAIRS,    AIR,                    null},
                        {null,                  NETHER_BRICK_STAIRS,    AIR,                    NETHER_BRICK_STAIRS,    null},
                        {null,                  AIR,                    NETHER_BRICK_STAIRS,    AIR,                    null},
                        {null,                  null,                   null,                   null,                   null},
                }
        };
        private static final Block[][][] autoPlacerLayout = {
                {
                        {null,                  LEVER,                  null,                   LEVER,                  null},
                        {LEVER,                 AIR,                    null,                   AIR,                    LEVER},
                        {null,                  null,                   ZPMCRAFTER.get(),       null,                   null},
                        {LEVER,                 AIR,                    null,                   AIR,                    LEVER},
                        {null,                  LEVER,                  null,                   LEVER,                  null},
                },
                {
                        {AIR,                   GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             AIR},
                        {GOLD_BLOCK,            PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK},
                        {RED_CONCRETE,          DARK_PRISMARINE_STAIRS, SEA_LANTERN,            DARK_PRISMARINE_STAIRS, RED_CONCRETE},
                        {GOLD_BLOCK,            PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK},
                        {AIR,                   GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             AIR},
                },
                {
                        {null,                  null,                   null,                   null,                   null},
                        {null,                  AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    null},
                        {null,                  DARK_PRISMARINE_STAIRS, AIR,                    DARK_PRISMARINE_STAIRS, null},
                        {null,                  AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    null},
                        {null,                  null,                   null,                   null,                   null},
                }
        };
        private static final Block[][][] autoBreakerAndPlacerLayout = {
                {
                        {null,                  null,                   null,                   null,                   null,                   null,                   null},
                        {null,                  AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  POWERED_RAIL,           null,                   ZPMCRAFTER.get(),       null,                   POWERED_RAIL,           null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  null,                   null,                   null,                   null,                   null,                   null},
                },
                {
                        {null,                  null,                   DARK_PRISMARINE_STAIRS, POWERED_RAIL,           DARK_PRISMARINE_STAIRS, null,                   null},
                        {null,                  PISTON,                 CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  PISTON,                 null},
                        {DARK_PRISMARINE_STAIRS,CHISELED_QUARTZ_BLOCK,  STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK,  DARK_PRISMARINE_STAIRS},
                        {POWERED_RAIL,          CHISELED_STONE_BRICKS,  NETHER_BRICK_STAIRS,    GLOWSTONE,              NETHER_BRICK_STAIRS,    CHISELED_STONE_BRICKS,  POWERED_RAIL},
                        {DARK_PRISMARINE_STAIRS,CHISELED_QUARTZ_BLOCK,  STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK,  DARK_PRISMARINE_STAIRS},
                        {null,                  PISTON,                 CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  PISTON,                 null},
                        {null,                  null,                   DARK_PRISMARINE_STAIRS, POWERED_RAIL,           DARK_PRISMARINE_STAIRS, null,                   null},
                },
                {
                        {null,                  GOLD_BLOCK,             AIR,                    RED_CONCRETE,           AIR,                    GOLD_BLOCK,             null},
                        {GOLD_BLOCK,            AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    GOLD_BLOCK},
                        {AIR,                   AIR,                    AIR,                    NETHER_BRICK_STAIRS,    AIR,                    AIR,                    AIR},
                        {RED_CONCRETE,          AIR,                    NETHER_BRICK_STAIRS,    SEA_LANTERN,            NETHER_BRICK_STAIRS,    AIR,                    RED_CONCRETE},
                        {AIR,                   AIR,                    AIR,                    NETHER_BRICK_STAIRS,    AIR,                    AIR,                    AIR},
                        {GOLD_BLOCK,            AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    GOLD_BLOCK},
                        {null,                  GOLD_BLOCK,             AIR,                    RED_CONCRETE,           AIR,                    GOLD_BLOCK,             null},
                },
        };
        private static final Block[][][] autoPlacerAndBreakerLayout = {
                {
                        {null,                  null,                   null,                   null,                   null,                   null,                   null},
                        {null,                  AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  POWERED_RAIL,           null,                   ZPMCRAFTER.get(),       null,                   POWERED_RAIL,           null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  null,                   null,                   null,                   null,                   null,                   null},
                },
                {
                        {null,                  null,                   NETHER_BRICK_STAIRS,    POWERED_RAIL,           NETHER_BRICK_STAIRS,    null,                   null},
                        {null,                  STICKY_PISTON,          GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             STICKY_PISTON,          null},
                        {NETHER_BRICK_STAIRS,   GOLD_BLOCK,             PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK,             NETHER_BRICK_STAIRS},
                        {POWERED_RAIL,          RED_CONCRETE,           DARK_PRISMARINE_STAIRS, SEA_LANTERN,            DARK_PRISMARINE_STAIRS, RED_CONCRETE,           POWERED_RAIL},
                        {NETHER_BRICK_STAIRS,   GOLD_BLOCK,             PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK,             NETHER_BRICK_STAIRS},
                        {null,                  STICKY_PISTON,          GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             STICKY_PISTON,          null},
                        {null,                  null,                   NETHER_BRICK_STAIRS,    POWERED_RAIL,           NETHER_BRICK_STAIRS,    null,                   null},
                },
                {
                        {null,                  CHISELED_QUARTZ_BLOCK,  AIR,                    CHISELED_STONE_BRICKS,  AIR,                    CHISELED_QUARTZ_BLOCK,  null},
                        {CHISELED_QUARTZ_BLOCK, AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    CHISELED_QUARTZ_BLOCK},
                        {AIR,                   AIR,                    AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    AIR,                    AIR},
                        {CHISELED_STONE_BRICKS, AIR,                    DARK_PRISMARINE_STAIRS, GLOWSTONE,              DARK_PRISMARINE_STAIRS, AIR,                    CHISELED_STONE_BRICKS},
                        {AIR,                   AIR,                    AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    AIR,                    AIR},
                        {CHISELED_QUARTZ_BLOCK, AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    CHISELED_QUARTZ_BLOCK},
                        {null,                  CHISELED_QUARTZ_BLOCK,  AIR,                    CHISELED_STONE_BRICKS,  AIR,                    CHISELED_QUARTZ_BLOCK,  null},
                },
        };
        private static final Block[][][] enhancedAutoBreakerAndPlacerLayout = {
                {
                        {null,                  END_ROD,                AIR,                    AIR,                    AIR,                    END_ROD,                null},
                        {END_ROD,               AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    END_ROD},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {END_ROD,               AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    END_ROD},
                        {null,                  END_ROD,                AIR,                    AIR,                    AIR,                    END_ROD,                null},
                },
                {
                        {null,                  NETHER_BRICK_WALL,      null,                   null,                   null,                   NETHER_BRICK_WALL,      null},
                        {NETHER_BRICK_WALL,     AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    NETHER_BRICK_WALL},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  POWERED_RAIL,           null,                   ZPMCRAFTER.get(),       null,                   POWERED_RAIL,           null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {NETHER_BRICK_WALL,     AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    NETHER_BRICK_WALL},
                        {null,                  NETHER_BRICK_WALL,      null,                   null,                   null,                   NETHER_BRICK_WALL,      null},
                },
                {
                        {null,                  NETHER_BRICK_WALL,      DARK_PRISMARINE_STAIRS, POWERED_RAIL,           DARK_PRISMARINE_STAIRS, NETHER_BRICK_WALL,      null},
                        {NETHER_BRICK_WALL,     PISTON,                 CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  PISTON,                 NETHER_BRICK_WALL},
                        {DARK_PRISMARINE_STAIRS,CHISELED_QUARTZ_BLOCK,  STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK,  DARK_PRISMARINE_STAIRS},
                        {POWERED_RAIL,          CHISELED_STONE_BRICKS,  NETHER_BRICK_STAIRS,    GLOWSTONE,              NETHER_BRICK_STAIRS,    CHISELED_STONE_BRICKS,  POWERED_RAIL},
                        {DARK_PRISMARINE_STAIRS,CHISELED_QUARTZ_BLOCK,  STICKY_PISTON,          NETHER_BRICK_STAIRS,    STICKY_PISTON,          CHISELED_QUARTZ_BLOCK,  DARK_PRISMARINE_STAIRS},
                        {NETHER_BRICK_WALL,     PISTON,                 CHISELED_QUARTZ_BLOCK,  CHISELED_STONE_BRICKS,  CHISELED_QUARTZ_BLOCK,  PISTON,                 NETHER_BRICK_WALL},
                        {null,                  NETHER_BRICK_WALL,      DARK_PRISMARINE_STAIRS, POWERED_RAIL,           DARK_PRISMARINE_STAIRS, NETHER_BRICK_WALL,      null},
                },
                {
                        {null,                  GOLD_BLOCK,             AIR,                    RED_CONCRETE,           AIR,                    GOLD_BLOCK,             null},
                        {GOLD_BLOCK,            AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    GOLD_BLOCK},
                        {AIR,                   AIR,                    AIR,                    NETHER_BRICK_STAIRS,    AIR,                    AIR,                    AIR},
                        {RED_CONCRETE,          AIR,                    NETHER_BRICK_STAIRS,    SEA_LANTERN,            NETHER_BRICK_STAIRS,    AIR,                    RED_CONCRETE},
                        {AIR,                   AIR,                    AIR,                    NETHER_BRICK_STAIRS,    AIR,                    AIR,                    AIR},
                        {GOLD_BLOCK,            AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    GOLD_BLOCK},
                        {null,                  GOLD_BLOCK,             AIR,                    RED_CONCRETE,           AIR,                    GOLD_BLOCK,             null},
                },
        };
        private static final Block[][][] enhancedAutoPlacerAndBreakerLayout = {
                {
                        {null,                  END_ROD,                AIR,                    AIR,                    AIR,                    END_ROD,                null},
                        {END_ROD,               AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    END_ROD},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {AIR,                   AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    AIR},
                        {END_ROD,               AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    END_ROD},
                        {null,                  END_ROD,                AIR,                    AIR,                    AIR,                    END_ROD,                null},
                },
                {
                        {null,                  STONE_BRICK_WALL,       null,                   null,                   null,                   STONE_BRICK_WALL,       null},
                        {STONE_BRICK_WALL,      AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {null,                  POWERED_RAIL,           null,                   ZPMCRAFTER.get(),       null,                   POWERED_RAIL,           null},
                        {null,                  LEVER,                  AIR,                    null,                   AIR,                    LEVER,                  null},
                        {STONE_BRICK_WALL,      AIR,                    LEVER,                  POWERED_RAIL,           LEVER,                  AIR,                    null},
                        {null,                  STONE_BRICK_WALL,       null,                   null,                   null,                   STONE_BRICK_WALL,       null},
                },
                {
                        {null,                  STONE_BRICK_WALL,       NETHER_BRICK_STAIRS,    POWERED_RAIL,           NETHER_BRICK_STAIRS,    STONE_BRICK_WALL,       null},
                        {STONE_BRICK_WALL,      STICKY_PISTON,          GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             STICKY_PISTON,          STONE_BRICK_WALL},
                        {NETHER_BRICK_STAIRS,   GOLD_BLOCK,             PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK,             NETHER_BRICK_STAIRS},
                        {POWERED_RAIL,          RED_CONCRETE,           DARK_PRISMARINE_STAIRS, SEA_LANTERN,            DARK_PRISMARINE_STAIRS, RED_CONCRETE,           POWERED_RAIL},
                        {NETHER_BRICK_STAIRS,   GOLD_BLOCK,             PISTON,                 DARK_PRISMARINE_STAIRS, PISTON,                 GOLD_BLOCK,             NETHER_BRICK_STAIRS},
                        {STONE_BRICK_WALL,      STICKY_PISTON,          GOLD_BLOCK,             RED_CONCRETE,           GOLD_BLOCK,             STICKY_PISTON,          STONE_BRICK_WALL},
                        {null,                  STONE_BRICK_WALL,       NETHER_BRICK_STAIRS,    POWERED_RAIL,           NETHER_BRICK_STAIRS,    STONE_BRICK_WALL,       null},
                },
                {
                        {null,                  CHISELED_QUARTZ_BLOCK,  AIR,                    CHISELED_STONE_BRICKS,  AIR,                    CHISELED_QUARTZ_BLOCK,  null},
                        {CHISELED_QUARTZ_BLOCK, AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    CHISELED_QUARTZ_BLOCK},
                        {AIR,                   AIR,                    AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    AIR,                    AIR},
                        {CHISELED_STONE_BRICKS, AIR,                    DARK_PRISMARINE_STAIRS, GLOWSTONE,              DARK_PRISMARINE_STAIRS, AIR,                    CHISELED_STONE_BRICKS},
                        {AIR,                   AIR,                    AIR,                    DARK_PRISMARINE_STAIRS, AIR,                    AIR,                    AIR},
                        {CHISELED_QUARTZ_BLOCK, AIR,                    AIR,                    AIR,                    AIR,                    AIR,                    CHISELED_QUARTZ_BLOCK},
                        {null,                  CHISELED_QUARTZ_BLOCK,  AIR,                    CHISELED_STONE_BRICKS,  AIR,                    CHISELED_QUARTZ_BLOCK,  null},
                },
        };

        public ZPMUpgrades() { }

        public static Block[][][] getAutoBreakerLayout() {
            return autoBreakerLayout;
        }
        public static Block[][][] getAutoPlacerLayout() {
            return autoPlacerLayout;
        }
        public static Block[][][] getAutoBreakerAndPlacerLayout() {
            return autoBreakerAndPlacerLayout;
        }
        public static Block[][][] getAutoPlacerAndBreakerLayout() {
            return autoPlacerAndBreakerLayout;
        }
        public static Block[][][] getEnhancedAutoBreakerAndPlacerLayout() {
            return enhancedAutoBreakerAndPlacerLayout;
        }
        public static Block[][][] getEnhancedAutoPlacerAndBreakerLayout() {
            return enhancedAutoPlacerAndBreakerLayout;
        }
    }
}
