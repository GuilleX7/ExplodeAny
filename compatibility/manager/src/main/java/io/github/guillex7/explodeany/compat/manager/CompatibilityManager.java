package io.github.guillex7.explodeany.compat.manager;

import java.util.logging.Logger;

import io.github.guillex7.explodeany.compat.common.Environment;
import io.github.guillex7.explodeany.compat.common.LoadableApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.bukkit.ABukkitApi;
import io.github.guillex7.explodeany.compat.common.coreprotect.ICoreProtectApi;
import io.github.guillex7.explodeany.compat.common.factions.IFactionsApi;
import io.github.guillex7.explodeany.compat.common.worldguard.IWorldGuardApi;

public class CompatibilityManager {
    private static CompatibilityManager instance;

    private final ABukkitApi[] registeredBukkitApis;
    private final ICoreProtectApi[] registeredCoreProtectApis;
    private final IWorldGuardApi[] registeredWorldGuardApis;
    private final IFactionsApi[] registeredFactionsApis;

    private ABukkitApi bukkitApi;
    private ICoreProtectApi coreProtectApi;
    private IWorldGuardApi worldGuardApi;
    private IFactionsApi factionsApi;

    private static <T extends LoadableApi> T safelyInstantiate(final Class<? extends T> apiClass) {
        try {
            return apiClass.getDeclaredConstructor().newInstance();
        } catch (final Exception e) {
            return null;
        } catch (final NoClassDefFoundError e) {
            return null;
        }
    }

    private CompatibilityManager() {
        this.registeredBukkitApis = new ABukkitApi[] {
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_21_paper.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_20.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_19_4_paper.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_16_1_paper.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_16_1.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_14_paper.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_14.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_13_paper.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_13.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_9.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_8_3.BukkitApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.bukkit.v1_8.BukkitApi.class)
        };

        this.registeredCoreProtectApis = new ICoreProtectApi[] {
                safelyInstantiate(io.github.guillex7.explodeany.compat.coreprotect.api_v7.CoreProtectApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.common.coreprotect.DisabledCoreProtectApi.class)
        };

        this.registeredWorldGuardApis = new IWorldGuardApi[] {
                safelyInstantiate(io.github.guillex7.explodeany.compat.worldguard.api_v7.WorldGuardApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.worldguard.api_v6.WorldGuardApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.common.worldguard.DisabledWorldGuardApi.class)
        };

        this.registeredFactionsApis = new IFactionsApi[] {
                safelyInstantiate(io.github.guillex7.explodeany.compat.factions.api_v3.FactionsApi.class),
                safelyInstantiate(io.github.guillex7.explodeany.compat.common.factions.DisabledFactionsApi.class)
        };
    }

    public static CompatibilityManager getInstance() {
        if (CompatibilityManager.instance == null) {
            CompatibilityManager.instance = new CompatibilityManager();
        }
        return CompatibilityManager.instance;
    }

    private <T extends LoadableApi> T getSuitableApi(final T[] apis, final String apiName) {
        final Version bukkitVersion = Environment.getBukkitVersion();

        for (final T api : apis) {
            if (api != null && api.isEnvironmentSuitable(bukkitVersion)) {
                return api;
            }
        }

        throw new IllegalStateException(
                String.format("No suitable %s API found, cowardly refusing to continue.", apiName));
    }

    private <T extends LoadableApi> T loadApi(final T[] registeredApis, final String apiName,
            final Version bukkitVersion, final Logger logger) {
        final T api = this.getSuitableApi(registeredApis, apiName);
        if (api != null) {
            api.load(bukkitVersion, logger);
        }
        return api;
    }

    public void load(final Logger logger) {
        final Version bukkitVersion = Environment.getBukkitVersion();

        this.bukkitApi = this.loadApi(this.registeredBukkitApis, "Bukkit", bukkitVersion, logger);
        this.coreProtectApi = this.loadApi(this.registeredCoreProtectApis, "CoreProtect", bukkitVersion, logger);
        this.worldGuardApi = this.loadApi(this.registeredWorldGuardApis, "WorldGuard", bukkitVersion, logger);
        this.factionsApi = this.loadApi(this.registeredFactionsApis, "Factions", bukkitVersion, logger);
    }

    public ABukkitApi getBukkitApi() {
        return this.bukkitApi;
    }

    public ICoreProtectApi getCoreProtectApi() {
        return this.coreProtectApi;
    }

    public IWorldGuardApi getWorldGuardApi() {
        return this.worldGuardApi;
    }

    public IFactionsApi getFactionsApi() {
        return this.factionsApi;
    }

    private void unloadApi(final LoadableApi api) {
        if (api != null) {
            api.unload();
        }
    }

    public void unload() {
        this.unloadApi(this.bukkitApi);
        this.unloadApi(this.coreProtectApi);
        this.unloadApi(this.worldGuardApi);
        this.unloadApi(this.factionsApi);
    }
}
