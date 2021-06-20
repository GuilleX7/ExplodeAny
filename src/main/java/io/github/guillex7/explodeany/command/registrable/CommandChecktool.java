package io.github.guillex7.explodeany.command.registrable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.github.guillex7.explodeany.ExplodeAny;

public class CommandChecktool extends RegistrableCommand {
	private static ItemStack checktool;
	private static File checktoolFile;
	private static Set<Player> playersUsingChecktool;

	private static ExplodeAny getPlugin() {
		return ExplodeAny.getInstance();
	}

	public static final Set<Player> getPlayersUsingChecktool() {
		return playersUsingChecktool;
	}

	public static void loadChecktool() {
		if (checktoolFile.exists() && checktoolFile.canRead()) {
			try {
				InputStream is = new FileInputStream(checktoolFile);
				BukkitObjectInputStream ois = new BukkitObjectInputStream(is);
				checktool = (ItemStack) ois.readObject();
				ois.close();
				is.close();
				getPlugin().getLogger().info("Checktool item loaded successfully");
			} catch (Exception e) {
				getPlugin().getLogger().warning("Couldn't load checktool item!");
			}
		}
	}

	public static boolean persistChecktool() {
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

	public static ItemStack getChecktool() {
		return checktool;
	}

	public static boolean setChecktool(ItemStack item) {
		checktool = item;

		if (persistChecktool()) {
			getPlugin().getLogger().info("Checktool item persisted successfully!");
			return true;
		} else {
			getPlugin().getLogger().info("Checktool item was set, but it couldn't be persisted");
			return false;
		}
	}

	private CommandChecktool() {
		super();
		checktool = new ItemStack(Material.AIR);
		checktoolFile = new File(getPlugin().getDataFolder(), "checktool.dump");
		playersUsingChecktool = new HashSet<>();
		loadChecktool();
	}

	public static CommandChecktool empty() {
		return new CommandChecktool();
	}

	@Override
	public String getName() {
		return "checktool";
	}

	@Override
	public String getUsage() {
		return "[toggle|set|give|reset]";
	}

	@Override
	public List<RegistrableCommand> getSubcommands() {
		return new ArrayList<RegistrableCommand>(Arrays.asList(CommandChecktoolToggle.empty(),
				CommandChecktoolReset.empty(), CommandChecktoolGive.empty(), CommandChecktoolSet.empty()));
	}
}
