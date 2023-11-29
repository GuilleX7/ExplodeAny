package io.github.guillex7.explodeany.configuration;

public enum ConfigurationLocale {
    NOT_ALLOWED("NotAllowed"), ONLY_PLAYER_ALLOWED("OnlyPlayerAllowed"), PLAYER_DOESNT_EXIST("PlayerDoesntExist"),
    PLAYER_IS_OFFLINE("PlayerIsOffline"), ENTER_CHECKTOOL_MODE("EnterChecktoolMode"),
    LEAVE_CHECKTOOL_MODE("LeaveChecktoolMode"), CHECKTOOL_USE("ChecktoolUse"), CHECKTOOL_SET("ChecktoolSet"),
    CHECKTOOL_NOT_PERSISTED("ChecktoolNotPersisted"), CHECKTOOL_GIVEN("ChecktoolGiven"),
    CHECKTOOL_RESET("ChecktoolReset"), CHECKTOOL_NOT_HANDLED("ChecktoolNotHandled"),
    DISABLED_IN_THIS_WORLD("DisabledInThisWorld"), RELOADED("Reloaded");

    private final String path;

    private ConfigurationLocale(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
