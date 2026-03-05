package io.github.guillex7.explodeany.compat.common.bukkit.api;

import io.github.guillex7.explodeany.compat.common.LoadableListener;

public interface IBukkitListenerUtils {
    LoadableListener createBlockExplodeListener();

    LoadableListener createTNTPrimeEventListener();
}
