package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

public class ChecktoolConfiguration {
    public static final String ROOT_PATH = "Checktool";
    private static final String ALWAYS_ENABLED_PATH = "AlwaysEnabled";
    private static final String ENABLED_BY_DEFAULT_PATH = "EnabledByDefault";
    private static final String PREVENT_ACTION_WHEN_CHECKING_HANDLED_BLOCKS = "PreventActionWhenCheckingHandledBlocks";
    private static final String PREVENT_ACTION_WHEN_CHECKING_NON_HANDLED_BLOCKS = "PreventActionWhenCheckingNonHandledBlocks";
    private static final String SILENT_WHEN_CHECKING_ON_DISABLED_WORLDS_PATH = "SilentWhenCheckingOnDisabledWorlds";
    private static final String SILENT_WHEN_CHECKING_WITHOUT_PERMISSIONS_PATH = "SilentWhenCheckingWithoutPermissions";
    private static final String SILENT_WHEN_CHECKING_NONHANDLED_BLOCKS_PATH = "SilentWhenCheckingNonHandledBlocks";

    private boolean alwaysEnabled;
    private boolean enabledByDefault;
    private boolean preventActionWhenCheckingHandledBlocks;
    private boolean preventActionWhenCheckingNonHandledBlocks;
    private boolean silentWhenCheckingOnDisabledWorlds;
    private boolean silentWhenCheckingWithoutPermissions;
    private boolean silentWhenCheckingNonHandledBlocks;

    public static ChecktoolConfiguration byDefault() {
        return new ChecktoolConfiguration(false,
                false,
                true,
                true,
                false,
                false,
                false);
    }

    public static ChecktoolConfiguration fromConfigurationSection(ConfigurationSection section) {
        ChecktoolConfiguration defaults = ChecktoolConfiguration.byDefault();

        return new ChecktoolConfiguration(
                section.getBoolean(ALWAYS_ENABLED_PATH, defaults.alwaysEnabled),
                section.getBoolean(ENABLED_BY_DEFAULT_PATH, defaults.enabledByDefault),
                section.getBoolean(PREVENT_ACTION_WHEN_CHECKING_HANDLED_BLOCKS,
                        defaults.preventActionWhenCheckingHandledBlocks),
                section.getBoolean(PREVENT_ACTION_WHEN_CHECKING_NON_HANDLED_BLOCKS,
                        defaults.preventActionWhenCheckingNonHandledBlocks),
                section.getBoolean(SILENT_WHEN_CHECKING_ON_DISABLED_WORLDS_PATH,
                        defaults.silentWhenCheckingOnDisabledWorlds),
                section.getBoolean(SILENT_WHEN_CHECKING_WITHOUT_PERMISSIONS_PATH,
                        defaults.silentWhenCheckingWithoutPermissions),
                section.getBoolean(SILENT_WHEN_CHECKING_NONHANDLED_BLOCKS_PATH,
                        defaults.silentWhenCheckingNonHandledBlocks));
    }

    public ChecktoolConfiguration(boolean alwaysEnabled, boolean enabledByDefault,
            boolean preventActionWhenCheckingHandledBlocks,
            boolean preventActionWhenCheckingNonHandledBlocks,
            boolean silentWhenCheckingOnDisabledWorlds,
            boolean silentWhenCheckingWithoutPermissions, boolean silentWhenCheckingNonHandledBlocks) {
        this.alwaysEnabled = alwaysEnabled;
        this.enabledByDefault = enabledByDefault;
        this.preventActionWhenCheckingHandledBlocks = preventActionWhenCheckingHandledBlocks;
        this.preventActionWhenCheckingNonHandledBlocks = preventActionWhenCheckingNonHandledBlocks;
        this.silentWhenCheckingOnDisabledWorlds = silentWhenCheckingOnDisabledWorlds;
        this.silentWhenCheckingWithoutPermissions = silentWhenCheckingWithoutPermissions;
        this.silentWhenCheckingNonHandledBlocks = silentWhenCheckingNonHandledBlocks;
    }

    public boolean isAlwaysEnabled() {
        return alwaysEnabled;
    }

    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }

    public boolean doPreventActionWhenCheckingHandledBlocks() {
        return preventActionWhenCheckingHandledBlocks;
    }

    public boolean doPreventActionWhenCheckingNonHandledBlocks() {
        return preventActionWhenCheckingNonHandledBlocks;
    }

    public boolean isSilentWhenCheckingOnDisabledWorlds() {
        return silentWhenCheckingOnDisabledWorlds;
    }

    public boolean isSilentWhenCheckingWithoutPermissions() {
        return silentWhenCheckingWithoutPermissions;
    }

    public boolean isSilentWhenCheckingNonHandledBlocks() {
        return silentWhenCheckingNonHandledBlocks;
    }

    @Override
    public String toString() {
        return String.format(
                "Always enabled: %s\n"
                        + "Enabled by default: %s\n"
                        + "Prevent action when checking handled blocks: %s\n"
                        + "Prevent action when checking non handled blocks: %s\n"
                        + "Silent when checking on disabled worlds: %s\n"
                        + "Silent when checking without permissions: %s\n"
                        + "Silent when checking non handled blocks: %s",
                alwaysEnabled,
                enabledByDefault,
                preventActionWhenCheckingHandledBlocks,
                preventActionWhenCheckingNonHandledBlocks,
                silentWhenCheckingOnDisabledWorlds,
                silentWhenCheckingWithoutPermissions,
                silentWhenCheckingNonHandledBlocks);
    }
}
