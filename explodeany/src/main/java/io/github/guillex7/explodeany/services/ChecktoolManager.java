package io.github.guillex7.explodeany.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.api.IBossBar;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.data.Duration;
import io.github.guillex7.explodeany.util.StringUtils;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public final class ChecktoolManager {
    private static final String CHECKTOOL_DUMP_FILENAME = "checktool.dump";

    private static ChecktoolManager instance;

    private final File checktoolFile;
    private final ReadWriteLock checktoolLock;
    private final Map<Player, Boolean> checktoolStateByPlayer;
    private final Map<Player, IBossBar> checktoolBossBarByPlayer;
    private final Map<Player, ScheduledTask> checktoolBossBarTaskByPlayer;
    private final ReadWriteLock checktoolStateLock;
    private final ConfigurationManager configurationManager;

    private ItemStack checktool;

    private ChecktoolManager() {
        this.checktool = this.getDefaultChecktool();
        this.checktoolFile = new File(this.getPlugin().getDataFolder(), CHECKTOOL_DUMP_FILENAME);
        this.checktoolLock = new ReentrantReadWriteLock();

        this.checktoolStateByPlayer = new HashMap<>();
        this.checktoolBossBarByPlayer = new HashMap<>();
        this.checktoolBossBarTaskByPlayer = new HashMap<>();
        this.checktoolStateLock = new ReentrantReadWriteLock();

        this.configurationManager = ConfigurationManager.getInstance();
        this.loadChecktool();
    }

    public static ChecktoolManager getInstance() {
        if (instance == null) {
            instance = new ChecktoolManager();
        }
        return instance;
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    public boolean isPlayerUsingChecktool(Player player) {
        if (this.configurationManager.getChecktoolConfiguration().isAlwaysEnabled()) {
            return true;
        }

        this.checktoolStateLock.readLock().lock();
        try {
            return this.checktoolStateByPlayer.getOrDefault(player,
                    this.configurationManager.getChecktoolConfiguration().isEnabledByDefault());
        } finally {
            this.checktoolStateLock.readLock().unlock();
        }
    }

    public void setPlayerIsUsingChecktool(Player player, boolean isUsingChecktool) {
        if (this.configurationManager.getChecktoolConfiguration().isAlwaysEnabled()) {
            return;
        }

        this.checktoolStateLock.writeLock().lock();
        try {
            this.checktoolStateByPlayer.put(player, isUsingChecktool);
        } finally {
            this.checktoolStateLock.writeLock().unlock();
        }
    }

    private ItemStack getDefaultChecktool() {
        return new ItemStack(Material.AIR);
    }

    public void loadChecktool() {
        this.checktoolLock.writeLock().lock();

        try {
            if (this.checktoolFile.exists() && this.checktoolFile.canRead()) {
                Logger configurationSerializationLogger = Logger.getLogger(ConfigurationSerialization.class.getName());
                Level previousConfigurationSerializationLevel = configurationSerializationLogger.getLevel();

                try (InputStream inputStream = new FileInputStream(this.checktoolFile);
                        BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)) {
                    configurationSerializationLogger.setLevel(Level.OFF);
                    this.checktool = (ItemStack) objectInputStream.readObject();
                    configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);

                    if (this.checktool != null && this.checktool.getType() != null) {
                        this.getPlugin().getLogger().info(String.format("Checktool item loaded successfully (%s)",
                                StringUtils.beautifyName(this.checktool.getType().toString())));
                    } else {
                        throw new ClassNotFoundException();
                    }
                } catch (ClassNotFoundException | IOException e) {
                    configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                    this.checktool = this.getDefaultChecktool();
                    this.getPlugin().getLogger()
                            .warning(
                                    "Couldn't load checktool item! The item might belong to a higher Minecraft version or might be corrupted");
                } catch (Exception e) {
                    configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                    this.checktool = this.getDefaultChecktool();
                    this.getPlugin().getLogger().warning("Couldn't load checktool item! Unknown issue");
                }
            }
        } finally {
            this.checktoolLock.writeLock().unlock();
        }
    }

    public boolean persistChecktool() {
        this.checktoolLock.writeLock().lock();

        try {
            boolean checktoolWasPersistedSuccessfully = false;

            try {
                this.checktoolFile.createNewFile();
            } catch (IOException | SecurityException e) {
                checktoolWasPersistedSuccessfully = false;
            }

            if (this.checktoolFile.exists() && this.checktoolFile.canWrite()) {
                try (OutputStream outputStream = new FileOutputStream(this.checktoolFile);
                        BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)) {
                    objectOutputStream.writeObject(this.checktool);
                    checktoolWasPersistedSuccessfully = true;
                } catch (Exception e) {
                    checktoolWasPersistedSuccessfully = false;
                }
            }
            return checktoolWasPersistedSuccessfully;
        } finally {
            this.checktoolLock.writeLock().unlock();
        }
    }

    public ItemStack getChecktool() {
        this.checktoolLock.readLock().lock();
        try {
            return checktool;
        } finally {
            this.checktoolLock.readLock().unlock();
        }
    }

    public boolean setChecktool(ItemStack item) {
        this.checktoolLock.writeLock().lock();

        try {
            this.checktool = item;

            if (this.persistChecktool()) {
                this.getPlugin().getLogger().info("Checktool item persisted successfully!");
                return true;
            } else {
                this.getPlugin().getLogger().info("Checktool item was set, but it couldn't be persisted");
                return false;
            }
        } finally {
            this.checktoolLock.writeLock().unlock();
        }
    }

    public void hideChecktoolBossBarForPlayer(Player player) {
        checktoolStateLock.writeLock().lock();

        try {
            ScheduledTask task = this.checktoolBossBarTaskByPlayer.get(player);
            if (task != null) {
                task.cancel();
            }

            IBossBar bossBar = this.checktoolBossBarByPlayer.get(player);
            if (bossBar != null) {
                bossBar.removePlayer(player);
            }

            this.checktoolBossBarByPlayer.remove(player);
            this.checktoolBossBarTaskByPlayer.remove(player);
        } finally {
            checktoolStateLock.writeLock().unlock();
        }
    }

    public void setChecktoolBossBarForPlayer(Player player, IBossBar bossBar, Duration bossBarDuration) {
        checktoolStateLock.writeLock().lock();

        try {
            ScheduledTask oldTask = this.checktoolBossBarTaskByPlayer.get(player);
            if (oldTask != null) {
                oldTask.cancel();
            }

            IBossBar oldBossBar = this.checktoolBossBarByPlayer.get(player);
            if (oldBossBar != null) {
                oldBossBar.removePlayer(player);
            }

            bossBar.addPlayer(player);

            this.checktoolBossBarByPlayer.put(player, bossBar);
            this.checktoolBossBarTaskByPlayer.put(player,
                    Bukkit.getGlobalRegionScheduler().runDelayed(this.getPlugin(), (task) -> {
                        bossBar.removePlayer(player);

                        this.checktoolBossBarByPlayer.remove(player);
                        this.checktoolBossBarTaskByPlayer.remove(player);
                    }, bossBarDuration.asMilliseconds()));
        } finally {
            checktoolStateLock.writeLock().unlock();
        }
    }
}
