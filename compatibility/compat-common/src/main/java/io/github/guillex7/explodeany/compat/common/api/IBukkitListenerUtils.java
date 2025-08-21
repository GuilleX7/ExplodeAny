package io.github.guillex7.explodeany.compat.common.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public interface IBukkitListenerUtils {
    LoadableListener createBlockExplodeListener();

    LoadableListener createTNTPrimeEventListener();
}
