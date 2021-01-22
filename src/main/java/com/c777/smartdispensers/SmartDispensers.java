package com.c777.smartdispensers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("smartdispensers")
public class SmartDispensers
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public SmartDispensers() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigData.SERVER_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        SmartDispenserBehaviorRegistration.registerSmartDispenserBehaviors(LOGGER);
    }

}
