package io.github.guillex7.explodeany.compat.common.event;

import org.bukkit.event.Listener;

public interface IBukkitListener extends Listener {
    public boolean shouldBeLoaded();

    public void unload();
}
