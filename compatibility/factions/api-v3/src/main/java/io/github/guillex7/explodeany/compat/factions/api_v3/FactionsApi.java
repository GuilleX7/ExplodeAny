package io.github.guillex7.explodeany.compat.factions.api_v3;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.ps.PS;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.factions.IFactionsApi;

public class FactionsApi implements IFactionsApi {
    @Override
    public String getName() {
        return "Factions";
    }

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
        try {
            return plugin != null && plugin.isEnabled() && plugin instanceof Factions && BoardColl.get() != null;
        } catch (final NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        this.announce(logger);
    }

    protected void announce(final Logger logger) {
        logger.info("Loaded support for Factions v3+");
    }

    @Override
    public boolean canBreakAtLocation(final Location targetLocation) {
        return BoardColl.get().getFactionAt(PS.valueOf(targetLocation)).isExplosionsAllowed();
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
