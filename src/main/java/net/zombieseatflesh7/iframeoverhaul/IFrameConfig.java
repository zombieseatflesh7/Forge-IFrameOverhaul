package net.zombieseatflesh7.iframeoverhaul;

import net.minecraftforge.common.config.Config;

@Config(modid = IFrameMod.MOD_ID)
public class IFrameConfig {
    @Config.Name("Enable Mod")
    @Config.Comment({"Should i-frame changes be applied"})
    public static boolean enabled = true;

    @Config.Name("Chat Logs")
    @Config.Comment({"Prints a ton of annoying messages to chat",
            "For debugging"})
    public static boolean doLogging = false;

    @Config.Name("Disable All I-Frames")
    @Config.Comment({"Disables i-frames on all damage sources",
            "This is like really unfair"})
    public static boolean disableIFrames = false;

    @Config.Name("Arrows Ignore I-Frames")
    @Config.Comment({"Arrows never bounce off their target"})
    public static boolean arrowsIgnored = false;

    @Config.Name("Player Melee Ignores I-Frames")
    @Config.Comment({"Fully charged player melee attacks will ignore i-frames"})
    public static boolean playerMeleePierces = false;


}
