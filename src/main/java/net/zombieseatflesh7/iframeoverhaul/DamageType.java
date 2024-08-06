package net.zombieseatflesh7.iframeoverhaul;

public class DamageType {
    public final String name;
    public final int iFrameDuration;

    public DamageType(String name)
    {
        this.name = name;
        this.iFrameDuration = 10;
    }
    public DamageType(String name, int iFrameDuration)
    {
        this.name = name;
        this.iFrameDuration = iFrameDuration;
    }
}
