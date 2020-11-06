package io.github.guillex7.explodeany.configuration;

public enum ConfigurationLocale {
	NOT_ALLOWED("NotAllowed", "You are not allowed to perform this action!");
	
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
