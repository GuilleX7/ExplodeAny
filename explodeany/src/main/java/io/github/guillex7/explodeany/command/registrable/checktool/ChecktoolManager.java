package io.github.guillex7.explodeany.command.registrable.checktool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.util.StringUtils;

public class ChecktoolManager {
    private static final String CHECKTOOL_DUMP_FILENAME = "checktool.dump";

    private static ChecktoolManager instance;

    private ItemStack checktool;
    private final File checktoolFile;
    private final Set<Player> playersUsingChecktool;

    private ChecktoolManager() {
        this.checktool = this.getDefaultChecktool();
        this.checktoolFile = new File(this.getPlugin().getDataFolder(), CHECKTOOL_DUMP_FILENAME);
        this.playersUsingChecktool = new HashSet<>();
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
        return this.playersUsingChecktool.contains(player);
    }

    public void addPlayerUsingChecktool(Player player) {
        this.playersUsingChecktool.add(player);
    }

    public void removePlayerUsingChecktool(Player player) {
        this.playersUsingChecktool.remove(player);
    }

    private ItemStack getDefaultChecktool() {
        return new ItemStack(Material.AIR);
    }

    public void loadChecktool() {
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
    }

    public boolean persistChecktool() {
        boolean checktoolWasPersistedSuccessfully = false;

        try {
            this.checktoolFile.createNewFile();
        } catch (Exception e) {
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
    }

    public ItemStack getChecktool() {
        return checktool;
    }

    public boolean setChecktool(ItemStack item) {
        this.checktool = item;

        if (this.persistChecktool()) {
            this.getPlugin().getLogger().info("Checktool item persisted successfully!");
            return true;
        } else {
            this.getPlugin().getLogger().info("Checktool item was set, but it couldn't be persisted");
            return false;
        }
    }
}
