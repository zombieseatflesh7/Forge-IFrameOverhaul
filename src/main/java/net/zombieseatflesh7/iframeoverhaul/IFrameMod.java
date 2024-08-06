package net.zombieseatflesh7.iframeoverhaul;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.zombieseatflesh7.iframeoverhaul.compat.EventHandlerRLCraft;
import org.apache.logging.log4j.Logger;


@Mod(modid = IFrameMod.MOD_ID, name = IFrameMod.Name, dependencies = IFrameMod.Dependencies)
public class IFrameMod {
    public static final String MOD_ID = "iframeoverhaul";
    public static final String Name = "I-Frame Overhaul";
    public static final String Dependencies = "after:bettercombatmod@[2.0.5,);after:spartanweaponry@[1.5.3,)";

    public static Logger logger;
    public static boolean isRLCombatLoaded = false;
    public static boolean isSpartanWeaponryLoaded = false;

    public static IFrame lastPlayerMeleeIFrame;
    public static float lastPlayerMeleeCooldown = 0f;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("Starting I-Frame Overhaul");

        isRLCombatLoaded = Loader.isModLoaded("bettercombatmod");
        isSpartanWeaponryLoaded = Loader.isModLoaded("spartanweaponry");

        MinecraftForge.EVENT_BUS.register(this);
        if(isRLCombatLoaded && isSpartanWeaponryLoaded) {
            logger.info("Adding compatibility for Spartan Weaponry");
            MinecraftForge.EVENT_BUS.register(new EventHandlerRLCraft());
        }

        DamageGroup.initialize();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MOD_ID))
        {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
        }
    }

}
