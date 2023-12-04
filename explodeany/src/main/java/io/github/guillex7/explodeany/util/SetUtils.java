package io.github.guillex7.explodeany.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetUtils {
    private SetUtils() {
    }

    @SafeVarargs
    public static <T> Set<T> createHashSetOf(T... items) {
        Set<T> set = new HashSet<>();
        Collections.addAll(set, items);
        return set;
    }
}
