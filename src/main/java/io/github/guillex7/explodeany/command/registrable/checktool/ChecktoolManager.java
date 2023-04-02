package io.github.guillex7.explodeany.command.registrable.checktool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.util.StringUtils;

public class ChecktoolManager {
    private static final String CHECKTOOL_DUMP_FILENAME = "checktool.dump";

    private static ChecktoolManager instance;
    private static ItemStack checktool;
    private static File checktoolFile;
    private static Set<Player> playersUsingChecktool;

    private ChecktoolManager() {
        checktool = new ItemStack(Material.AIR);
        checktoolFile = new File(getPlugin().getDataFolder(), CHECKTOOL_DUMP_FILENAME);
        playersUsingChecktool = new HashSet<>();
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

    public void loadChecktool() {
        if (checktoolFile.exists() && checktoolFile.canRead()) {
            try {
                InputStream is = new FileInputStream(checktoolFile);
                BukkitObjectInputStream ois = new BukkitObjectInputStream(is);
                checktool = (ItemStack) ois.readObject();
                ois.close();
                is.close();
                getPlugin().getLogger().info(String.format("Checktool item loaded successfully (%s)",
                        StringUtils.beautifyName(checktool.getType().toString())));
            } catch (Exception e) {
                getPlugin().getLogger().warning("Couldn't load checktool item!");
            }
        }
    }

    public boolean persistChecktool() {
        try {
            checktoolFile.createNewFile();
        } catch (IOException e) {
            return false;
        }

        if (checktoolFile.exists() && checktoolFile.canWrite()) {
            try {
                OutputStream os = new FileOutputStream(checktoolFile);
                BukkitObjectOutputStream oos = new BukkitObjectOutputStream(os);
                oos.writeObject(checktool);
                oos.close();
                os.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        return true;
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
