package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.Listener;

public interface LoadableListener extends Listener {
	public void unload();

	public String getName();

	public boolean shouldBeLoaded();

	public boolean isAdvisable();
}
