package io.github.guillex7.explodeany.compat.manager;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;

public class CompatibilityManager {
    private static CompatibilityManager instance;

    private final ACompatibilityApi[] registeredApis;
    private ACompatibilityApi api;

    private CompatibilityManager() {
        this.registeredApis = new ACompatibilityApi[] {
                new io.github.guillex7.explodeany.compat.v1_20.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_19_4_paper.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_16_1_paper.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_16_1.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_14_paper.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_14.CompatibilityApi(),
                new io.github.guillex7.explodeany.compat.v1_13_paper.CompatibilityApi(),
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

    private ACompatibilityApi getMaximumApiForEnvironment(Version bukkitVersion) {
        for (ACompatibilityApi registeredApi : this.registeredApis) {
            if (bukkitVersion.isEqualOrAfter(registeredApi.getMinimumSupportedBukkitVersion())
                    && registeredApi.isEnvironmentSuitable()) {
                return registeredApi;
            }
        }

        return this.registeredApis[this.registeredApis.length - 1];
    }

    public Version getBukkitVersion() {
        return Version.fromString(Bukkit.getVersion());
    }

    public ACompatibilityApi getApi() {
        return api;
    }
}
