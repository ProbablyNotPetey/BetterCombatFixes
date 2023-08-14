package com.vivividly.bc_fixes;

import com.mojang.logging.LogUtils;
import com.vivividly.bc_fixes.botania.BotaniaAttackHitHandler;
import com.vivividly.bc_fixes.network.PacketHandler;
import com.vivividly.bc_fixes.redstonearsenal.RSAAttackHitHandler;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(BCFixes.MOD_ID)
public class BCFixes {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "bc_fixes";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public BCFixes() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Register blocks, items, etc. here

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the client setup method for modloading
        eventBus.addListener(this::clientSetup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);



    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        if (ModList.get().isLoaded("redstone_arsenal")) {
            BetterCombatClientEvents.ATTACK_HIT.register(new RSAAttackHitHandler());
        }
        if (ModList.get().isLoaded("botania")) {
            BetterCombatClientEvents.ATTACK_HIT.register(new BotaniaAttackHitHandler());
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

}
