package com.vivividly.bc_fixes.mixin;

import com.vivividly.bc_fixes.botania.BCFStarcallerItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.StarcallerItem;

@Mixin(StarcallerItem.class)
public class StarcallerMixin implements BCFStarcallerItem {

    @Shadow(remap = false)
    private void summonFallingStar(ItemStack stack, Level world, Player player) {}


    @Override
    public void summonStar(ItemStack stack, Level level, Player player) {
        long timeSinceLast = level.getGameTime() - ItemNBTHelper.getLong(stack, "lastTriggerTime", level.getGameTime());
        if (timeSinceLast > 12L && player.getMainHandItem() == stack && !level.isClientSide) {
            ItemNBTHelper.setLong(stack, "lastTriggerTime", level.getGameTime());
            this.summonFallingStar(stack, level, player);
        }
    }
}
