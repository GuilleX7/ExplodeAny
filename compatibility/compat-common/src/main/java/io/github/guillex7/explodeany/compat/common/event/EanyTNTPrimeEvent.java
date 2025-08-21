package io.github.guillex7.explodeany.compat.common.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/* Copied from com.destroystokyo.paper.event.block.TNTPrimeEvent */
public class EanyTNTPrimeEvent extends BlockEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private PrimeReason reason;
    private Entity primerEntity;

    public EanyTNTPrimeEvent(Block theBlock, PrimeReason reason, Entity primerEntity) {
        super(theBlock);
        this.reason = reason;
        this.primerEntity = primerEntity;
    }

    /**
     * Gets the TNT prime reason
     *
     * @return Prime reason
     */
    public PrimeReason getReason() {
        return this.reason;
    }

    /**
     * Gets the TNT primer {@link Entity}.
     *
     * It's null if {@link #getReason()} is {@link PrimeReason#REDSTONE} or
     * {@link PrimeReason#FIRE}.
     * It's not null if {@link #getReason()} is {@link PrimeReason#PLAYER} or
     * {@link PrimeReason#PROJECTILE}
     * It might be null if {@link #getReason()} is {@link PrimeReason#EXPLOSION}
     *
     * @return The {@link Entity} who primed the TNT
     */
    public Entity getPrimerEntity() {
        return this.primerEntity;
    }

    /**
     * Gets whether spawning {@link org.bukkit.entity.TNTPrimed} should be cancelled
     * or not
     *
     * @return Whether spawning {@link org.bukkit.entity.TNTPrimed} should be
     *         cancelled or not
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets whether to cancel spawning {@link org.bukkit.entity.TNTPrimed} or not
     *
     * @param cancel whether spawning {@link org.bukkit.entity.TNTPrimed} should be
     *               cancelled or not
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum PrimeReason {
        /**
         * When TNT with the unstable block state set to true is broken
         */
        BLOCK_BREAK,

        /**
         * When TNT is primed by a dispenser holding flint and still
         */
        DISPENSER,

        /**
         * When TNT prime was caused by other explosion (chain reaction)
         */
        EXPLOSION,

        /**
         * When TNT prime was caused by fire
         */
        FIRE,

        /**
         * When {@link org.bukkit.entity.Player} used
         * {@link org.bukkit.Material#FLINT_AND_STEEL} or
         * {@link org.bukkit.Material#FIRE_CHARGE} on given TNT block
         */
        PLAYER,

        /**
         * When TNT prime was caused by an {@link Entity} shooting TNT
         * using a bow with {@link org.bukkit.enchantments.Enchantment#ARROW_FIRE}
         * enchantment
         */
        PROJECTILE,

        /**
         * When redstone power triggered the TNT prime
         */
        REDSTONE,

        /**
         * When the cause of the TNT prime is unknown
         */
        UNKNOWN
    }
}
