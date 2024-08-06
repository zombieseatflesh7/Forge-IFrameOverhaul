package net.zombieseatflesh7.iframeoverhaul.compat;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import bettercombat.mod.event.RLCombatSweepEvent;
import com.oblivioussp.spartanweaponry.api.IWeaponPropertyContainer;
import com.oblivioussp.spartanweaponry.api.WeaponProperties;
import com.oblivioussp.spartanweaponry.util.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.zombieseatflesh7.iframeoverhaul.IFrameConfig;
import net.zombieseatflesh7.iframeoverhaul.IFrameMod;

public class EventHandlerRLCraft {

    @SubscribeEvent
    public void onSweepAttack(RLCombatSweepEvent ev) { //quickstrike compatibility
        Item weapon = ev.getItemStack().getItem();
        if(weapon instanceof IWeaponPropertyContainer
                && ((IWeaponPropertyContainer<?>)weapon).hasWeaponProperty(WeaponProperties.QUICK_STRIKE)
                && IFrameMod.lastPlayerMeleeIFrame != null) {
            EntityLivingBase living = (EntityLivingBase)ev.getTargetEntity();
            int newDuration = (int)Math.ceil(((double)((ConfigHandler.quickStrikeHurtResistTicks - 10) * living.maxHurtResistantTime)) / 20d);
            newDuration = Math.min(IFrameMod.lastPlayerMeleeIFrame.duration, newDuration);
            IFrameMod.lastPlayerMeleeIFrame.duration = newDuration;
            IFrameMod.lastPlayerMeleeIFrame = null;

            if(IFrameConfig.doLogging)
                Minecraft.getMinecraft().player.sendChatMessage("Quickstrike Attack Detected. Setting I-Frame duration to " + newDuration);
        }
    }

    @SubscribeEvent
    public void onRLCombatModifyDamageEvent(RLCombatModifyDamageEvent ev) {
        IFrameMod.lastPlayerMeleeCooldown = ev.getCooledStrength();
    }
}
