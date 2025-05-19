## Getting started

Welcome to this crash course on ExplodeAny! Let's start with an empty configuration, which should look like this (don't worry if yours is different, some features might have been added since your version!):

```yaml
UseBlockDatabase: false
CheckBlockDatabaseAtStartup: false
BlockDurability: 100.0
EnableMetrics: true

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
    BossBarDuration: 30

Groups: {}

Materials: {}

VanillaEntity: {}

CannonProjectile: {}

MagicEntity: {}

QualityArmory: {}

ThrowableCreeperEggs: {}

CustomEntity: {}

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

DisabledWorlds: {}

WorldHoleProtection:
    # default:
    #     Heights:
    #         - -64 # Bedrock layer post-1.18
    #         - 0 # Bedrock layer pre-1.18
    # world_nether:
    #     Heights:
    #         - 127
    #         - 0
```

There are four interesting keys over there, `VanillaEntity`, `CannonProjectile`, `MagicEntity` and `CustomEntity`. All of them work pretty much the same way, but for simplicity, we will focus on `VanillaEntity`, since it refers to entities that exist in the base game. For example, when we talk about entities like TNT, creepers, withers, etc, we refer to `VanillaEntity` entities.

Let's imagine you want to add some configuration to a vanilla entity, for instance, TNT. You can start by placing the name of the entity in the proper section, like this:

```yaml
VanillaEntity:
    TNT: {}

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

You can see VanillaEntity is actually a section that can contain vanilla entity names (like TNT), which in turn represent another section.
This is where the materials that are going to be affected by that entity explosion should be listed. You can add the materials in the same as we did for the entity:

```yaml
VanillaEntity:
    TNT:
        OBSIDIAN: {}

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

As you can notice, the material itself starts another section, which represents how the OBSIDIAN block should be affected by a TNT explosion in particular.
There are many properties you can use to describe how a block should be affected by a given explosion. You can use all the properties are listed here: https://github.com/GuilleX7/ExplodeAny/blob/main/explodeany/src/main/resources/exampleConfig.yml#L196

These properties are:

-   `Damage`: represents the maximum damage that the block will take from the explosion
-   `DropChance`: the chance of the block breaking naturally, thus dropping an item when breaking
-   `DistanceAttenuationFactor`: which is a factor that specifies how the damage is attenuated due to distance. When it is 0, all blocks in range take the same damage (no attenuation). When it is 1, blocks that are farther from the center of the explosion will take much less damage than the blocks in the center. Value in between are also allowed, thus allowing gradual attenuation
-   `UnderwaterDamageFactor`: which is a factor that specifies how the damage is attenuated due to the explosion finding water in its trajectory. When it is 0, blocks underwater won't take damage. When it is 1, blocks underwater will take the same amount of damage than if they were on the surface. Values greater than 1 increase the damage that blocks underwater take
-   `FancyUnderwaterDetection`: this one specifies how water should be looked for in the explosion so that "UnderwaterDamageFactor" is applied or not. When it is false, the explosion will be considered underwater if its center is underwater, thus all blocks in range will have underwater attenuation. When it is true, a ray will be traced from the center to the explosion to each block individually, applying or not the underwater attenuation depending on whether there's water in between the center and each specific block
-   `Particles`: defines which particles (if any) should spawn when the block is broken by the explosion
-   `Sound`: defines which sound (if any) should be played when the block is broken by the explosion

So, for example, if we want OBSIDIAN to take 50.0 damage points from PRIMED_TNT and make it drop 50% of the times it is broken, we would end up with the following configuration:

```yaml
VanillaEntity:
    PRIMED_TNT:
        OBSIDIAN:
            Damage: 50.0
            DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

Actually, there's an alternative way of representing the same, which is the following:

```yaml
VanillaEntity:
    PRIMED_TNT:
        Materials:
            OBSIDIAN:
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

Notice that we just put the `Materials` key in between the `VanillaEntity` and the material. Both are equivalent, but the latter allows you to specify the so-called "entity properties", which define the properties of the explosion itself, and not how a given material is affected/damaged. Look at this example:

```yaml
VanillaEntity:
    PRIMED_TNT:
        Properties:
            # Here you can set properties of the explosion itself (when triggered by PRIMED_TNT)
            # This one in particular enables this explosion to do damage underwater!
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                # Here you can set how OBSIDIAN is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

The "entity properties" are given by the section `Properties` inside the proper entity. All the properties you can put in here are listed here: https://github.com/GuilleX7/ExplodeAny/blob/main/explodeany/src/main/resources/exampleConfig.yml#L68

These properties are:

-   `ExplosionRadius`: it allows you to override the original explosion radius of a given explosion, if it had any originally. For CustomEntity (for example, for explosions triggered by mods), this must be specified, since ExplodeAny has no way to know what the original radius of the explosion was.
-   `ExplosionFactor`: this is a multiplicative factor that will be applied to the explosion. If you want to magnify (or reduce) a given explosion radius, you can use this. Otherwise, keep it at 1.0.
-   `ReplaceOriginalExplosion`: this one can be tricky. If you just specify a ExplosionRadius, you will change the radius of the explosion **for ExplodeAny only**. That means, if a vanilla TNT is set a radius of 20.0, the vanilla explosion will break blocks in the original radius like usual, but ExplodeAny will try to damage blocks that are handled by it in a radius of 20 blocks. If you set this to true, then ExplodeAny will cancel the vanilla explosion and, instead, will trigger another vanilla explosion that is capable of damaging blocks up to 20 blocks away, thus truly increasing the explosion radius.
-   `UnderwaterExplosionFactor`: this allows to magnify/reduce the radius of the explosion when it takes place underwater
-   `ExplosionDamageBlocksUnderwater`: normally, vanilla explosions do not break blocks underwater. However, if you set this to true, ExplodeAny will do something similar to `ReplaceOriginalExplosion`, and it will replace the original vanilla explosion with another one capable of breaking blocks underwater. You can have both `ReplaceOriginalExplosion` and `ExplosionDamageBlocksUnderwater` set to true, since ExplodeAny will replace the original explosion with a proper explosion according to the configuration.
-   `ExplosionRemoveWaterloggedStateFromNearbyBlocks`: just says if the explosion should remove waterlogged state from blocks affected by the explosion
-   `ExplosionRemoveNearbyWaterloggedBlocks`: just says if the explosion should remove waterlogged blocks affected by the explosion
-   `ExplosionRemoveNearbyLiquids`: just says if the explosion should remove liquids affected by the explosion
-   `PackDroppedItems`: this is only useful in case you are planning to have huge explosions (with radius > 20) and high drop chances, where A LOT of dropped items can appear at once. In that case, having this set to true will try to combine many dropped items into packs, preventing clients from crashing due to having to render a huge amount of items.
-   `Particles` and `Sound`: same as before, but this time they will spawn every time the explosion takes place.

That's everything about configuring entities and explosions. But there's something more: in exampleConfig.yml, you can notice I didn't used regular VanillaEntity names (like PRIMED_TNT) nor material names (like OBSIDIAN), but rather used `unbreakableBlocks` and `dangerousEntity`. These names represent groups, which are a collection of entities or materials that can be used to specify properties for many entities/materials at the same time. You can even mix entities and materials within the same group: ExplodeAny will know which names are appropiate for the section you are using the group in.

So, we had the following configuration:

```yaml
VanillaEntity:
    PRIMED_TNT:
        Properties:
            # Here you can set properties of the explosion itself (when triggered by PRIMED_TNT)
            # This one in particular enables this explosion to do damage underwater!
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                # Here you can set how OBSIDIAN is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

We could achieve the same by having the following:

```yaml
Groups:
    MyBelovedEntities:
        - PRIMED_TNT
    MyBelovedMaterials:
        - OBSIDIAN

VanillaEntity:
    MyBelovedEntities:
        Properties:
            # Here you can set properties of the explosion itself (when triggered by PRIMED_TNT)
            # This one in particular enables this explosion to do damage underwater!
            ExplosionDamageBlocksUnderwater: true
        Materials:
            MyBelovedMaterials:
                # Here you can set how OBSIDIAN is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

As you can see, we defined two different groups, one containing an entity (PRIMED_TNT) and another one containing a material (OBSIDIAN). They can be used in the same way as the original names. If you're wondering what's the advantage of this approach, consider the following configuration:

```yaml
VanillaEntity:
    PRIMED_TNT:
        Properties:
            # Here you can set properties of the explosion itself (when triggered by PRIMED_TNT)
            # This one in particular enables this explosion to do damage underwater!
            ExplosionDamageBlocksUnderwater: true
        Materials:
            OBSIDIAN:
                # Here you can set how OBSIDIAN is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0
            CRYING_OBSIDIAN:
                # Here you can set how CRYING_OBSIDIAN is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0
            BEDROCK:
                # Here you can set how BEDROCK is affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

This configuration is perfectly fine, but we had to repeat the same properties for each material (OBSIDIAN, CRYING_OBSIDIAN and BEDROCK) affected by PRIMED_TNT. If we were to change the properties of all of them, we would have to change them in three different places. Instead, if they share the same properties, we can define a group that contains all of them and replace them with just a single section, like this:

```yaml
Groups:
    UnbreakableBlocks:
        - OBSIDIAN
        - CRYING_OBSIDIAN
        - BEDROCK

VanillaEntity:
    PRIMED_TNT:
        Properties:
            # Here you can set properties of the explosion itself (when triggered by PRIMED_TNT)
            # This one in particular enables this explosion to do damage underwater!
            ExplosionDamageBlocksUnderwater: true
        Materials:
            UnbreakableBlocks:
                # Here you can set how OBSIDIAN, CRYING_OBSIDIAN and BEDROCK are affected by explosions triggered by PRIMED_TNT
                Damage: 50.0
                DropChance: 50.0

CannonProjectile: {}

MagicEntity: {}

CustomEntity: {}
```

Notice how everything we explained here is applicable for every other section in the file (`CannonProjectile`, `MagicEntity` and `CustomEntity`). Just make sure to check which are the allowed values for the entities in these sections!

## Compatibility with mods

ExplodeAny is a plugin that uses the Bukkit API, so it can't interact in any way with mods. That means there's no way for ExplodeAny to detect or add direct support for mods that run on Forge, Fabric or any other mod loader like we do for other plugins. However, we tried my best to increase ExplodeAny compatibility with those mods, so we came up with a new section, `CustomEntity`, that _might_ be useful for your use case if you're reading this.

While ExplodeAny doesn't know about mods, it _does_ know about entities and blocks registered in the Vanilla registry. Behind the scenes, there are some ways a mod can add explosions to the game. Very often, they do one of the following:

1. The mod registers a new entity that causes an explosion
2. The mod uses an already registered entity that causes an explosion (like vanilla TNT)
3. The mod uses a completely custom explosion algorithm

If the mod goes the first way, you're lucky: ExplodeAny should be able to detect the new entity type, and you should be able to use it in the `CustomEntity` section, like you would do in any other section! But, how do you know if the mod has been implemented like that? And how do you know which entity type to use?

The answer is enabling ExplodeAny's debug mode. You can enable it using `/eany debug enable` or `/eany debug on`, and disable it with `/eany debug disable` or `/eany debug off`. When enabled, ExplodeAny will log every explosion that has detected (even if it's not configured, of course!) along with the entity that caused it on the server console. That means you should be able to see from vanilla entities to custom entities, so as soon as you detonate something that creates a modded explosion, you will be able to understand the way ExplodeAny sees it internally. As explained before, there are three posibilities:

1. The mod registers a new entity that causes an explosion <-> ExplodeAny will log out something like `Detected custom entity explosion. Entity type: XXX`, where XXX is the name of the entity added by the mod, and the name that you can use inside the `CustomEntity` section to configure it! Remember that it is mandatory for entities inside `CustomEntity` to specify their `ExplosionRadius`, as ExplodeAny can't guess the explosion radius.

e.g.

```yaml
CustomEntity:
    # This ID comes from the ExplodeAny debug mode, enable it with /eany debug enable!
    CREATEBIGCANNONS_SHOT:
        # Remember that specifying the ExplosionRadius is mandatory here!
        Properties:
            ExplosionRadius: 5.0
        Materials:
            # You can use the same properties as in other sections
            OBSIDIAN:
                # Disable obsidian being damaged by Create Big Cannons regular shots
                Damage: 0.0
```

2. The mod uses an already registered entity that causes an explosion (like vanilla TNT) <-> ExplodeAny will log out something like `Detected vanilla entity explosion. Entity type: XXX`, where XXX is the name of a vanilla entity. This means the explosion looks as a XXX vanilla explosion for ExplodeAny, so there's no way to tell them apart, but you can configure that vanilla explosion and it will be applied to the mod too.
3. The mod uses a completely custom explosion algorithm <-> ExplodeAny won't log out anything! If you see an explosion but not a message on the console, then ExplodeAny is not able to recognize what happened. In this case, unfortunately, there's nothing you can do to configure it.
