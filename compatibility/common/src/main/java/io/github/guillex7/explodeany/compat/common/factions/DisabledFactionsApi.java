package io.github.guillex7.explodeany.compat.common.factions;

public class DisabledFactionsApi implements IFactionsApi {
    @Override
    public String getName() {
        return "Factions";
    }

    @Override
    public boolean isEnvironmentSuitable(final io.github.guillex7.explodeany.compat.common.Version bukkitVersion) {
        return true;
    }

    @Override
    public void load(final io.github.guillex7.explodeany.compat.common.Version bukkitVersion, final java.util.logging.Logger logger) {
        // Do nothing
    }

    @Override
    public boolean canBreakAtLocation(final org.bukkit.Location targetLocation) {
        return true;
    }

    @Override
    public void unload() {
        // Do nothing
    }
    
}
