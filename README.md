# AnyBan

AnyBan is a punishment plugin for BungeeCord, and designed to be like a BungeeCord version of the vanilla punishment system in Spigot.

## Features

- Rich tab completion
- Full compatible with data in Spigot

## Usage

### Commands

All commands have the same name as vanilla Minecraft one, with an "a" prefix.

| Command                            | Permission node            | Description                                     |
|------------------------------------|----------------------------|-------------------------------------------------|
| `/aban <player> [<reason>]`        | `anyban.command.ban`       | Adds the player into the proxy blacklist        |
| `/aban-ip <player\|ip> [<reason>]` | `anyban.command.ban-ip`    | Adds the IP address into the proxy blacklist    |
| `/abanlist [<players\|ips>]`       | `anyban.command.banlist`   | Displays the proxy blacklist                    |
| `/apardon <player>`                | `anyban.command.pardon`    | Removes the player from proxy blacklist         |
| `/apardon-ip <ip>`                 | `anyban.command.pardon-ip` | Removes the IP address from the proxy blacklist |

### Import data from Spigot

You can import following files from your Spigot server:

- `banned-players.json`
- `banned-ips.json`
- `usercache.json`

`banned-players.json` and `banned-ips.json` are bans data, and `usercache.json` is a cache file which stores players' UUID and can be used in tab completion of this plugin.

Make sure that a proxy is stopping while copying these files.
