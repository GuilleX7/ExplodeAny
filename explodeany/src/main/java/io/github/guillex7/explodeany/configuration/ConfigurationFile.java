package io.github.guillex7.explodeany.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationFile {
    private final JavaPlugin plugin;
    private final String fileName;
    private final File file;
    private FileConfiguration fileConfiguration;

    public ConfigurationFile(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);
        this.fileConfiguration = null;
    }

    public void reloadConfig() {
        this.fileConfiguration = new YamlConfiguration();
        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | IllegalArgumentException exception) {
            this.plugin.getLogger()
                    .severe(String.format("Could not load configuration file (%s), does it exist?", this.fileName));
        } catch (InvalidConfigurationException exception) {
            this.plugin.getLogger().severe(
                    String.format("Configuration file seems to be invalid, please check below for more details:\n%s",
                            exception.getMessage()));
        }

        try (InputStream defaultFileStream = this.plugin.getResource(this.fileName);
             InputStreamReader defaultFileStreamReader = new InputStreamReader(defaultFileStream)) {
            if (defaultFileStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration
                        .loadConfiguration(defaultFileStreamReader);
                this.fileConfiguration.setDefaults(defaultConfig);
            }
        } catch (Exception exception) {
            this.plugin.getLogger().warning(
                    String.format("Could not load default configuration file (%s), defaults won't be applied",
                            this.fileName));
        }
    }

    public void saveDefaultFileIfMissing() {
        if (!this.file.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }

    public FileConfiguration getConfig() {
        if (this.fileConfiguration == null) {
            this.reloadConfig();
        }
        return this.fileConfiguration;
    }
}
