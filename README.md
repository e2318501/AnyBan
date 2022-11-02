# AnyBan

AnyBan is a punishment plugin for BungeeCord. It is designed to be like a BungeeCord version of the default ban system in Spigot.

## Features

- Full compatible with ban data in Spigot
- Offline ban/ip-ban
- Never-joined players support
- Floodgate players support
- Rich tab completion

## Usage

### Commands

Some commands have the same name with an "a" prefix as in Spigot.

| Command                                       | Permission node            | Description                                                           |
|-----------------------------------------------|----------------------------|-----------------------------------------------------------------------|
| `/aban <player>`                              | `anyban.command.ban`       | Bans a player                                                         |
| `/aunban <player>`, `/apardon <players>`      | `anyban.command.pardon`    | Unbans a player                                                       |
| `/aisban <player>`                            | `anyban.command.isban`     | Shows whether the player is banned or not, and more info if banned    |
| `/aban-ip <player>`                           | `anyban.command.ban-ip`    | Bans a player by his/her IP                                           |
| `/aunban-ip <player>`, `/apardon-ip <player>` | `anyban.command.pardon-ip` | Unbans a player banned by his/her IP                                  |
| `/aisban-ip <player>`                         | `anyban.command.isban-ip`  | Shows whether the player is ip-banned or not, and more info if banned |
| `/anyban reload`                              | `anyban.admin`             | Reloads a config from `config.yml`                                    |                                          

### Data

You can import data of ban/ban-ip in Spigot by copying `banned-players.json` and `banned-ips.json` into the folder of the plugin. Make sure that a proxy is stopping while copying.

### Floodgate support

If you want to ban a Floodgate player who has never joined a network, make sure that Floodgate is installed in BungeeCord and turn `floodgateEnabled` in `config.yml` to true.
