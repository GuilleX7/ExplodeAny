package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_8.listener.CBlockExplodeListener;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.listener.CTNTPrimeListener;
import io.github.guillex7.explodeany.compat.common.LoadableListener;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IBukkitListenerUtils;

public class CBukkitListenerUtils implements IBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }

    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
