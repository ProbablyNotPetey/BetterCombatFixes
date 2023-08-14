package com.vivividly.bc_fixes.botania;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface BCFStarcallerItem {
    void summonStar(ItemStack stack, Level level, Player player);
}
