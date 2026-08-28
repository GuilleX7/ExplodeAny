package io.github.guillex7.explodeany.data;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExplodingVanillaEntityTest {

    @Test
    public void resolvesEveryEnumNameCaseInsensitively() {
        for (final ExplodingVanillaEntity value : ExplodingVanillaEntity.values()) {
            assertEquals(value, ExplodingVanillaEntity.fromEntityTypeName(value.name()));
            assertEquals(value, ExplodingVanillaEntity.fromEntityTypeName(value.name().toLowerCase(Locale.ROOT)));
        }
    }

    @Test
    public void preservesBukkitEntityTypeAliases() {
        assertEquals(ExplodingVanillaEntity.PRIMED_TNT,
                ExplodingVanillaEntity.fromEntityTypeName("TNT"));
        assertEquals(ExplodingVanillaEntity.MINECART_TNT,
                ExplodingVanillaEntity.fromEntityTypeName("TNT_MINECART"));
        assertEquals(ExplodingVanillaEntity.ENDER_CRYSTAL,
                ExplodingVanillaEntity.fromEntityTypeName("END_CRYSTAL"));
    }

    @Test
    public void returnsNullForUnsupportedAndNullNames() {
        assertNull(ExplodingVanillaEntity.fromEntityTypeName("ZOMBIE"));
        assertNull(ExplodingVanillaEntity.fromEntityTypeName("GLOW_SQUID"));
        assertNull(ExplodingVanillaEntity.fromEntityTypeName(null));
    }
}
