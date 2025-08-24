package io.github.guillex7.explodeany.configuration;

public enum PermissionNode {
    CHECKTOOL_USE("explodeany.checktool.use"),
    CHECKTOOL_ENABLE("explodeany.checktool.enable"),
    CHECKTOOL_ENABLE_OTHERS("explodeany.checktool.enable.others"),
    CHECKTOOL_DISABLE("explodeany.checktool.disable"),
    CHECKTOOL_DISABLE_OTHERS("explodeany.checktool.disable.others"),
    CHECKTOOL_TOGGLE("explodeany.checktool.toggle"),
    CHECKTOOL_TOGGLE_OTHERS("explodeany.checktool.toggle.others"),
    CHECKTOOL_GIVE("explodeany.checktool.give"),
    CHECKTOOL_SET("explodeany.checktool.set"),
    CHECKTOOL_RESET("explodeany.checktool.reset"),
    CHECKTOOL_INFO("explodeany.checktool.info"),
    CONFIGURATION_SHOW("explodeany.configuration.show"),
    DEBUG_ENABLE("explodeany.debug.enable"),
    DEBUG_DISABLE("explodeany.debug.disable"),
    RELOAD("explodeany.reload");

    private final String key;

    PermissionNode(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
