package io.github.guillex7.explodeany.compat.bukkit.v1_13_paper.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_13_paper.listener.CTNTPrimeListener;
import io.github.guillex7.explodeany.compat.common.LoadableListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.bukkit.v1_13.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
