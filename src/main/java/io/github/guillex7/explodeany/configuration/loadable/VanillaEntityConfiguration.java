package io.github.guillex7.explodeany.configuration.loadable;

import java.util.HashSet;
import java.util.Set;

public final class VanillaEntityConfiguration extends LoadableSectionConfiguration<String> {
	private final Set<String> validEntities = new HashSet<>();
	
	private VanillaEntityConfiguration () {
		super();
		validEntities.add("WITHER");
		validEntities.add("ENDER_CRYSTAL");
		validEntities.add("PRIMED_TNT");
		validEntities.add("CREEPER");
		validEntities.add("CHARGED_CREEPER");
		validEntities.add("FIREBALL");
		validEntities.add("DRAGON_FIREBALL");
		validEntities.add("SMALL_FIREBALL");
		validEntities.add("WITHER_SKULL");
		validEntities.add("CHARGED_WITHER_SKULL");
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
