package io.github.guillex7.explodeany.configuration.loadable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class VanillaEntityConfiguration extends LoadableSectionConfiguration<String> {
	private final Set<String> validEntities = new HashSet<>(
			Arrays.asList("WITHER", "ENDER_CRYSTAL", "PRIMED_TNT", "CREEPER", "CHARGED_CREEPER", "FIREBALL",
					"DRAGON_FIREBALL", "SMALL_FIREBALL", "WITHER_SKULL", "CHARGED_WITHER_SKULL"));

	private VanillaEntityConfiguration() {
		super();
	};

	public static VanillaEntityConfiguration empty() {
		return new VanillaEntityConfiguration();
	}

	@Override
	public String getSectionPath() {
		return "VanillaEntity";
	}

	@Override
	public String getEntityName(String entity) {
		return entity;
	}

	@Override
	public String getEntityFromName(String name) {
		return name.toUpperCase();
	}

	@Override
	public boolean checkEntityTypeIsValid(String entity) {
		return entity != null && validEntities.contains(entity);
	}
}
