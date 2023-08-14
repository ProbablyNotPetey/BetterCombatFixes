package com.vivividly.bc_fixes.network;

import com.vivividly.bc_fixes.BCFixes;
import com.vivividly.bc_fixes.network.packet.SummonFallingStarServerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(BCFixes.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = network;

        network.messageBuilder(SummonFallingStarServerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SummonFallingStarServerPacket::new)
                .encoder(SummonFallingStarServerPacket::toBytes)
                .consumerMainThread(SummonFallingStarServerPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
