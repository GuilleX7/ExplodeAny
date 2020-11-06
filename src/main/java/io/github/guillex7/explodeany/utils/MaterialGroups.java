package io.github.guillex7.explodeany.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

public class MaterialGroups {
	@SafeVarargs
	public static final <T> Set<T> createStaticHashSet(T... objs) {
		Set<T> set = new HashSet<T>();
		Collections.addAll(set, objs);
		return set;
	}
	
	private static final String materialColorsKeyword = "ANYCOLOR";
	private static final String woodTypesKeyword = "ANYWOODTYPE";

	private static final Set<String> materialColors = createStaticHashSet(
			"WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW",
			"LIME", "PINK", "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE",
			"BLUE", "BROWN", "GREEN", "RED", "BLACK"
			);
	
	private static final Set<String> woodTypes = createStaticHashSet(
			"OAK", "SPRUCE", "BIRCH", "JUNGLE", "ACACIA", "DARK_OAK"
			);
	
	private static final Set<String> getMaterialColors() {
		return materialColors;
	}
	
	private static final Set<String> getWoodTypes() {
		return woodTypes;
	}
	
	public static final String getMaterialColorsKeyword() {
		return materialColorsKeyword;
	}
	
	public static final String getWoodTypesKeyword() {
		return woodTypesKeyword;
	}
	
	public static Set<Material> expandKeywordsToMaterials(String materialName) {
		Set<Material> allMaterials = new HashSet<Material>();
		allMaterials.addAll(expandColors(materialName));
		allMaterials.addAll(expandWoodTypes(materialName));
		return allMaterials;
	}
	
	private static Set<Material> expandColors(String materialName) {
		Set<Material> coloredMaterials = new HashSet<Material>();
		if (materialName.contains(getMaterialColorsKeyword())) {
			for (String materialColor : getMaterialColors()) {
				Material coloredMaterial;
				try {
					coloredMaterial = Material.valueOf(
						materialName.replaceAll(getMaterialColorsKeyword(), materialColor)
							);
				} catch (Exception e) {
					continue;
				}
				coloredMaterials.add(coloredMaterial);
			}
		}
		return coloredMaterials;
	}
	
	private static Set<Material> expandWoodTypes(String materialName) {
		Set<Material> woodTypedMaterials = new HashSet<Material>();
		if (materialName.contains(getWoodTypesKeyword())) {
			for (String woodType : getWoodTypes()) {
				Material woodTypedMaterial;
				try {
					woodTypedMaterial = Material.valueOf(
						materialName.replaceAll(getWoodTypesKeyword(), woodType)
							);
				} catch (Exception e) {
					continue;
				}
				woodTypedMaterials.add(woodTypedMaterial);
			}
		}
		return woodTypedMaterials;
	}
}
