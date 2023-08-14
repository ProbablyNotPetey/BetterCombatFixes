package com.vivividly.bc_fixes.botania;

import com.vivividly.bc_fixes.network.PacketHandler;
import com.vivividly.bc_fixes.network.packet.SummonFallingStarServerPacket;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.item.equipment.tool.StarcallerItem;

import java.util.List;

public class BotaniaAttackHitHandler implements BetterCombatClientEvents.PlayerAttackHit {
    @Override
    public void onPlayerAttackStart(LocalPlayer player, AttackHand attackHand, List<Entity> list, @Nullable Entity entity) {

        ItemStack stack = attackHand.isOffHand() ? player.getOffhandItem() : player.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        Item item = stack.getItem();
        if (item instanceof StarcallerItem) {
            PacketHandler.sendToServer(new SummonFallingStarServerPacket(attackHand.isOffHand()));
        }
    }
}
