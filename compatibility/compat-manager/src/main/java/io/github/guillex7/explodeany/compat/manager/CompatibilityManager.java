package io.github.guillex7.explodeany.compat.manager;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;

public class CompatibilityManager {
    private static CompatibilityManager instance;

    private final ICompatibilityApi[] registeredApis;
    private ICompatibilityApi api;

    private CompatibilityManager() {
        this.registeredApis = new ICompatibilityApi[] {
                new io.github.guillex7.explodeany.compat.v1_16_1.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_14.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_13.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_9.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_8_3.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_8.CompatibilityApi()
        };
    }

    public static CompatibilityManager getInstance() {
        if (instance == null) {
            instance = new CompatibilityManager();
        }
        return instance;
    }

    public void loadMaximumApiForEnvironment() {
        this.api = this.getMaximumApiForEnvironment(this.getBukkitVersion());
        this.api.load();
    }

    private ICompatibilityApi getMaximumApiForEnvironment(Version bukkitVersion) {
        for (ICompatibilityApi api : this.registeredApis) {
            if (bukkitVersion.isEqualOrAfter(api.getMinimumSupportedBukkitVersion())) {
                return api;
            }
        }

        return this.registeredApis[this.registeredApis.length - 1];
    }

    public Version getBukkitVersion() {
        return Version.fromString(Bukkit.getVersion());
    }

    public ICompatibilityApi getApi() {
        return api;
    }
}
