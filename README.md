![ExplodeAny logo](https://raw.githubusercontent.com/GuilleX7/ExplodeAny/main/etc/logo-1-1.png)
# ExplodeAny
Bukkit plugin that **allows to control any explosion.** It focuses on allowing certain types of explosions damage **vanilla-unbreakable blocks,** or even disabling damage for blocks that would break in regular conditions!
It supports all Vanilla explosions and some other plugins' explosions, though there is only support for Cannons currently. Suggestions for adding compatibility with other plugins (or any other kind of suggestion/question/whatever) are welcome!

## Features
 - Allows to configure the breakage of **vanilla-unbreakable** blocks
 - Each pair of type of explosion and damaged block can be easily **customized**
 - **Easy** to configure
 - Lightweight, **performance** oriented
 - Support for **other plugins**

## Current supported plugins
Although they're **NOT** necessary for the plugin to work, ExplodeAny will enable support for them if they are loaded.

 - Cannons (https://dev.bukkit.org/projects/cannons)

## Building
This project uses Maven for building. However, some dependencies (Cannons) have to be supplied in a local repository. This proccess in completely automated.

For compiling, just type `mvn clean` in order to install dependencies in the local Maven repository, and then type `mvn install`.


