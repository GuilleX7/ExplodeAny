# Global properties

## Basic properties

These are the main configuration keys used in `config.yml` to control the plugin's global behavior.  
Below is a list of each property, its purpose, and example values.

| Configuration key             | Description                                                                                                                                                                                              | Values                   |
| ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ |
| `UseBlockDatabase`            | Enables or disables the use of a block database for storing data. The database allows to persist damage data across server restarts, but the file where the data is stored might become large over time. | `true` or `false`        |
| `CheckBlockDatabaseAtStartup` | Checks the block database for consistency at plugin startup. Only relevant if `UseBlockDatabase` is enabled. It may reduce the size of the database by removing invalid entries.                         | `true` or `false`        |
| `BlockDurability`             | Sets the default durability for all blocks, i.e., the health of the blocks managed by the plugin.                                                                                                        | positive number (min: 1) |
| `EnableMetrics`               | Enables or disables plugin metrics (such as bStats).                                                                                                                                                     | `true` or `false`        |

**Defaults:**

```yaml
UseBlockDatabase: false
CheckBlockDatabaseAtStartup: false
BlockDurability: 100.0
EnableMetrics: true
```

## Localization properties

These properties are used to control the localization of the plugin.

| Configuration key | Description                                             |
| ----------------- | ------------------------------------------------------- |
| `Locale`          | Contains localized messages for the plugin. (See below) |
| `LocalePrefix`    | The prefix for all messages sent to the player.         |

The `Locale` property contains all the localized messages used by the plugin. Each message is defined with a key and a value. The key is used to reference the message in the code, while the value is the actual message that will be displayed to the player. [Color and formatting codes](https://htmlcolorcodes.com/minecraft-color-codes/) are supported in all messages.

The `LocalePrefix` property is a string that is prepended to all messages sent to the player. This can be used to customize the prefix for all messages, such as adding a color code or changing the text.

### Messages

Some messages have placeholders that can be replaced with dynamic values. The placeholders are enclosed in `%` symbols and will be replaced with the corresponding value when the message is sent to the player. Below is a list of all the messages used by the plugin, along with their descriptions and placeholders.

| Message key              | Description                                                                                 | Placeholders                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| ------------------------ | ------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `NotAllowed`             | The player is not allowed to perform the action.                                            | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `Usage`                  | The command's usage.                                                                        | - %DESCRIPTION%: the command description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| `OnlyPlayerAllowed`      | Only players can perform this action.                                                       | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `PlayerDoesntExist`      | The player doesn't exist in the server.                                                     | - %NAME%: the player's name                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `PlayerIsOffline`        | The player must be online to perform that.                                                  | - %NAME%: the player's name                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `EnterChecktoolMode`     | The player can now right-click a block with the checktool item to display block durability. | - %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `LeaveChecktoolMode`     | The player can no longer check for a block durability.                                      | - %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `ChecktoolToggledOn`     | Checktool mode toggled on for player.                                                       | - %NAME%: the player's name                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `ChecktoolToggledOff`    | Checktool mode toggled off for player.                                                      | - %NAME%: the player's name                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `ChecktoolUse`           | Block health: %DURABILITY_PERCENTAGE%% (%PRETTY_MATERIAL%)                                  | - %MATERIAL%: the clicked block's type<br>- %PRETTY_MATERIAL%: the clicked block's type in a human readable way<br>- %B_X%: the X coordinate of the targeted block<br>- %B_Y%: the Y coordinate of the targeted block<br>- %B_Z%: the Z coordinate of the targeted block<br>- %DURABILITY%: the clicked block's durability<br>- %MAX_DURABILITY%: the maximum durability a block can have (same as BlockDurability)<br>- %DURABILITY_PERCENTAGE%: the clicked block's durability as a percentage (excluding the % symbol) |
| `ChecktoolUseBossBar`    | %PRETTY_MATERIAL%: %DURABILITY_PERCENTAGE%%                                                 | - %MATERIAL%: the clicked block's type<br>- %PRETTY_MATERIAL%: the clicked block's type in a human readable way<br>- %B_X%: the X coordinate of the targeted block<br>- %B_Y%: the Y coordinate of the targeted block<br>- %B_Z%: the Z coordinate of the targeted block<br>- %DURABILITY%: the clicked block's durability<br>- %MAX_DURABILITY%: the maximum durability a block can have (same as BlockDurability)<br>- %DURABILITY_PERCENTAGE%: the clicked block's durability as a percentage (excluding the % symbol) |
| `ChecktoolSet`           | Checktool successfully set to %PRETTY_ITEM%!                                                | - %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `ChecktoolNotPersisted`  | Checktool item was set to %PRETTY_ITEM%, but it couldn't be persisted                       | - %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `ChecktoolGiven`         | A checktool (%PRETTY_ITEM%) was given to player %NAME%                                      | - %NAME%: the player's name<br>- %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                  |
| `ChecktoolReset`         | Checktool successfully reset to bare hand (Air)                                             | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `ChecktoolNotHandled`    | %PRETTY_MATERIAL% is not handled by the current configuration                               | - %MATERIAL%: the clicked block's type<br>- %PRETTY_MATERIAL%: the clicked block's type in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                           |
| `ChecktoolInfo`          | Current checktool item: %PRETTY_ITEM%                                                       | - %ITEM%: the checktool item's name<br>- %PRETTY_ITEM%: the checktool item's name in a human readable way                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `ChecktoolAlwaysEnabled` | Checktool can't be toggled off because it's always enabled                                  | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `DisabledInThisWorld`    | This functionality is disabled in this world                                                | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `Reloaded`               | Reloaded successfully!                                                                      | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `DebugEnabled`           | Debug mode has been enabled                                                                 | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `DebugDisabled`          | Debug mode has been disabled                                                                | (None)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |

**Defaults:**

```yaml
Locale:
    NotAllowed: "You are not allowed to perform this action!"
    Usage: "Usage: %DESCRIPTION%"
    OnlyPlayerAllowed: "Only players can perform this action!"
    PlayerDoesntExist: "Player %NAME% doesn't exist in the server!"
    PlayerIsOffline: "Player %NAME% must be online to perform that"
    EnterChecktoolMode: "You can now right-click a block with %PRETTY_ITEM% to display block durability"
    LeaveChecktoolMode: "You can no longer check for a block durability"
    ChecktoolToggledOn: Checktool mode toggled on for player %NAME%
    ChecktoolToggledOff: Checktool mode toggled off for player %NAME%
    ChecktoolUse: "Block health: %DURABILITY_PERCENTAGE%% (%PRETTY_MATERIAL%)"
    ChecktoolUseBossBar: "%PRETTY_MATERIAL%: %DURABILITY_PERCENTAGE%%"
    ChecktoolSet: "Checktool successfully set to %PRETTY_ITEM%!"
    ChecktoolNotPersisted: "Checktool item was set to %PRETTY_ITEM%, but it couldn't be persisted"
    ChecktoolGiven: "A checktool (%PRETTY_ITEM%) was given to player %NAME%"
    ChecktoolReset: "Checktool successfully reset to bare hand (Air)"
    ChecktoolNotHandled: "%PRETTY_MATERIAL% is not handled by the current configuration"
    ChecktoolInfo: "Current checktool item: %PRETTY_ITEM%"
    ChecktoolAlwaysEnabled: "Checktool can't be toggled off because it's always enabled"
    DisabledInThisWorld: "This functionality is disabled in this world"
    Reloaded: "Reloaded successfully!"
    DebugEnabled: "Debug mode has been enabled"
    DebugDisabled: "Debug mode has been disabled"

LocalePrefix: "[ExplodeAny] "
```

## Checktool properties

These properties are used to control the checktool item. The checktool item is an item that players can use to check the durability of blocks. By default, the checktool item is set to bare hand (Air), but it can be changed to any item in the game. Note that all properties of the item are persisted, including the name, lore, and enchantments, so only a clone of the item will be recognized as a checktool item.

| Configuration key                           | Description                                                                                                                                                                                                                                    |
| ------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `AlwaysEnabled`                             | If set to true, the checktool will be always enabled (the player only needs the permission to use it). If set to false, then the checktool can be toggled on and off by the player, given the player has the permission to do so.              |
| `EnabledByDefault`                          | If set to true, the checktool will be enabled by default. If set to false, the checktool will be disabled by default.                                                                                                                          |
| `PreventActionWhenCheckingHandledBlocks`    | Prevents the default vanilla behaviour of the item to be used when the checktool is in the player's hand when the player right-clicks a block that is handled by the plugin.                                                                   |
| `PreventActionWhenCheckingNonHandledBlocks` | Prevents the default vanilla behaviour of the item to be used when the checktool is in the player's hand when the player right-clicks a block that is not handled by the plugin.                                                               |
| `SilentWhenCheckingOnDisabledWorlds`        | If set to true, using the checktool in a world where ExplodeAny is disabled will not send any message to the player. If set to false, the plugin will send a message to the player informing that the functionality is disabled in that world. |
| `SilentWhenCheckingWithoutPermissions`      | If set to true, using the checktool without the permission to do so will not send any message to the player. If set to false, the plugin will send a message to the player informing that he/she doesn't have the permission.                  |
| `SilentWhenCheckingNonHandledBlocks`        | If set to true, the plugin will not send any message when the player checks a block that is not handled by the plugin. If set to false, the plugin will send a message to the player informing that the block is not handled.                  |
| `SilentWhenCheckingHandledBlocks`           | If set to true, the plugin will not send any message when the player checks a block that is handled by the plugin. If set to false, the plugin will send a message to the player informing the block's durability.                             |
| `ShowBossBar`                               | If set to true, a boss bar will be displayed to the player when he/she checks a block's durability.                                                                                                                                            |
| `BossBarColor`                              | Color of the boss bar that will be displayed to the player when he/she checks a block's durability. Allowed values are: `PINK`, `BLUE`, `RED`, `GREEN`, `YELLOW`, `PURPLE`, `WHITE`.                                                           |
| `BossBarStyle`                              | Style of the boss bar that will be displayed to the player when he/she checks a block's durability. Allowed values are: `SOLID`, `SEGMENTED_6`, `SEGMENTED_10`, `SEGMENTED_12`, `SEGMENTED_20`.                                                |
| `BossBarDuration`                           | Duration of the boss bar that will be displayed to the player when he/she checks a block's durability. [See how durations are formatted.](#duration)                                                                                           |

**Defaults:**

```yaml
Checktool:
    AlwaysEnabled: false
    EnabledByDefault: false
    PreventActionWhenCheckingHandledBlocks: true
    PreventActionWhenCheckingNonHandledBlocks: true
    SilentWhenCheckingOnDisabledWorlds: false
    SilentWhenCheckingWithoutPermissions: false
    SilentWhenCheckingNonHandledBlocks: false
    SilentWhenCheckingHandledBlocks: false
    ShowBossBar: false
    BossBarColor: PURPLE
    BossBarStyle: SOLID
    BossBarDuration: 1500ms
```

## Disabled worlds

This list contains all the worlds where the plugin is disabled. While the plugin will still be loaded, it will not perform any action in these worlds. This is useful for worlds where you don't want the plugin to interfere with the gameplay, such as creative worlds or minigames. Some commands, like reloading or configuration related commands, might still be accessible.

**Defaults:**

```yaml
DisabledWorlds: []
```

**Example:**

```yaml
DisabledWorlds:
    - MyWorld
    - MyWorld2
```

## World hole protection

Since the plugin allows breaking all blocks, it is possible to create world holes by breaking blocks in the world. This can be problematic, especially in survival worlds. To prevent this, the plugin has a world hole protection feature that prevents players from breaking blocks at certain heights/layers.

Each key in the list represents a world where the world hole protection is enabled. The allowed values are described below:

| Configuration key        | Description                                                                                                                                                                                                         |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Heights`                | A list of heights/layers where ExplodeAny won't destroy blocks. By default, this only affects to blocks listed in the configuration, so if the bottom layer is made of dirt, ExplodeAny won't prevent these blocks. |
| `ProtectUnhandledBlocks` | If set to true, unhandled blocks will also be protected from explosions. In the example above, this means that even dirt would be protected.                                                                        |

**Defaults:**

```yaml
WorldHoleProtection: {}
```

**Example:**

```yaml
WorldHoleProtection:
    default:
        Heights:
            - -64 # Bedrock layer post-1.18
        #   - 0   # Bedrock layer pre-1.18 (only one of these two is usually needed)
    world_nether:
        Heights:
            - 127
            - 0
```

# Explosion properties

The behavior of ExplodeAny can be customized by specifying the properties of each couple of **entities that can explode + blocks that can be destroyed.** Different sections in the configuration file group different types of explosive entities, thus allowing to customize different types of explosions. Most common entities, like `TNT` or `CREEPER`, fall into the `VanillaEntity` section, which contains Minecraft's vanilla entities. Other sections allow to configure explosions from other plugins or even mods.

An overview of a simple configuration can be seen below:

```yaml
VanillaEntity:
    TNT:
        Properties:
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                Damage: 50.0

CustomEntity:
    CGM_MISSILE: # This entity comes from a mod
        Properties:
            ExplosionRadius: 4.0
        Materials:
            OBSIDIAN:
                Damage: 50.0
```

As you can see, the configuration is divided into sections. Each section contains a list of entities, their properties and the materials that will be affected by their explosions. The properties are used to customize the behavior of the explosion itself, such as the explosion radius, damage underwater and other effects. The materials section contains a list of blocks that will be affected by the explosion, along with their properties, such as damage, drop chance for blocks, etc.

While different sections can have different entities allowed, the overall structure (including available materials and properties) remains the same.

## Available sections

The list of supported sections includes:

| Section name           | Description                                                                                                                                                                                                                                                              | Availability                                                                                                   |
| ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------- |
| `VanillaEntity`        | This section allows to customize the behavior of explosions caused by vanilla entities, such as creepers, TNT, beds, etc.                                                                                                                                                | Always                                                                                                         |
| `CannonProjectile`     | This section allows to customize the behavior of explosions caused by Cannons' projectiles                                                                                                                                                                               | Soft-depends on [Cannons](https://www.spigotmc.org/resources/cannons.56764/)                                   |
| `MagicEntity`          | This section allows to customize the behavior of explosions caused by the Magic plugin                                                                                                                                                                                   | Soft-depends on [Magic](https://www.spigotmc.org/resources/magic.1056/)                                        |
| `QualityArmory`        | This section allows to customize the behavior of explosions caused by the weapons of the QualityArmory plugin.                                                                                                                                                           | Soft-depends on [QualityArmory](https://www.spigotmc.org/resources/quality-armory.47561/) [v2.0.10 or greater] |
| `ThrowableCreeperEggs` | This section allows to customize the behavior of explosions caused by TCE creeper eggs.                                                                                                                                                                                  | Soft-depends on [Throwable Creeper Eggs](https://www.spigotmc.org/resources/throwable-creeper-eggs.75877/)     |
| `CustomEntity`         | This section allows to customize the behavior of explosions caused by custom entities explosions, which are mostly entities introduced by mods in Forge servers. It also allows to configure explosions that have no entity attached (through the `UNKNOWN` entity name) | Always                                                                                                         |

The list of entities available in each section is listed below:

| Section name           | Entity names                                                                                                                                                                                                                                              |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `VanillaEntity`        | `WITHER`, `ENDER_CRYSTAL` (or `END_CRYSTAL`), `PRIMED_TNT` (or `TNT`), `MINECART_TNT` (or `TNT_MINECART`), `CREEPER`, `CHARGED_CREEPER`, `FIREBALL`, `DRAGON_FIREBALL`, `SMALL_FIREBALL`, `WITHER_SKULL`, `CHARGED_WITHER_SKULL`, `BED`, `RESPAWN_ANCHOR` |
| `CannonProjectile`     | Any projectile ID (e.g. `diamond`, `cannonball`, `clusterbomb`, etc)                                                                                                                                                                                      |
| `MagicEntity`          | `WITHER`, `ENDER_CRYSTAL`, `PRIMED_TNT`, `MINECART_TNT`, `CREEPER`, `CHARGED_CREEPER`, `FIREBALL`, `DRAGON_FIREBALL`, `SMALL_FIREBALL`, `WITHER_SKULL`, `CHARGED_WITHER_SKULL`, `BED`, `RESPAWN_ANCHOR`                                                   |
| `QualityArmory`        | `RPG`, `HomingRPG`, `MiniNuke`, `Grenade`, `StickyGrenade`, `ProxyMine`                                                                                                                                                                                   |
| `ThrowableCreeperEggs` | `CREEPER`, `CHARGED_CREEPER`                                                                                                                                                                                                                              |
| `CustomEntity`         | Any entity name (e.g. `CGM_MISSILE`, `CGM_BOMB`, etc). `UNKNOWN` can be used to configure explosions that are not attached to any entity (for example, some plugins might trigger explosions without a specific entity)                                   |

## Structure of the configuration

Given a section (represented by the placeholder `<SECTION>`) and an entity (represented by the placeholder `<ENTITY>` in the following example), the structure of the configuration is as follows:

```yaml
<SECTION>:
    <ENTITY/GROUP>:
        Properties:
            <ENTITY-PROPERTY-1>: <VALUE>
            <ENTITY-PROPERTY-2>: <VALUE>
        Materials:
            <MATERIAL/GROUP>:
                <MATERIAL-PROPERTY-1>: <VALUE>
                <MATERIAL-PROPERTY-2>: <VALUE>
```

Where `Properties` is a section that contains the properties of the entity (aka [Entity properties](#entity-properties)), and `Materials` is a section that contains the [materials](#materials) (or [group names](#groups)) that will be affected by the explosion. Each material can have its own properties when affected by a given entity (aka [Entity-material properties](#entity-material-properties)). We will cover these properties in the next sections.

For example, given the section `VanillaEntity` and the entity `TNT`, the configuration would look like this:

```yaml
VanillaEntity:
    TNT:
        Properties:
            <ENTITY-PROPERTY-1>: <VALUE>
            <ENTITY-PROPERTY-2>: <VALUE>
        Materials:
            <MATERIAL/GROUP>:
                <MATERIAL-PROPERTY-1>: <VALUE>
                <MATERIAL-PROPERTY-2>: <VALUE>
```

Groups can also be used in every place where entity and materials appear. They are a way to group multiple entities or materials together, avoiding repetition. To know more about groups, see [Groups](#groups).

Entities and materials also accept wildcards. For example, `TNT*` will match all entities that start with `TNT`, such as `TNT`, `TNT_MINECART`, etc. Similarly, `*` will match all entities or materials. As with groups, they allow to avoid repetition. See [Name wildcards](#name-wildcards) for more information.

## Entity properties

The properties of the entity are used to customize the behavior of the explosion itself. All entities have the same set of properties available:

| Property                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | Values                                          |
| ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------- |
| `ExplosionRadius`                                           | The explosion radius of the entity. It is important to note that setting this property **will not replace** the original radius of the explosion by default. Instead, if just tells ExplodeAny to ignore the original radius of the explosion and, instead, use this radius for all its computations.<br> For example, a TNT has a standard explosion radius of 4. If TNT is configured to damage obsidian blocks, ExplodeAny will scan obsidian blocks in the range of 4 blocks within the center of the explosion. However, if this property is set to 10, ExplodeAny will ignore the original radius and scan obsidian blocks to damage in a radius of 10 blocks.<br> Leaving this property unset (or setting it to 0, equivalently) tells ExplodeAny to use the original explosion radius. This property is mandatory for `CustomEntity` entities, as they are unknown entities to the plugin and the user needs to provide a radius | [Minimum]: 0.0<br>[Default] 0.0                 |
| `ExplosionFactor`                                           | A multiplicative factor applied to the explosion radius. This property is used to magnify or reduce the explosion radius of the entity. For example, if this property is set to 2.0, the explosion radius will be doubled. If it is set to 0.5, the explosion radius will be halved.<br> Leaving this property unset (or setting it to 1.0, equivalently) tells ExplodeAny to use the original explosion radius.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | [Minimum]: 0.0<br>[Default] 1.0                 |
| `ReplaceOriginalExplosion`                                  | As stated in `ExplosionRadius`, the original explosion radius si not overridden by default. However, if this property is enabled, the original explosion will be cancelled and, instead, a new explosion with a radius of `ExplosionRadius` (multiplied by `ExplosionFactor`) will be created in place. This allows to also magnify or reduce the explosion damage of the original explosion to blocks that ExplodeAny do not handle.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | [Default] false                                 |
| `UnderwaterExplosionFactor`                                 | A multiplicative factor applied to the explosion radius when it takes place underwater. This property is used to magnify or reduce the explosion radius of the entity when it takes place underwater. For example, if this property is set to 2.0, the explosion radius will be doubled. If it is set to 0.5, the explosion radius will be halved.<br> Leaving this property unset (or setting it to 1.0, equivalently) tells ExplodeAny to use the original explosion radius.                                                                                                                                                                                                                                                                                                                                                                                                                                                           | [Minimum]: 0.0<br>[Default] 1.0                 |
| `ExplosionDamageBlocksUnderwater`                           | By default, explosions that occur underwater (or underlava, equivalently) will not damage blocks that are not handled by ExplodeAny (i.e. blocks that are not present in the configuration). If this property is enabled, explosions that occur underwater will damage blocks as if they were above water. This property is useful for creating underwater explosions that have the same effect as above-water explosions, without having to explicitly list all possible blocks. The explosion might be a bit weaker due to the high explosion resistance of water and lava.                                                                                                                                                                                                                                                                                                                                                            | [Default] false                                 |
| `ReplaceOriginalExplosionWhenUnderwater`                    | This property is similar to `ReplaceOriginalExplosion`, but it only applies when the explosion takes place underwater. When `ExplosionDamageBlocksUnderwater` is enabled, a new explosion on top of the original explosion is created to enable damage to vanilla blocks that are not handled. If this property is enabled, the original explosion will be cancelled and the new explosion will override it completely. Note that this is also affected by the custom `ExplosionRadius` and `ExplosionFactor` properties.                                                                                                                                                                                                                                                                                                                                                                                                                | [Default] true                                  |
| `ExplosionRemoveWaterloggedStateFromNearbyBlocks`           | This property is used to remove the waterlogged state from nearby blocks before the explosion. This allows to break blocks that are normally not breakable, such as waterlogged stairs, etc.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | [Default] false                                 |
| `ExplosionRemoveWaterloggedStateFromNearbyBlocksOnSurface`  | This property depends on `ExplosionRemoveWaterloggedStateFromNearbyBlocks` and allows to further configure the behavior. If the explosion takes place on the surface, then waterlogged blocks nearby will be affected. If disabled, nearby waterlogged state won't be removed when the explosion takes place on the surface.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | [Default] true                                  |
| `ExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater` | This property depends on `ExplosionRemoveWaterloggedStateFromNearbyBlocks` and allows to further configure the behavior. If the explosion takes place underwater, then waterlogged blocks nearby will be affected. If disabled, nearby waterlogged state won't be removed when the explosion takes place underwater (or underlava).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | [Default] true                                  |
| `ExplosionRemoveNearbyWaterloggedBlocks`                    | This property is used to remove nearby blocks that are waterlogged before the explosion. In contrast to the above properties, this will remove the entire block instead of the waterlogged state. This allows to break blocks that are normally not breakable, such as waterlogged stairs, etc.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          | [Default] false                                 |
| `ExplosionRemoveNearbyWaterloggedBlocksOnSurface`           | This property depends on `ExplosionRemoveNearbyWaterloggedBlocks` and allows to further configure the behavior. If the explosion takes place on the surface, then waterlogged blocks nearby will be affected. If disabled, nearby waterlogged blocks won't be removed when the explosion takes place on the surface.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | [Default] true                                  |
| `ExplosionRemoveNearbyWaterloggedBlocksUnderwater`          | This property depends on `ExplosionRemoveNearbyWaterloggedBlocks` and allows to further configure the behavior. If the explosion takes place underwater, then waterlogged blocks nearby will be affected. If disabled, nearby waterlogged blocks won't be removed when the explosion takes place underwater (or underlava).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | [Default] true                                  |
| `ExplosionRemoveNearbyLiquids`                              | This property is used to remove nearby liquids before the explosion. This allows to break blocks that are normally not breakable because they are placed behind or under a liquid.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | [Default] false                                 |
| `ExplosionRemoveNearbyLiquidsOnSurface`                     | This property depends on `ExplosionRemoveNearbyLiquids` and allows to further configure the behavior. If the explosion takes place on the surface, then liquids nearby will be affected. If disabled, nearby liquids won't be removed when the explosion takes place on the surface.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | [Default] true                                  |
| `ExplosionRemoveNearbyLiquidsUnderwater`                    | This property depends on `ExplosionRemoveNearbyLiquids` and allows to further configure the behavior. If the explosion takes place underwater, then liquids nearby will be affected. If disabled, nearby liquids won't be removed when the explosion takes place underwater (or underlava).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | [Default] true                                  |
| `PackDroppedItems`                                          | This property is used to pack the dropped items into a single entity, which will be spawned at the explosion's location. This can help reducing lag on client side when the explosion causes a lot of items to be spawned (in general, when explosion radius > 10 and many items are dropped). If set to false, then items will drop naturally (i.e. scattered over the ground).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | [Default] false                                 |
| `Particles`                                                 | This section allows to specify the particles that will spawn when the entity explodes.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | See [Particle properties](#particle-properties) |
| `Sound`                                                     | This section allows to specify the sound to be played when the entity explodes.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          | See [Sound properties](#sound-properties)       |

Example of configuration (omitting particles and sound properties, which will be covered later):

```yaml
<SECTION>:
    <ENTITY/GROUP>:
        Properties:
            ExplosionRadius: 10.0
            ExplosionFactor: 2.0
            ReplaceOriginalExplosion: true
            UnderwaterExplosionFactor: 1.5
            ExplosionDamageBlocksUnderwater: true
            ExplosionRemoveWaterloggedStateFromNearbyBlocks: true
            ExplosionRemoveNearbyWaterloggedBlocks: true
            ExplosionRemoveNearbyLiquids: true
            PackDroppedItems: false
```

### Particle properties

The particle properties are used to customize the behavior of the particles that will spawn when the entity explodes. The properties are as follows:

| Property   | Description                                                                                                                                                 | Values |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| `Name`     | Name of the particles to be spawned. Valid values are listed in [Particles](#particles)                                                                     |        |
| `DeltaX`   | Width of the cube that will contain the particles, centered in the explosion                                                                                |        |
| `DeltaY`   | Height of the cube that will contain the particles, centered in the explosion                                                                               |        |
| `DeltaZ`   | Depth of the cube that will contain the particles, centered in the explosion                                                                                |        |
| `Amount`   | Amount of particles to be spawned. Be careful, larger values could cause FPS drops.                                                                         |        |
| `Speed`    | Speed of the particles to be spawned. Must be a positive value or zero.                                                                                     |        |
| `Force`    | Allows the particles to be seen up to 256 blocks away.                                                                                                      |        |
| `Red`      | Red component of the color of the particles. Only applicable when particle's name is `REDSTONE`. Maximum value is 255 (maximum unsigned number in a byte)   |        |
| `Green`    | Green component of the color of the particles. Only applicable when particle's name is `REDSTONE`. Maximum value is 255 (maximum unsigned number in a byte) |        |
| `Blue`     | Blue component of the color of the particles. Only applicable when particle's name is `REDSTONE`. Maximum value is 255 (maximum unsigned number in a byte)  |        |
| `Size`     | Size of the particles. Must be a positive value. Only applicable when particle's name is `REDSTONE`.                                                        |        |
| `Material` | Some particles, like `BLOCK_CRACK`, require a Material to be specified. They will take the appearance of the material specified here.                       |        |

Example of properties:

```yaml
<SECTION>:
    <ENTITY/GROUP>:
        Properties:
            Particles:
                Name: BLOCK_CRACK
                DeltaX: 1.0
                DeltaY: 1.0
                DeltaZ: 1.0
                Amount: 100
                Speed: 0.5
                Force: true
                Material: STONE
```

```yaml
<SECTION>:
    <ENTITY/GROUP>:
        Properties:
            Particles:
                Name: REDSTONE
                DeltaX: 1.0
                DeltaY: 1.0
                DeltaZ: 1.0
                Amount: 100
                Speed: 0.5
                Force: true
                Red: 0
                Green: 127
                Blue: 255
```

### Sound properties

The sound properties are used to customize the behavior of the sound that will be played when the entity explodes. The properties are as follows:

| Property | Description                                                                                                                                                                                 | Values |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| `Name`   | Name of the sound to be played. Valid values are listed in [Sounds](#sounds)                                                                                                                |        |
| `Volume` | Volume of the sound to be played. Must be a positive value. Values greater than 1.0 will not increase the loudness of the sound, but will allow the sound to be heared in a greater radius. |        |
| `Pitch`  | Pitch of the sound. Must be between 0.5 and 2.0, and allows to specify the speed the sound is played at.                                                                                    |        |

Example of properties:

```yaml
<SECTION>:
    <ENTITY>:
        Properties:
            Sound:
                Name: ENTITY_GENERIC_EXPLODE
                Volume: 1.0
                Pitch: 1.0
```

## Entity-material properties

The entity-material properties are used to customize how a specific material is affected by a given explosion. The properties are as follows:

| Property                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Values                                                                                                                                                                                                                                                                        |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Damage`                    | Base damage used to compute the effective damage taken by a block. This is the base damage used to compute the effective damage taken by a block. The effective damage is calculated according to the following formula: <br> `effectiveDamage = baseDamage * underwaterDamageFactor * (1 - distanceFactor * distanceAttenuationFactor)`<br> where `distanceFactor = distance / explosionRadius`                                                                                  | [Minimum]: 0.0<br> [Default] is the same value as BlockDurability (which means enough damage to break the block in one explosion)<br> Values greater than BlockDurability are allowed.                                                                                        |
| `DropChance`                | Indicates the chance of naturally breaking the block (and thus having a drop). The value is a percentage, so 0.0 means blocks will never break naturally, and 100.0 means blocks will always break naturally.                                                                                                                                                                                                                                                                     | [Minimum] [default] is 0.0, which means blocks will never break naturally.<br> [Maximum] is 100.0, which means blocks will always break naturally.                                                                                                                            |
| `DropMaterial`              | Overrides the material that will be dropped when the block is broken. It can be a [Material](#materials), but note that not all materials are available for drops (liquids like `WATER`, for instance, can't be dropped)                                                                                                                                                                                                                                                          | [Default] is the same material as in the name of the section                                                                                                                                                                                                                  |
| `DistanceAttenuationFactor` | Indicates how effective damage decreases with distance. The value is a percentage, so 0.0 means that all blocks in range will take the same amount of damage, and 1.0 means that effective damage decreases linearly as distance increases.                                                                                                                                                                                                                                       | [Minimum] [default] is 0.0, which means that all blocks in range will take the same amount of damage.<br> [Maximum] is 1.0, which means that effective damage decreases linearly as distance increases.                                                                       |
| `UnderwaterDamageFactor`    | Damage multiplicative factor applied when explosion takes place underwater. The value is a percentage, so 0.0 means no damage will be taken underwater, and 1.0 means that water doesn't affect damage.                                                                                                                                                                                                                                                                           | [Minimum] is 0.0, which means no damage will be taken underwater.<br> [Default] is 0.5, which halves the damage taken in underwater explosions.<br> A value of 1.0 means that water doesn't affect damage.<br> A value greater than 1.0 means damage underwater is magnified. |
| `FancyUnderwaterDetection`  | Specifies when `UnderwaterDamageFactor` is applied. If set to false, all blocks will be considered to be underwater (and thus `UnderwaterDamageFactor` will be applied) if the center of the explosion happens to be underwater too. If set to true, the plugin will trace a ray from the explosion center to each block and will look for water in the path to each block individually, applying `UnderwaterDamageFactor` only to those blocks where water was found on the way. | False [default]: Look for water in the explosion center. (faster) <br> True: Trace a ray from the explosion center to each block and look for water. (fast)                                                                                                                   |
| `Particles`                 | This section allows to specify the particles that will spawn when the entity explodes.                                                                                                                                                                                                                                                                                                                                                                                            | See [Particle properties](#particle-properties)                                                                                                                                                                                                                               |
| `Sound`                     | This section allows to specify the sound to be played when the entity explodes.                                                                                                                                                                                                                                                                                                                                                                                                   | See [Sound properties](#sound-properties)                                                                                                                                                                                                                                     |

# Material properties

Outside of explosions, blocks (materials) also have properties that can be configured. The general structure is as follows:

```yaml
...(other global properties)

Materials:
    <MATERIAL/GROUP>:
        Properties:
            <PROPERTY>: <VALUE>
```

The properties that can be configured are as follows:

| Property                       | Description                                                                                                                                                                                                                                   | Values                        |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| `TimeToFullRegeneration`       | Time it takes for the material to regenerate completely, starting from zero durability to the default block durability. It can be specified as a [Duration](#duration). A duration of 0 means that regeneration is disabled for the material. | [Minimum] 0s<br> [Default] 0s |
| `DelayBeforeStartRegenerating` | Delay before the material starts regenerating after it was hit by a explosion. It can be specified as a [Duration](#duration). A duration of 0 means no delay.                                                                                | [Minimum] 0s<br> [Default] 0s |

Example of configuration:

```yaml
...(other global properties)

Materials:
    OBSIDIAN:
        TimeToFullRegeneration: 1m # (if it had 0 durability, it would take 1 minute to completely regenerate)
        DelayBeforeStartRegenerating: 10s # (wait 10 seconds before starting to regenerate)
```

# Utilities

## Groups

Groups are used to group multiple entities, materials or both together. They can be used in the same place of the configuration where entities and materials are accepted. If the group contains both materials and entities, then the plugin will choose the right one depending on the section where the group is used. When no item in the group is valid for the section, then the group will be considered invalid.

Groups can have any name, as long as it doesn't coincide with a valid material or entity name. If the name of the group coincides with a valid material or entity name, then the valid material or entity will be selected as the target, not the group.

It can happen that a group contains entities or materials that are also present individually in the same section. In this case, the plugin will merge all configurations depending on where the group is being used. The merging rules are as follows:

-   If the group is used in the `Entities` section, then materials will be merged as follows:

1. If an entity appears in two or more groups, then materials
   will try to merge without overwriting each other. If the same material appears
   listed in both groups, then the first occurrence of the material will be kept.
2. If an entity appears in two or more groups but also as an individual item,
   then rule 1 applies, and after that, the materials in the individual configuration will be merged,
   overwriting existing materials if necessary (individual configuration has greater priority than groups).

-   If the group is used in the `Materials` section, then entity-material configurations will be merged as follows:

1.  If a material appears in two or more groups, then the first occurrence of the entity-material configuration will be kept.
2.  If a material appears in two or more groups but also as an individual
    item, then the individual material will be kept.

Examples of groups and their usage:

```yaml
Groups:
    myUnbreakableBlockGroup:
        - OBSIDIAN
        - CRYING_OBSIDIAN
    myExplosiveEntityList:
        - PRIMED_TNT
        - CREEPER

VanillaEntity:
    myExplosiveEntityList:
        Properties:
            ExplosionDamageBlocksUnderwater: true
        Materials:
            myUnbreakableBlockGroup:
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
```

The above example is equivalent to the following configuration:

```yaml
VanillaEntity:
    PRIMED_TNT:
        Properties:
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
            CRYING_OBSIDIAN:
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
    CREEPER:
        Properties:
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
            CRYING_OBSIDIAN:
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
```

## Name wildcards

Name wildcards are used to match multiple entities or materials with a single name. Currently, the only wildcard available is `*` (asterisk), which can be used to match any number of characters in a given position of the name.

To be able to use the wildcard, it is necessary to enclose the name in double quotes:

```yaml
VanillaEntity:
    TNT:
        Properties:
            ExplosionDamageBlocksUnderwater: true
        Materials:
            "*_WOOL":
                Properties:
                    Damage: 50.0
                    DropChance: 50.0
```

For example, consider the following examples:

`*_OBSIDIAN` will match all materials that end with the word `_OBSIDIAN` in their name, such as `OBSIDIAN` and `CRYING_OBSIDIAN`

`*_WOOL` will match all materials that end with the word `WOOL` in their name, such as `WHITE_WOOL`, `ORANGE_WOOL`, `MAGENTA_WOOL`, etc.

`*CREEPER` will match all entities that end with the word `CREEPER` in their name, such as `CREEPER`, `CHARGED_CREEPER`, etc.

`TNT_*` will match all entities that start with the word `TNT` in their name, such as `TNT`, `TNT_MINECART`, etc.

`*` will match any material or entity name, so you can use it to list all materials or entities in the game.

# Type reference

## Duration

Durations can be specified in the following format:

| Unit | Example | Description     |
| ---- | ------- | --------------- |
| ms   | 1000ms  | Milliseconds    |
| s    | 1s      | Seconds         |
| m    | 1m      | Minutes         |
| h    | 1h      | Hours           |
| d    | 1d      | Days            |
| w    | 1w      | Weeks           |
| -    | 1       | Ticks (default) |

Remember that 1 second is 20 ticks, so 1s = 20 ticks.

Units can be combined by spaces, and do not need to be in any particular order. For example, the following formats are all valid:

-   `1h 30m` = 1 hour and 30 minutes
-   `1d 2h 3m 4s` = 1 day, 2 hours, 3 minutes and 4 seconds
-   `1w 2d 3h 4m 5s` = 1 week, 2 days, 3 hours, 4 minutes and 5 seconds
-   `1s 10` = 1 second and 10 ticks

## Materials

Materials are keys used to represent the different types of blocks and items that exist in the game. You can find a list of all the materials in the [official Spigot API](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html).

## Particles

Particles are keys used to represent the different types of particles that exist in the game. You can find a list of all the particles in the [official Spigot API](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Particle.html).

## Sounds

Sounds are keys used to represent the different types of sounds that exist in the game. You can find a list of all the sounds in the [official Spigot API](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html).
