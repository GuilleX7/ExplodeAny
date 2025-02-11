package io.github.guillex7.explodeany.block;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.block.Block;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.block.exception.BlockDatabaseException;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class BlockDatabase {

    private static BlockDatabase instance;
    private static final TypeToken<Map<BlockLocation, BlockStatus>> DATABASE_TYPE_TOKEN = new TypeToken<Map<BlockLocation, BlockStatus>>() {
    };

    private Map<BlockLocation, BlockStatus> database;

    private static TypeToken<Map<BlockLocation, BlockStatus>> getDatabaseTypeToken() {
        return DATABASE_TYPE_TOKEN;
    }

    private BlockDatabase() {
        this.database = new HashMap<>();
    }

    public static BlockDatabase getInstance() {
        if (instance == null) {
            instance = new BlockDatabase();
        }
        return instance;
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    private Map<BlockLocation, BlockStatus> getDatabase() {
        return database;
    }

    private void setDatabase(Map<BlockLocation, BlockStatus> database) {
        this.database = database;
    }

    public BlockStatus getBlockStatus(Block block) {
        return this.getBlockStatus(block, true);
    }

    public BlockStatus getBlockStatus(Block block, boolean persistIfAbsentOrIncongruent) {
        BlockLocation blockLocation = BlockLocation.fromBlock(block);
        BlockStatus blockStatus = this.getDatabase().get(blockLocation);
        if (blockStatus == null || !blockStatus.isCongruentWith(block)) {
            blockStatus = BlockStatus.defaultForBlock(block);
            if (persistIfAbsentOrIncongruent) {
                this.getDatabase().put(blockLocation, blockStatus);
            }
        }
        return blockStatus;
    }

    public void removeBlockStatus(Block block) {
        this.getDatabase().remove(BlockLocation.fromBlock(block));
    }

    public void loadFromFile(File databaseFile) {
        if (!databaseFile.exists() || !databaseFile.canRead()) {
            this.getPlugin().getLogger().info("Database doesn't exist yet, will create a new database");
            return;
        }

        this.setDatabase(this.deserializeDatabaseFile(databaseFile));
    }

    private Map<BlockLocation, BlockStatus> deserializeDatabaseFile(File databaseFile) {
        Map<BlockLocation, BlockStatus> loadedDatabase;
        try (FileReader fileReader = new FileReader(databaseFile)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(BlockLocation.class, new BlockLocationAdapter());
            gsonBuilder.registerTypeAdapter(BlockStatus.class, new BlockStatusAdapter());
            gsonBuilder.enableComplexMapKeySerialization();
            Gson gson = gsonBuilder.create();

            loadedDatabase = gson.fromJson(fileReader, getDatabaseTypeToken().getType());

            if (loadedDatabase == null) {
                throw new BlockDatabaseException();
            } else {
                this.getPlugin().getLogger().info("Database loaded successfully");
            }
        } catch (Exception e) {
            loadedDatabase = new HashMap<>();
            this.getPlugin().getLogger().warning("Couldn't load database, creating an empty one");
        }

        return loadedDatabase;
    }

    public void saveToFile(File databaseFile) {
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                this.getPlugin().getLogger().warning("Couldn't create a new database file! Database won't be saved");
                return;
            }
        }

        this.serializeDatabaseFile(databaseFile, this.getDatabase());
    }

    private void serializeDatabaseFile(File databaseFile, Map<BlockLocation, BlockStatus> database) {
        try (FileWriter fileWriter = new FileWriter(databaseFile)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(BlockLocation.class, new BlockLocationAdapter());
            gsonBuilder.registerTypeAdapter(BlockStatus.class, new BlockStatusAdapter());
            gsonBuilder.enableComplexMapKeySerialization();
            Gson gson = gsonBuilder.create();
            fileWriter.write(gson.toJson(database, getDatabaseTypeToken().getType()));
            this.getPlugin().getLogger().info("Database saved successfully");
        } catch (Exception e) {
            this.getPlugin().getLogger().warning("Couldn't save database");
        }
    }

    public void sanitize() {
        Iterator<Entry<BlockLocation, BlockStatus>> iterator = this.getDatabase().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<BlockLocation, BlockStatus> entry = iterator.next();
            if (this.isEntryNotSane(entry, ConfigurationManager.getInstance().doCheckBlockDatabaseAtStartup())) {
                iterator.remove();
            }
        }
    }

    private boolean isEntryNotSane(Entry<BlockLocation, BlockStatus> entry, boolean checkDeeply) {
        BlockStatus blockStatus = entry.getValue();
        if (blockStatus.shouldBreak()) {
            return true;
        }

        blockStatus.sanitize();

        if (checkDeeply) {
            Block block = entry.getKey().toBlock();
            return block.isEmpty() || !ConfigurationManager.getInstance().doHandlesBlock(block)
                    || !blockStatus.isCongruentWith(block);
        }
        return false;
    }
}
