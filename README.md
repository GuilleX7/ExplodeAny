![ExplodeAny logo](https://raw.githubusercontent.com/GuilleX7/ExplodeAny/main/etc/logo-1-1.png)

# ExplodeAny

Revamp your Minecraft explosions! Control any blast with ExplodeAny - the ultimate TNT plugin. From TNT breaking obsidian to underwater explosions causing damage. Easily customizable, lightweight, compatible with any Bukkit version better than 1.7+. Get Ready To Explode Like Never Before!™

## Features

- Allows to configure how **every block** is affected by **every explosion**:
    - **Obsidian and bedrock** can be broken with **TNT**
    - Dirt can become **indestructible**
    - ...

- **Spawn particles, play sounds...** configure what happens when:
    - An entity explodes
    - A block is broken because of the explosion

- **Allow explosions to break blocks underwater!** Not only TNT, but every explosion can break underwater.

- **Damage persists across server restarts** (if you want, of course :))

- **Really easy to configure,** we provide you with a sample configuration that contains every property you can use!

- Lightweight, **performance** oriented

- Support for **other plugins**

## Compatibility

ExplodeAny is compatible with Bukkit 1.7+ and Java 8+. However, older versions of Bukkit might not have support for the whole set of features. Here are some caveats:

**Bukkit 1.17 or higher**

Congrats, you have full compatibility!

**Bukkit 1.16 or lower**

- No compatibility with Magic: Magic explosions will be considered Vanilla

**Bukkit 1.13 or lower**

- Reduced particle compatibility: "force" will not be used when spawning particles

**Bukkit 1.8 or lower**

- No particles at all :(
- Vanilla entity "DRAGON_FIREBALL" is not supported

*Please note that support for entities, materials, particles and sounds completely depend on the Bukkit version you are using*

## Current supported plugins

Although they’re **not** necessary for the plugin to work, ExplodeAny will enable support for them if they are loaded.

- Cannons (https://www.spigotmc.org/resources/cannons.56764/)
- Magic (https://www.spigotmc.org/resources/magic.1056/)

## Building

This repository contains a multi-module Maven project. In order to build it, you will need at least a valid installation of JDK 8 and Maven.

To generate a JAR file, open a console and type `mvn package` at the root directory to build the entire project. The final JAR file will be placed in the `explodeany/target` folder. 

Every dependency should be automatically resolved by Maven using our repositories (check the `pom.xml` file)
