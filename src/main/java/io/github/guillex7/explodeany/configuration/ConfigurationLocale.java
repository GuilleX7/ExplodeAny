package io.github.guillex7.explodeany.configuration;

public enum ConfigurationLocale {
	NOT_ALLOWED("NotAllowed"),
	ONLY_PLAYER_ALLOWED("OnlyPlayerAllowed"),
	ENTER_CHECKTOOL_MODE("EnterChecktoolMode"),
	LEAVE_CHECKTOOL_MODE("LeaveChecktoolMode"),
	CHECKTOOL_USE("ChecktoolUse"),
	CHECKTOOL_NOT_HANDLED("ChecktoolNotHandled"),
	RELOADED("Reloaded");

	private final String path;

	ConfigurationLocale(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
