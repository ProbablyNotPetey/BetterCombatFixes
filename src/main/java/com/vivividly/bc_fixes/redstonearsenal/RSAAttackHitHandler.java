package com.vivividly.bc_fixes.redstonearsenal;

import cofh.core.network.packet.server.ItemLeftClickPacket;
import cofh.redstonearsenal.item.FluxSickleItem;
import cofh.redstonearsenal.item.FluxSwordItem;
import cofh.redstonearsenal.item.FluxTridentItem;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Effectively a copy of {@link cofh.redstonearsenal.event.RSAClientEvents#handleClickInputEvent} except using the Better Combat API
 */
public class RSAAttackHitHandler implements BetterCombatClientEvents.PlayerAttackHit {

    @Override
    public void onPlayerAttackStart(LocalPlayer player, AttackHand attackHand, List<Entity> list, @Nullable Entity entity) {

        ItemStack stack = attackHand.isOffHand() ? player.getOffhandItem() : player.getMainHandItem();
        if (stack.isEmpty()) {
            return;
        }
        Item item = stack.getItem();


        // Flux Sword and Sickle
        if (item instanceof FluxSwordItem || item instanceof FluxSickleItem) {
            ItemLeftClickPacket.createAndSend();
        }
        // Flux Trident
        if (item instanceof FluxTridentItem trident) {
            if (trident.isEmpowered(stack) && trident.hasEnergy(stack, true) && trident.startPlunge(player)) {
                ItemLeftClickPacket.createAndSend();
            }
        }
    }
}
