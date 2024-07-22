<img src="https://raw.githubusercontent.com/httpolar/TwitchChatReader/1.20.x/src/main/resources/assets/twitchchatreader/icon.png" width="128" alt="Mod Icon">

# TwitchChatReader

A Minecraft fabric mod that brings Twitch chat right into the game!

![](https://cdn-raw.modrinth.com/data/se6JUdf1/images/72548e9388b08bc1a4ad5e35500171d536f6a0dd.png)

### Installation

You can find latest versions of the mod on [GitHub releases](https://github.com/httpolar/TwitchChatReader/releases)
and/or [Modrinth page](https://modrinth.com/mod/twitchchatreader). In order for this mod to function you will also need
to install [Fabric API](https://modrinth.com/mod/fabric-api).

To install the mod simply move the JAR file into your mods folder after installing Fabric and Fabric API.

### Commands

- `/twitch connect <username>` - connect to specified Twitch channel's chat
- `/twitch disconnect` - disconnect from currently connected chat
- `/twitch config reload` - reloads configuration file
- `/twitch config prefix <role> get` - displays current prefix for the specified chat role (global, subs, mods)
- `/twitch config prefix <role> set <prefix>` - sets a new prefix for the specified chat role
- `/twitch config prefix <role> unset` - removes prefix for the specified chat role

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
