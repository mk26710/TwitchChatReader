<img src="https://raw.githubusercontent.com/httpolar/TwitchChatReader/1.20.x/src/main/resources/assets/twitchchatreader/icon.png" width="128" alt="Mod Icon">

# TwitchChatReader

A Minecraft fabric mod that brings Twitch chat right into the game!

![](https://cdn-raw.modrinth.com/data/se6JUdf1/images/72548e9388b08bc1a4ad5e35500171d536f6a0dd.png)

### Installation

You can find latest versions of the mod on [GitHub releases](https://github.com/httpolar/TwitchChatReader/releases) and/or [Modrinth page](https://modrinth.com/mod/twitchchatreader). In order for this mod to function you will also need to install [Fabric API](https://modrinth.com/mod/fabric-api).

To install the mod simply move the JAR file into your mods folder after installing Fabric and Fabric API.

### How to use

 - Run `/twitch connect <username>` replacing `<username>` with someones twitch.tv channel name in order to connect to someone's chat.
 - Run `/twitch disconnect` to destroy the connection if there's any connection existing right now.
 - Run `/twitch config reload` to hot-reload the configuration file (if you edit the config file you don't have to restart the game, just use this command)

### Configuration

Configuration file is located in `.minecraft/config/twitchchatreader.json`, and it should look like this by default


```json
{
  "prefixes": {
    "global": null,
    "followers": null,
    "subscribers": "&5[SUB]",
    "moderators": "&2[MOD]",
    "vips": "&5[VIP]"
  }
}
```
