package io.github.guillex7.explodeany.compat.common;

import java.util.logging.Logger;

public interface LoadableApi {
    boolean isEnvironmentSuitable(final Version bukkitVersion);

    String getName();

    void load(final Version bukkitVersion, final Logger logger);

    void unload();
}
