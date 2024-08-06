package net.zombieseatflesh7.iframeoverhaul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class IFrame {
    public final DamageSource source;
    public final Entity attacker;
    public final DamageGroup group;
    public float damage;
    public final int entityAge;
    public int duration;

    public IFrame(EntityLivingBase living, DamageSource source, float damage, int entityAge) {
        this.source = source;
        this.attacker = (source.getTrueSource() instanceof EntityLivingBase) ? source.getTrueSource() : null;
        this.group = DamageGroup.getGroupFromSource(source);
        this.damage = damage;
        this.entityAge = entityAge;
        this.duration = living.maxHurtResistantTime * ((group != null) ? group.getDamageType(source).iFrameDuration : 10) / 20;
    }

    public String getDamageType() {
        return source.getDamageType();
    }

    public boolean isExpired(int age) {
        return (entityAge + duration) <= age;
    }
}
