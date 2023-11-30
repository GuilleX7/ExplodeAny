package io.github.guillex7.explodeany.compat.common.data;

import java.util.HashSet;
import java.util.Set;

public enum ExplodingVanillaEntity {
    WITHER("WITHER"),
    ENDER_CRYSTAL("ENDER_CRYSTAL"),
    PRIMED_TNT("PRIMED_TNT"),
    CREEPER("CREEPER"),
    CHARGED_CREEPER("CHARGED_CREEPER"),
    FIREBALL("FIREBALL"),
    DRAGON_FIREBALL("DRAGON_FIREBALL"),
    SMALL_FIREBALL("SMALL_FIREBALL"),
    WITHER_SKULL("WITHER_SKULL"),
    CHARGED_WITHER_SKULL("CHARGED_WITHER_SKULL");

    private final String entityName;

    private static final Set<String> ENTITY_NAMES;

    static {
        ENTITY_NAMES = new HashSet<>();
        for (ExplodingVanillaEntity entity : values()) {
            ENTITY_NAMES.add(entity.getEntityName());
        }
    }

    private ExplodingVanillaEntity(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public static boolean isEntityNameValid(String entityName) {
        return entityName != null && ENTITY_NAMES.contains(entityName);
    }
}
