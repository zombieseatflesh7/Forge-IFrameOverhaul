package net.zombieseatflesh7.iframeoverhaul;

import net.minecraftforge.common.config.Config;

@Config(modid = IFrameMod.MOD_ID)
public class IFrameConfig {

    @Config.Name("Affects Players")
    @Config.Comment({"Toggles i-frame changes for players",
            "Leave this on, coward"})
    public static boolean affectsPlayers = true;

    @Config.Name("Affects Mobs")
    @Config.Comment({"Toggles i-frame changes for mobs",
            "Makes combat a little easier"})
    public static boolean affectsMobs = true;

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
    @Config.Comment({"Mostly charged player melee attacks will ignore i-frames"})
    public static boolean playerMeleePierces = false;


}
