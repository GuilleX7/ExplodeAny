package io.github.guillex7.explodeany.data;

public enum QualityArmoryExplosive {
    RPG("RPG", 4.0, "#rpg"),
    HOMING_RPG("HomingRPG", 4.0, "#homingrpg"),
    MINI_NUKE("MiniNuke", 10.0, "#mininuke"),
    GRENADE("Grenade", 3.0, "#grenade"),
    STICKY_GRENADE("StickyGrenade", 3.0, "#stickygrenade"),
    PROXY_MINE("ProxyMine", 3.0, "#proxymine");

    private final String name;
    private final double explosionRadius;
    private final String coreProtectIdentifier;

    public static QualityArmoryExplosive fromName(final String name) {
        for (final QualityArmoryExplosive explosive : QualityArmoryExplosive.values()) {
            if (explosive.getName().equalsIgnoreCase(name)) {
                return explosive;
            }
        }

        return null;
    }

    QualityArmoryExplosive(final String name, final double explosionRadius, final String coreProtectIdentifier) {
        this.name = name;
        this.explosionRadius = explosionRadius;
        this.coreProtectIdentifier = coreProtectIdentifier;
    }

    public String getName() {
        return this.name;
    }

    public double getExplosionRadius() {
        return this.explosionRadius;
    }

    public String getCoreProtectIdentifier() {
        return this.coreProtectIdentifier;
    }
}
