package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.data.Duration;

public class ChecktoolConfiguration {
    public static final String ROOT_PATH = "Checktool";
    private static final String ALWAYS_ENABLED_PATH = "AlwaysEnabled";
    private static final String ENABLED_BY_DEFAULT_PATH = "EnabledByDefault";
    private static final String PREVENT_ACTION_WHEN_CHECKING_HANDLED_BLOCKS = "PreventActionWhenCheckingHandledBlocks";
    private static final String PREVENT_ACTION_WHEN_CHECKING_NON_HANDLED_BLOCKS = "PreventActionWhenCheckingNonHandledBlocks";
    private static final String SILENT_WHEN_CHECKING_ON_DISABLED_WORLDS_PATH = "SilentWhenCheckingOnDisabledWorlds";
    private static final String SILENT_WHEN_CHECKING_WITHOUT_PERMISSIONS_PATH = "SilentWhenCheckingWithoutPermissions";
    private static final String SILENT_WHEN_CHECKING_NONHANDLED_BLOCKS_PATH = "SilentWhenCheckingNonHandledBlocks";
    private static final String SILENT_WHEN_CHECKING_HANDLED_BLOCKS_PATH = "SilentWhenCheckingHandledBlocks";
    private static final String SHOW_BOSS_BAR = "ShowBossBar";
    private static final String BOSS_BAR_COLOR = "BossBarColor";
    private static final String BOSS_BAR_STYLE = "BossBarStyle";
    private static final String BOSS_BAR_DURATION = "BossBarDuration";

    private final boolean alwaysEnabled;
    private final boolean enabledByDefault;
    private final boolean preventActionWhenCheckingHandledBlocks;
    private final boolean preventActionWhenCheckingNonHandledBlocks;
    private final boolean silentWhenCheckingOnDisabledWorlds;
    private final boolean silentWhenCheckingWithoutPermissions;
    private final boolean silentWhenCheckingNonHandledBlocks;
    private final boolean silentWhenCheckingHandledBlocks;
    private final boolean showBossBar;
    private final EanyBossBarColor bossBarColor;
    private final EanyBossBarStyle bossBarStyle;
    private final Duration bossBarDuration;

    public static ChecktoolConfiguration byDefault() {
        return new ChecktoolConfiguration(false,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                EanyBossBarColor.PURPLE,
                EanyBossBarStyle.SOLID,
                Duration.ofTicks(30));
    }

    public static ChecktoolConfiguration fromConfigurationSection(ConfigurationSection section) {
        ChecktoolConfiguration defaults = ChecktoolConfiguration.byDefault();

        EanyBossBarColor bossBarColor;
        String bossBarColorString = section.getString(BOSS_BAR_COLOR, defaults.bossBarColor.name());
        try {
            bossBarColor = EanyBossBarColor.valueOf(bossBarColorString);
        } catch (IllegalArgumentException e) {
            ExplodeAny.getInstance().getLogger().warning(String.format(
                    "Invalid boss bar color '%s' in configuration section '%s'. Using default value '%s'.",
                    bossBarColorString, section.getCurrentPath(), defaults.bossBarColor.name()));
            bossBarColor = defaults.bossBarColor;
        }

        EanyBossBarStyle bossBarStyle;
        String bossBarStyleString = section.getString(BOSS_BAR_STYLE, defaults.bossBarStyle.name());
        try {
            bossBarStyle = EanyBossBarStyle.valueOf(bossBarStyleString);
        } catch (IllegalArgumentException e) {
            ExplodeAny.getInstance().getLogger().warning(String.format(
                    "Invalid boss bar style '%s' in configuration section '%s'. Using default value '%s'.",
                    bossBarStyleString, section.getCurrentPath(), defaults.bossBarStyle.name()));
            bossBarStyle = defaults.bossBarStyle;
        }

        Duration bossBarDuration = Duration.parse(section.getString(BOSS_BAR_DURATION));
        if (bossBarDuration.isZero()) {
            bossBarDuration = defaults.bossBarDuration;
        }

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
                        defaults.silentWhenCheckingNonHandledBlocks),
                section.getBoolean(SILENT_WHEN_CHECKING_HANDLED_BLOCKS_PATH,
                        defaults.silentWhenCheckingHandledBlocks),
                section.getBoolean(SHOW_BOSS_BAR, defaults.showBossBar),
                bossBarColor, bossBarStyle, bossBarDuration);
    }

    public ChecktoolConfiguration(boolean alwaysEnabled, boolean enabledByDefault,
            boolean preventActionWhenCheckingHandledBlocks,
            boolean preventActionWhenCheckingNonHandledBlocks,
            boolean silentWhenCheckingOnDisabledWorlds,
            boolean silentWhenCheckingWithoutPermissions, boolean silentWhenCheckingNonHandledBlocks,
            boolean silentWhenCheckingHandledBlocks, boolean showBossBar, EanyBossBarColor bossBarColor,
            EanyBossBarStyle bossBarStyle, Duration bossBarDuration) {
        this.alwaysEnabled = alwaysEnabled;
        this.enabledByDefault = enabledByDefault;
        this.preventActionWhenCheckingHandledBlocks = preventActionWhenCheckingHandledBlocks;
        this.preventActionWhenCheckingNonHandledBlocks = preventActionWhenCheckingNonHandledBlocks;
        this.silentWhenCheckingOnDisabledWorlds = silentWhenCheckingOnDisabledWorlds;
        this.silentWhenCheckingWithoutPermissions = silentWhenCheckingWithoutPermissions;
        this.silentWhenCheckingNonHandledBlocks = silentWhenCheckingNonHandledBlocks;
        this.silentWhenCheckingHandledBlocks = silentWhenCheckingHandledBlocks;
        this.showBossBar = showBossBar;
        this.bossBarColor = bossBarColor;
        this.bossBarStyle = bossBarStyle;
        this.bossBarDuration = bossBarDuration;
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

    public boolean isSilentWhenCheckingHandledBlocks() {
        return silentWhenCheckingHandledBlocks;
    }

    public boolean doShowBossBar() {
        return showBossBar;
    }

    public EanyBossBarColor getBossBarColor() {
        return bossBarColor;
    }

    public EanyBossBarStyle getBossBarStyle() {
        return bossBarStyle;
    }

    public Duration getBossBarDuration() {
        return bossBarDuration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Always enabled: ").append(alwaysEnabled).append("\n");
        builder.append("Enabled by default: ").append(enabledByDefault).append("\n");
        builder.append("Prevent action when checking handled blocks: ").append(preventActionWhenCheckingHandledBlocks)
                .append("\n");
        builder.append("Prevent action when checking non handled blocks: ")
                .append(preventActionWhenCheckingNonHandledBlocks).append("\n");
        builder.append("Silent when checking on disabled worlds: ").append(silentWhenCheckingOnDisabledWorlds)
                .append("\n");
        builder.append("Silent when checking without permissions: ").append(silentWhenCheckingWithoutPermissions)
                .append("\n");
        builder.append("Silent when checking non handled blocks: ").append(silentWhenCheckingNonHandledBlocks)
                .append("\n");
        builder.append("Silent when checking handled blocks: ").append(silentWhenCheckingHandledBlocks).append("\n");
        builder.append("Show boss bar: ").append(showBossBar).append("\n");
        builder.append("Boss bar color: ").append(bossBarColor).append("\n");
        builder.append("Boss bar style: ").append(bossBarStyle).append("\n");
        builder.append("Boss bar duration: ").append(bossBarDuration);
        return builder.toString();
    }
}
