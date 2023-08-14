package com.vivividly.bc_fixes.network.packet;

import com.vivividly.bc_fixes.BCFixes;
import com.vivividly.bc_fixes.botania.BCFStarcallerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import vazkii.botania.common.item.equipment.tool.StarcallerItem;

import java.util.function.Supplier;

public class SummonFallingStarServerPacket extends Packet {

    private boolean isOffHand;

    public SummonFallingStarServerPacket(boolean isOffHand) {
        this.isOffHand = isOffHand;
    }
    public SummonFallingStarServerPacket(FriendlyByteBuf buf) {
        isOffHand = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isOffHand);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context ctx = sup.get();
        ctx.enqueueWork(() -> {
            BCFixes.LOGGER.info("Packet received. Summoning falling star...");
            ServerPlayer player = ctx.getSender();
            ServerLevel level = player.getLevel();
            ItemStack stack = isOffHand ? player.getOffhandItem() : player.getMainHandItem();
            Item item = stack.getItem();
            if(item instanceof StarcallerItem) {
                BCFStarcallerItem starcallerItem = (BCFStarcallerItem)item;
                starcallerItem.summonStar(stack, level, player);
            }
        });
        return true;
    }
}
