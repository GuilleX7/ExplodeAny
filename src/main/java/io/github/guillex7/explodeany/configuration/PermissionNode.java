package io.github.guillex7.explodeany.configuration;

public enum PermissionNode {
    CHECKTOOL_USE("explodeany.checktool.use"), CHECKTOOL_TOGGLE("explodeany.checktool.toggle"),
    CHECKTOOL_GIVE("explodeany.checktool.give"), CHECKTOOL_SET("explodeany.checktool.set"),
    CHECKTOOL_RESET("explodeany.checktool.reset"), RELOAD("explodeany.reload");

    private String key;

    private PermissionNode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
