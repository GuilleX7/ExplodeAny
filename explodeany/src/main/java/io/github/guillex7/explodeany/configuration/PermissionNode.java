package io.github.guillex7.explodeany.configuration;

public enum PermissionNode {
    CHECKTOOL_USE("explodeany.checktool.use"),
    CHECKTOOL_TOGGLE("explodeany.checktool.toggle"),
    CHECKTOOL_TOGGLE_OTHERS("explodeany.checktool.toggle.others"),
    CHECKTOOL_GIVE("explodeany.checktool.give"),
    CHECKTOOL_SET("explodeany.checktool.set"),
    CHECKTOOL_RESET("explodeany.checktool.reset"),
    CHECKTOOL_INFO("explodeany.checktool.info"),
    CONFIGURATION_SHOW("explodeany.configuration.show"),
    RELOAD("explodeany.reload");

    private final String key;

    private PermissionNode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
