package io.github.guillex7.explodeany.data;

public enum QualityArmoryExplosive {
    RPG("RPG", 4.0),
    HOMING_RPG("HomingRPG", 4.0),
    MINI_NUKE("MiniNuke", 10.0),
    GRENADE("Grenade", 3.0),
    STICKY_GRENADE("StickyGrenade", 3.0),
    PROXY_MINE("ProxyMine", 3.0);

    private final String name;
    private final double explosionRadius;

    public static QualityArmoryExplosive fromName(final String name) {
        for (final QualityArmoryExplosive explosive : QualityArmoryExplosive.values()) {
            if (explosive.getName().equalsIgnoreCase(name)) {
                return explosive;
            }
        }

        return null;
    }

    QualityArmoryExplosive(final String name, final double explosionRadius) {
        this.name = name;
        this.explosionRadius = explosionRadius;
    }

    public String getName() {
        return this.name;
    }

    public double getExplosionRadius() {
        return this.explosionRadius;
    }
}
