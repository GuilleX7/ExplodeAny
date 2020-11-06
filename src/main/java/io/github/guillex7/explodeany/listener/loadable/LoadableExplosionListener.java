package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.Listener;

public interface LoadableExplosionListener extends Listener {
	public abstract void unload();
	public abstract String getName();
	public boolean shouldBeLoaded();
}
