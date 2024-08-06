package net.zombieseatflesh7.iframeoverhaul;

import net.minecraft.util.DamageSource;
import java.util.ArrayList;

/*
Damage groups (world):
FIRE: onFire, inFire, lava, hotGround, lightningBolt
THORNS: sweetBerryBush, cactus
 */
public class DamageGroup
{
    public String name;
    public DamageType[] damageTypes;

    DamageGroup(String name, DamageType[] damageTypes) {
        this.name = name;
        this.damageTypes = damageTypes;
    }

    private static final ArrayList<DamageGroup> groups = new ArrayList<>();

    public DamageType getDamageType(DamageSource source) {
        for (DamageType damageType : damageTypes) {
            if (damageType.name.equals(source.damageType))
                return damageType;
        }
        return new DamageType(source.damageType);
    }

    public static void initialize() {
        groups.add(new DamageGroup("fire", new DamageType[]{
                        new DamageType("inFire"),
                        new DamageType("onFire"),
                        new DamageType("lava"),
                        new DamageType("hotFloor"),
                        new DamageType("lightningBolt")}));
        groups.add(new DamageGroup("thorny", new DamageType[]{
                        new DamageType("sweetBerryBush"),
                        new DamageType("cactus")}));
        groups.add(new DamageGroup("velocity", new DamageType[]{
                        new DamageType("flyIntoWall", 5)}));
        //groups.add(new DamageGroup("arrow", new DamageType[]{
        //                new DamageType("arrow", 0)}));
    }

    public static DamageGroup getGroupFromSource(DamageSource source) {
        for (DamageGroup group : groups) {
            for (int i = 0; i < group.damageTypes.length; i++)
                if (group.damageTypes[i].name.equals(source.damageType))
                    return group;
        }
        return null;
    }
}

