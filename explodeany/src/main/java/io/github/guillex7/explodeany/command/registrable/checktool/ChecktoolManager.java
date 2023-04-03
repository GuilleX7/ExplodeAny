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
    private File checktoolFile;
    private Set<Player> playersUsingChecktool;

    private ChecktoolManager() {
        this.checktool = getDefaultChecktool();
        this.checktoolFile = new File(getPlugin().getDataFolder(), CHECKTOOL_DUMP_FILENAME);
        this.playersUsingChecktool = new HashSet<>();
        loadChecktool();
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

    public Set<Player> getPlayersUsingChecktool() {
        return playersUsingChecktool;
    }

    private ItemStack getDefaultChecktool() {
        return new ItemStack(Material.AIR);
    }

    public void loadChecktool() {
        if (this.checktoolFile.exists() && this.checktoolFile.canRead()) {
            InputStream inputStream = null;
            BukkitObjectInputStream objectInputStream = null;
            Logger configurationSerializationLogger = Logger.getLogger(ConfigurationSerialization.class.getName());
            Level previousConfigurationSerializationLevel = configurationSerializationLogger.getLevel();

            try {
                inputStream = new FileInputStream(checktoolFile);
                objectInputStream = new BukkitObjectInputStream(inputStream);

                configurationSerializationLogger.setLevel(Level.OFF);
                checktool = (ItemStack) objectInputStream.readObject();
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);

                if (checktool != null && checktool.getType() != null) {
                    getPlugin().getLogger().info(String.format("Checktool item loaded successfully (%s)",
                            StringUtils.beautifyName(checktool.getType().toString())));
                } else {
                    throw new ClassNotFoundException();
                }
            } catch (ClassNotFoundException | IOException e) {
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                checktool = getDefaultChecktool();
                getPlugin().getLogger()
                        .warning(
                                "Couldn't load checktool item! The item might belong to a higher Minecraft version or might be corrupted");
            } catch (Exception e) {
                configurationSerializationLogger.setLevel(previousConfigurationSerializationLevel);
                checktool = getDefaultChecktool();
                getPlugin().getLogger().warning("Couldn't load checktool item! Unknown issue");
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (Exception e) {
                        // Pass
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        // Pass
                    }
                }
            }
        }
    }

    public boolean persistChecktool() {
        try {
            checktoolFile.createNewFile();
        } catch (IOException e) {
            return false;
        }

        OutputStream outputStream = null;
        BukkitObjectOutputStream objectOutputStream = null;
        boolean checktoolWasPersistedSuccessfully = false;

        if (checktoolFile.exists() && checktoolFile.canWrite()) {
            try {
                outputStream = new FileOutputStream(checktoolFile);
                objectOutputStream = new BukkitObjectOutputStream(outputStream);
                objectOutputStream.writeObject(checktool);
                checktoolWasPersistedSuccessfully = true;
            } catch (Exception e) {
                checktoolWasPersistedSuccessfully = false;
            } finally {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (Exception e) {
                        // Pass
                    }
                }

                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        // Pass
                    }
                }
            }
        }

        return checktoolWasPersistedSuccessfully;
    }

    public ItemStack getChecktool() {
        return checktool;
    }

    public boolean setChecktool(ItemStack item) {
        checktool = item;

        if (persistChecktool()) {
            getPlugin().getLogger().info("Checktool item persisted successfully!");
            return true;
        } else {
            getPlugin().getLogger().info("Checktool item was set, but it couldn't be persisted");
            return false;
        }
    }
}
