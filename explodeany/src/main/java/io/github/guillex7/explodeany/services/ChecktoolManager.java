package io.github.guillex7.explodeany.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.data.Duration;
import io.github.guillex7.explodeany.util.StringUtils;

public final class ChecktoolManager {
    private static final String CHECKTOOL_DUMP_FILENAME = "checktool.dump";

    private static ChecktoolManager instance;

    private final File checktoolFile;
    private final Map<Player, Boolean> checktoolStateByPlayer;
    private final Map<Player, IBossBar> checktoolBossBarByPlayer;
    private final Map<Player, BukkitTask> checktoolBossBarTaskByPlayer;
    private final ConfigurationManager configurationManager;

    private ItemStack checktool;

    private ChecktoolManager() {
        this.checktool = this.getDefaultChecktool();
        this.checktoolFile = new File(this.getPlugin().getDataFolder(), ChecktoolManager.CHECKTOOL_DUMP_FILENAME);
        this.checktoolStateByPlayer = new HashMap<>();
        this.checktoolBossBarByPlayer = new HashMap<>();
        this.checktoolBossBarTaskByPlayer = new HashMap<>();
        this.configurationManager = ConfigurationManager.getInstance();
        this.loadChecktool();
    }

    public static ChecktoolManager getInstance() {
        if (ChecktoolManager.instance == null) {
            ChecktoolManager.instance = new ChecktoolManager();
        }
        return ChecktoolManager.instance;
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    public boolean isPlayerUsingChecktool(final Player player) {
        if (this.configurationManager.getChecktoolConfiguration().isAlwaysEnabled()) {
            return true;
        }

        return this.checktoolStateByPlayer.getOrDefault(player,
                this.configurationManager.getChecktoolConfiguration().isEnabledByDefault());
    }

    public void setPlayerIsUsingChecktool(final Player player, final boolean isUsingChecktool) {
        if (this.configurationManager.getChecktoolConfiguration().isAlwaysEnabled()) {
            return;
        }

        this.checktoolStateByPlayer.put(player, isUsingChecktool);
    }

    private ItemStack getDefaultChecktool() {
        return new ItemStack(Material.AIR);
    }

    public void loadChecktool() {
        if (this.checktoolFile.exists() && this.checktoolFile.canRead()) {
            final Logger configurationSerializationLogger = Logger
                    .getLogger(ConfigurationSerialization.class.getName());
            final Level previousConfigurationSerializationLevel = configurationSerializationLogger.getLevel();

            try (final InputStream inputStream = new FileInputStream(this.checktoolFile);
                    final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)) {
                configurationSerializationLogger.setLevel(Level.OFF);
                this.checktool = (ItemStack) objectInputStream.readObject();
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);

                if (this.checktool != null && this.checktool.getType() != null) {
                    this.getPlugin().getLogger().info(String.format("Checktool item loaded successfully (%s)",
                            StringUtils.beautifyName(this.checktool.getType().toString())));
                } else {
                    throw new ClassNotFoundException();
                }
            } catch (final ClassNotFoundException | IOException e) {
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                this.checktool = this.getDefaultChecktool();
                this.getPlugin().getLogger()
                        .warning(
                                "Couldn't load checktool item! The item might belong to a higher Minecraft version or might be corrupted");
            } catch (final Exception e) {
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                this.checktool = this.getDefaultChecktool();
                this.getPlugin().getLogger().warning("Couldn't load checktool item! Unknown issue");
            }
        }
    }

    public boolean persistChecktool() {
        boolean checktoolWasPersistedSuccessfully = false;

        try {
            this.checktoolFile.createNewFile();
        } catch (final IOException | SecurityException e) {
            checktoolWasPersistedSuccessfully = false;
        }

        if (this.checktoolFile.exists() && this.checktoolFile.canWrite()) {
            try (final OutputStream outputStream = new FileOutputStream(this.checktoolFile);
                    final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(this.checktool);
                checktoolWasPersistedSuccessfully = true;
            } catch (final Exception e) {
                checktoolWasPersistedSuccessfully = false;
            }
        }

        return checktoolWasPersistedSuccessfully;
    }

    public ItemStack getChecktool() {
        return this.checktool;
    }

    public boolean setChecktool(final ItemStack item) {
        this.checktool = item;

        if (this.persistChecktool()) {
            this.getPlugin().getLogger().info("Checktool item persisted successfully!");
            return true;
        } else {
            this.getPlugin().getLogger().info("Checktool item was set, but it couldn't be persisted");
            return false;
        }
    }

    public void hideChecktoolBossBarForPlayer(final Player player) {
        final BukkitTask task = this.checktoolBossBarTaskByPlayer.get(player);
        if (task != null) {
            task.cancel();
        }

        final IBossBar bossBar = this.checktoolBossBarByPlayer.get(player);
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }

        this.checktoolBossBarByPlayer.remove(player);
        this.checktoolBossBarTaskByPlayer.remove(player);
    }

    public void setChecktoolBossBarForPlayer(final Player player, final IBossBar bossBar,
            final Duration bossBarDuration) {
        final BukkitTask oldTask = this.checktoolBossBarTaskByPlayer.get(player);
        if (oldTask != null) {
            oldTask.cancel();
        }

        final IBossBar oldBossBar = this.checktoolBossBarByPlayer.get(player);
        if (oldBossBar != null) {
            oldBossBar.removePlayer(player);
        }

        bossBar.addPlayer(player);

        this.checktoolBossBarByPlayer.put(player, bossBar);
        this.checktoolBossBarTaskByPlayer.put(player, Bukkit.getScheduler().runTaskLater(this.getPlugin(), () -> {
            bossBar.removePlayer(player);

            this.checktoolBossBarByPlayer.remove(player);
            this.checktoolBossBarTaskByPlayer.remove(player);
        }, bossBarDuration.asTicks()));
    }
}
