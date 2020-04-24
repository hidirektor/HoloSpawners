package io.github.portlek.spawner.command;

import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import io.github.portlek.spawner.util.Util;
import java.util.Iterator;
import java.util.List;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cactoos.list.ListOf;
import org.jetbrains.annotations.NotNull;

public class CommandItemSpawner implements TabExecutor {
   @NotNull
   private final SpawnerAPI spawnerAPI;

   public CommandItemSpawner(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      Player phover = (Player) sender;
      TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
      msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439").create()));
      if (args.length > 4) {
         if (sender instanceof Player) {
            sender.sendMessage(this.spawnerAPI.getLanguage().commands);
            phover.spigot().sendMessage(msg);
         } else {
            sender.sendMessage(this.spawnerAPI.getLanguage().commands);
         }
         return true;
      } else if (args.length == 0) {
         if (sender instanceof Player) {
            sender.sendMessage(this.spawnerAPI.getLanguage().commands);
            phover.spigot().sendMessage(msg);
         } else {
            sender.sendMessage(this.spawnerAPI.getLanguage().commands);
         }
         return true;
      } else {
         String arg1 = args[0];
         if (args.length == 1) {
            if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandReload)) {
               if (!sender.hasPermission("zpawner.reload")) {
                  sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                  return true;
               } else {
                  this.spawnerAPI.reloadPlugin();
                  sender.sendMessage(this.spawnerAPI.getLanguage().generalReloadComplete);
                  return true;
               }
            } else {
               if (sender instanceof Player) {
                  sender.sendMessage(this.spawnerAPI.getLanguage().commands);
                  phover.spigot().sendMessage(msg);
               } else {
                  sender.sendMessage(this.spawnerAPI.getLanguage().commands);
               }
               return true;
            }
         } else {
            String arg2 = args[1];
            Player player;
            if (args.length == 2) {
               if (!sender.hasPermission("zpawner.distribute")) {
                  sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                  return true;
               } else if (!arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerDistribute)) {
                  if (sender instanceof Player) {
                     sender.sendMessage(this.spawnerAPI.getLanguage().commands);
                     phover.spigot().sendMessage(msg);
                  } else {
                     sender.sendMessage(this.spawnerAPI.getLanguage().commands);
                  }
                  return true;
               } else {
                  SpawnerType spawner = this.spawnerAPI.findSpawnerTypeById(arg2);
                  if (Util.spawnerNotFound(this.spawnerAPI.getLanguage(), sender, spawner)) {
                     return true;
                  } else {
                     Iterator var14 = Bukkit.getOnlinePlayers().iterator();

                     while(var14.hasNext()) {
                        player = (Player)var14.next();
                        Util.giveSpawner(this.spawnerAPI.getLanguage(), sender, player, (SpawnerType)spawner, arg2, 1);
                     }

                     return true;
                  }
               }
            } else {
               String arg3 = args[2];
               if (args.length == 3) {
                  if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerGive)) {
                     if (!sender.hasPermission("zpawner.give")) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                        return true;
                     } else if (Util.playerNotFound(this.spawnerAPI.getLanguage(), sender, arg2)) {
                        return true;
                     } else {
                        player = Bukkit.getPlayer(arg2);
                        SpawnerType spawner = this.spawnerAPI.findSpawnerTypeById(arg3);
                        if (Util.spawnerNotFound(this.spawnerAPI.getLanguage(), sender, spawner)) {
                           return true;
                        } else {
                           assert player != null;

                           Util.giveSpawner(this.spawnerAPI.getLanguage(), sender, player, (SpawnerType)spawner, arg3, 1);
                           return true;
                        }
                     }
                  } else if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerDistribute)) {
                     SpawnerType spawner = this.spawnerAPI.findSpawnerTypeById(arg2);
                     if (!sender.hasPermission("zpawner.distribute")) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                        return true;
                     } else if (!Util.spawnerNotFound(this.spawnerAPI.getLanguage(), sender, spawner) && !Util.isNotNumber(this.spawnerAPI.getLanguage(), sender, arg3)) {
                        Iterator var16 = Bukkit.getOnlinePlayers().iterator();

                        while(var16.hasNext()) {
                           Player p = (Player)var16.next();
                           Util.giveSpawner(this.spawnerAPI.getLanguage(), sender, p, spawner, arg2, Integer.parseInt(arg3));
                        }

                        return true;
                     } else {
                        return true;
                     }
                  } else if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerBreaker)) {
                     if (!sender.hasPermission("zpawner.distribute")) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                        return true;
                     } else if (Util.playerNotFound(this.spawnerAPI.getLanguage(), sender, arg2)) {
                        return true;
                     } else {
                        player = Bukkit.getPlayer(arg2);
                        ItemStack breaker = this.spawnerAPI.getConfigs().spawnerBreakers.getBreaker(arg3).getItemStack();
                        if (breaker.getType() == Material.AIR) {
                           sender.sendMessage(this.spawnerAPI.getLanguage().errorBreakerNotFound);
                           return true;
                        } else {
                           assert player != null;

                           Util.giveBreaker(this.spawnerAPI.getLanguage(), sender, player, breaker, arg3, 1);
                           return true;
                        }
                     }
                  } else {
                     if (sender instanceof Player) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().commands);
                        phover.spigot().sendMessage(msg);
                     } else {
                        sender.sendMessage(this.spawnerAPI.getLanguage().commands);
                     }
                     return true;
                  }
               } else {
                  String arg4 = args[3];
                  if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerGive)) {
                     if (!sender.hasPermission("zpawner.give")) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                        return true;
                     } else if (Util.playerNotFound(this.spawnerAPI.getLanguage(), sender, arg2)) {
                        return true;
                     } else {
                        player = Bukkit.getPlayer(arg2);
                        SpawnerType spawner = this.spawnerAPI.findSpawnerTypeById(arg3);
                        if (!Util.spawnerNotFound(this.spawnerAPI.getLanguage(), sender, spawner) && !Util.isNotNumber(this.spawnerAPI.getLanguage(), sender, arg4)) {
                           assert player != null;

                           Util.giveSpawner(this.spawnerAPI.getLanguage(), sender, player, spawner, arg3, Integer.parseInt(arg4));
                           return true;
                        } else {
                           return true;
                        }
                     }
                  } else if (arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerBreaker)) {
                     if (!sender.hasPermission("zpawner.breaker")) {
                        sender.sendMessage(this.spawnerAPI.getLanguage().errorPermission);
                        return true;
                     } else if (Util.playerNotFound(this.spawnerAPI.getLanguage(), sender, arg2)) {
                        return true;
                     } else {
                        player = Bukkit.getPlayer(arg2);
                        ItemStack breaker = this.spawnerAPI.getConfigs().spawnerBreakers.getBreaker(arg3).getItemStack();
                        if (breaker.getType() == Material.AIR) {
                           sender.sendMessage(this.spawnerAPI.getLanguage().errorBreakerNotFound);
                           return true;
                        } else if (Util.isNotNumber(this.spawnerAPI.getLanguage(), sender, arg4)) {
                           return true;
                        } else {
                           assert player != null;

                           Util.giveBreaker(this.spawnerAPI.getLanguage(), sender, player, breaker, arg3, Integer.parseInt(arg4));
                           return true;
                        }
                     }
                  } else {
                     return false;
                  }
               }
            }
         }
      }
   }

   @NotNull
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
      if (!sender.hasPermission("zpawner.give") && !sender.hasPermission("zpawner.distribute") && !sender.hasPermission("zpawner.breaker")) {
         return new ListOf(new String[0]);
      } else if (args.length > 4) {
         return new ListOf(new String[0]);
      } else if (args.length == 0) {
         return this.spawnerAPI.getLanguage().getItemSpawnerCommands();
      } else {
         String arg1 = args[0];
         String lastWord = args[args.length - 1];
         if (args.length == 1) {
            return Util.listArgument(this.spawnerAPI.getLanguage().getItemSpawnerCommands(), lastWord);
         } else {
            boolean isGive = arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerGive);
            boolean isBreaker = arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerBreaker);
            if (args.length == 2) {
               return !isGive && !isBreaker ? Util.listSpawner(this.spawnerAPI, lastWord) : Util.listPlayer(sender, lastWord);
            } else if (args.length == 3) {
               if (isGive) {
                  return Util.listSpawner(this.spawnerAPI, lastWord);
               } else {
                  return (List)(arg1.equalsIgnoreCase(this.spawnerAPI.getLanguage().commandItemSpawnerDistribute) ? new ListOf(new String[]{"[<amount>]"}) : Util.listBreaker(this.spawnerAPI.getConfigs().spawnerBreakers, lastWord));
               }
            } else {
               return !isGive && !isBreaker ? new ListOf(new String[0]) : new ListOf(new String[]{"[<amount>]"});
            }
         }
      }
   }
}
