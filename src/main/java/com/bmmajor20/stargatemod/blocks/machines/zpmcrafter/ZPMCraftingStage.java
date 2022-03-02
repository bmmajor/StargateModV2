package com.bmmajor20.stargatemod.blocks.machines.zpmcrafter;

public enum ZPMCraftingStage {
    AWAITING_RESOURCES, STABILIZING, AWAITING_STRUCTURE, DEPLOYING_SINGULARITY, CRAFTING, COLLAPSING_REVERSIBLE, COLLAPSING_IRREVERSIBLE
    //R                 S            W                   D                      C         B                      A
}
/*
 * Normal Stage Traversal:
 * R -> S -> W -> D -> C (-> R)
 *
 * Steps towards collapsing:
 * S -> B -|-> S -> ...
 *         |-> A
 * Can be re-stabilized by feeding it with the same amount of power it was using for the stabilization itself
 *
 * W -> A
 * This is a tricky one, build the structure in time and there's no need to worry!
 * The power usage will start increasing after a while, eventually reaching a point you need more energy than the machine can handle.
 * From there on, the machine will start losing power, salvage what you can, cause when the power reaches 0... You'll be lucky if a few blocks remain
 *
 * D -> A
 * You were deploying the singularity and lost power... There's no saving you there, sorry!
 *
 * C -> B -|-> C -> ...
 *         |-> A
 * This can be stabilized again by feeding it the power from the STABILIZING step
 */