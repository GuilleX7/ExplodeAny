package io.github.guillex7.explodeany.compat.v1_13_paper.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import com.destroystokyo.paper.event.block.TNTPrimeEvent.PrimeReason;

import io.github.guillex7.explodeany.compat.common.event.EanyTNTPrimeEvent;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CTNTPrimeListener implements LoadableListener {
    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        /* Do nothing */
    }

    private EanyTNTPrimeEvent.PrimeReason translateNativeReason(final PrimeReason reason) {
        switch (reason) {
            case REDSTONE:
                return EanyTNTPrimeEvent.PrimeReason.REDSTONE;
            case FIRE:
                return EanyTNTPrimeEvent.PrimeReason.FIRE;
            case ITEM:
                return EanyTNTPrimeEvent.PrimeReason.PLAYER;
            case PROJECTILE:
                return EanyTNTPrimeEvent.PrimeReason.PROJECTILE;
            case EXPLOSION:
                return EanyTNTPrimeEvent.PrimeReason.EXPLOSION;
            default:
                return EanyTNTPrimeEvent.PrimeReason.UNKNOWN;
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onTNTPrime(final TNTPrimeEvent event) {
        final EanyTNTPrimeEvent eanyEvent = new EanyTNTPrimeEvent(event.getBlock(),
                this.translateNativeReason(event.getReason()),
                event.getPrimerEntity());

        Bukkit.getPluginManager().callEvent(eanyEvent);

        event.setCancelled(eanyEvent.isCancelled());
    }

    @Override
    public void unload() {
        TNTPrimeEvent.getHandlerList().unregister(this);
    }
}
