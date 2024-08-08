package net.zombieseatflesh7.iframeoverhaul.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.zombieseatflesh7.iframeoverhaul.DamageGroup;
import net.zombieseatflesh7.iframeoverhaul.IFrame;
import net.zombieseatflesh7.iframeoverhaul.IFrameConfig;
import net.zombieseatflesh7.iframeoverhaul.IFrameMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase
		extends Entity{
	@Shadow
	protected float lastDamage;

	protected final List<IFrame> iFrames = Lists.newArrayList();

	public MixinEntityLivingBase(World world) {
		super(world);
	}

	@Redirect(method = "attackEntityFrom", at = @At(value = "FIELD", target = "net.minecraft.entity.EntityLivingBase.hurtResistantTime:I", ordinal = 0))
	private int getHurtResistanceTime(EntityLivingBase entity, DamageSource source, float amount) {
		if ((entity instanceof EntityPlayer) ? !IFrameConfig.affectsPlayers : !IFrameConfig.affectsMobs)
			return entity.hurtResistantTime;
		if (IFrameConfig.disableIFrames)
			return 0;
		if (IFrameConfig.arrowsIgnored && source.getImmediateSource() instanceof EntityArrow)
			return 0;

		IFrame iFrame;
		updateIFrames();
		int i = findMatchingIFrame(source);

		if(i > -1) {
			iFrame = iFrames.get(i);

			if(source.getImmediateSource() instanceof EntityPlayer && source.getDamageType().equals("player")
					&& ((float)entity.hurtResistantTime <= (float)entity.maxHurtResistantTime / 2.0F //sometimes mods set the hurt timer to 0, so im reading that
					|| IFrameConfig.playerMeleePierces && IFrameMod.lastPlayerMeleeCooldown >= 0.75f)) { //fully charged attacked pierce iframes
				if(IFrameConfig.doLogging)
					Minecraft.getMinecraft().player.sendChatMessage("I-Frame piercing melee attack detected.");
				iFrames.remove(i);
				iFrames.add(new IFrame(entity, source, amount, this.ticksExisted));
				IFrameMod.lastPlayerMeleeIFrame = iFrame;
				return 0;
			}

			this.lastDamage = iFrame.damage;
			if(amount > iFrame.damage) {
				if(IFrameConfig.doLogging)
					Minecraft.getMinecraft().player.sendChatMessage("Age " + this.ticksExisted + " " + entity.getName() + " partially received " + amount + " - " + iFrame.damage + " damage of type " + source.getDamageType() + " from " + ((source.getTrueSource() != null) ? source.getTrueSource().getName() : "the world") + ".");
				iFrame.damage = amount;
			}
			else if(IFrameConfig.doLogging)
				Minecraft.getMinecraft().player.sendChatMessage("Age " + entity.ticksExisted + " " + entity.getName() + " blocked " + amount + " - " + iFrame.damage + " damage of type " + source.getDamageType() + " from " + ((source.getTrueSource() != null) ? source.getTrueSource().getName() : "the world") + ".");
			return entity.maxHurtResistantTime;
		}

		if (IFrameConfig.doLogging)
			Minecraft.getMinecraft().player.sendChatMessage("Age " + this.ticksExisted + " " + entity.getName() + " received " + amount + " damage of type " + source.getDamageType() + " from " + ((source.getTrueSource() != null) ? source.getTrueSource().getName() : "the world") + ".");

		iFrame = new IFrame(entity, source, amount, this.ticksExisted);
		iFrames.add(iFrame);
		if (source.getTrueSource() instanceof EntityPlayer && source.getDamageType().equals("player"))
			IFrameMod.lastPlayerMeleeIFrame = iFrame;
		return 0;
	}

	@Unique
	public int findMatchingIFrame(DamageSource source) {
		DamageGroup group = DamageGroup.getGroupFromSource(source);
		Entity attacker = (source.getTrueSource() instanceof EntityLivingBase) ? source.getTrueSource() : null;
		if(attacker == null) { //if world damage
			if(group != null) { //if source is part of a group
				for (int i = 0; i < iFrames.size(); i++) { //all current iframes
					if (iFrames.get(i).group == group && iFrames.get(i).attacker == null) { //if matching damage groups
						return i;
					}
				}
			}
			else //not part of a group
				for (int i = 0; i < iFrames.size(); i++)
					if (iFrames.get(i).getDamageType().equals(source.getDamageType()) && iFrames.get(i).attacker == null) //compare damage types
						return i;
		}
		else // damage from an entity
			for (int i = 0; i < iFrames.size(); i++)
				if (iFrames.get(i).attacker == attacker)
					return i;
		return -1;
	}

	@Unique
	public void updateIFrames() {
		for (int i = iFrames.size() - 1; i >= 0; i--)
		{
			if (iFrames.get(i).isExpired(this.ticksExisted)) {
				iFrames.remove(i);
			}
		}
	}
}

