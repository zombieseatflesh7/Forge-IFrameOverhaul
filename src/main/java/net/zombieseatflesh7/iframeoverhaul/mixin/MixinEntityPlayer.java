package net.zombieseatflesh7.iframeoverhaul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.zombieseatflesh7.iframeoverhaul.IFrameMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

    @Shadow public abstract float getCooledAttackStrength(float adjustTicks);

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "net.minecraft.entity.player.EntityPlayer.resetCooldown()V"))
    public void onAttackTarget(Entity targetEntity, CallbackInfo ci) {
        IFrameMod.lastPlayerMeleeCooldown = getCooledAttackStrength(0.5f);
    }
}
