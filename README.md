![ExplodeAny logo](https://raw.githubusercontent.com/GuilleX7/ExplodeAny/main/etc/logo-1-1.png)

# ExplodeAny

Revamp your Minecraft explosions! Control any blast with ExplodeAny - the ultimate TNT plugin. From TNT breaking obsidian to underwater explosions causing damage. Easily customizable, lightweight, compatible with any Bukkit version better than 1.8+. Get Ready To Explode Like Never Before!™

Official pages:

[Spigot](https://www.spigotmc.org/resources/explodeany-obsidian-breaker-tnt-explosion-modifier.85537/)

[Modrinth](https://modrinth.com/plugin/explodeany)

[CurseForge - Bukkit](https://www.curseforge.com/minecraft/bukkit-plugins/explodeany-official)

[Hangar](https://hangar.papermc.io/GuilleX7/ExplodeAny)

## Building

This repository contains a multi-module Maven project. In order to build it, you will need at least a valid installation of JDK 8 and Maven.

To generate a JAR file, open a console and type `mvn clean` at the root directory. This will install some local dependencies. Then, type `mvn package` at the root directory to build the entire project. The final JAR file will be placed in the `explodeany/target` folder.

Every dependency should be automatically resolved by Maven using both remote repositories and the local Maven repository (check the `pom.xml` file)
