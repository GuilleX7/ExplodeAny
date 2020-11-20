package io.github.guillex7.explodeany.configuration;

public enum ConfigurationLocale {
	NOT_ALLOWED("NotAllowed", "You are not allowed to perform this action!"),
	ONLY_PLAYER_ALLOWED("OnlyPlayerAllowed", "Only players can perform this action!"),
	ENTER_CHECKTOOL_MODE("EnterChecktoolMode", "You can now right-click a block with your bare hand to display block durability"),
	LEAVE_CHECKTOOL_MODE("LeaveChecktoolMode", "You can no longer check for a block durability"),
	CHECKTOOL_USE("ChecktoolUse", "Material: %MATERIAL%, durability: %DURABILITY% / %MAX_DURABILITY%"),
	CHECKTOOL_NOT_HANDLED("ChecktoolNotHandled", "%MATERIAL% is not handled by the current configuration"),
	RELOADED("Reloaded", "Reloaded successfully!");
	
	private final String path;
	private final String defaultLocale;
	
	ConfigurationLocale(String path, String defaultLocale) {
		this.path = path;
		this.defaultLocale = defaultLocale;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getDefaultLocale() {
		return defaultLocale;
	}
}
