package io.github.portlek.spawner;

import io.github.portlek.hologram.base.Holograms;
import io.github.portlek.inventory.Inventories;
import io.github.portlek.mcyaml.YamlOf;
import io.github.portlek.spawner.command.CommandChestSpawner;
import io.github.portlek.spawner.command.CommandItemSpawner;
import io.github.portlek.spawner.files.ConfigOptions;
import io.github.portlek.spawner.hook.VaultHook;
import io.github.portlek.spawner.hook.VaultWrapper;
import io.github.portlek.spawner.listener.chest.ChestBreak;
import io.github.portlek.spawner.listener.chest.ChestClick;
import io.github.portlek.spawner.listener.chest.ChestPlace;
import io.github.portlek.spawner.listener.chest.SignBreak;
import io.github.portlek.spawner.listener.chest.SignClick;
import io.github.portlek.spawner.listener.natural.SpawnerBreak;
import io.github.portlek.spawner.listener.natural.SpawnerClick;
import io.github.portlek.spawner.listener.natural.SpawnerPlace;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.cactoos.list.ListOf;

public final class Spawner extends JavaPlugin {
   public void onEnable() {
      if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
         VaultHook hook = new VaultHook();
         if (hook.initiate()) {
            VaultWrapper vaultWrapper = new VaultWrapper(hook.get());
            Bukkit.getConsoleSender().sendMessage("   ");
            Bukkit.getConsoleSender().sendMessage("  ___            __                       _         ");
            Bukkit.getConsoleSender().sendMessage(" |_ _|  _ __    / _|  _   _   _ __ ___   (_)   __ _ ");
            Bukkit.getConsoleSender().sendMessage("  | |  | '_ \\  | |_  | | | | | '_ ` _ \\  | |  / _` |");
            Bukkit.getConsoleSender().sendMessage("  | |  | | | | |  _| | |_| | | | | | | | | | | (_| |");
            Bukkit.getConsoleSender().sendMessage(" |___| |_| |_| |_|    \\__,_| |_| |_| |_| |_|  \\__,_|");
            Bukkit.getConsoleSender().sendMessage("    ");
            PluginCommand itemSpawnerCommand = this.getCommand("itemsp");
            PluginCommand chestSpawnerCommand = this.getCommand("sandiksp");
            if (itemSpawnerCommand != null && chestSpawnerCommand != null) {
               SpawnerAPI spawnerAPI = new SpawnerAPI(this, vaultWrapper, new YamlOf(this, "natural/menuler", "menu"), new YamlOf(this, "chest/menuler", "chesticimenu"), new YamlOf(this, "chest/menuler", "genelayarlarmenu"), new YamlOf(this, "chest/menuler", "ganimetdeposumenu"), new YamlOf(this, "chest/menuler", "sandikayarlarmenu"), new YamlOf(this, "chest/menuler", "yetkililermenu"), new YamlOf(this, "natural", "spawners"), new YamlOf(this, "chest", "spawners"), new YamlOf(this, "chest", "users"), new Holograms(this, new YamlOf(this, "natural", "holograms")), new ConfigOptions(new YamlOf(this, "config")), new YamlOf(this, "natural", "placed"), new YamlOf(this, "chest", "placed"));
               this.getServer().getScheduler().runTaskTimer(this, () -> {
                  SpawnerAPI.PLACED_CHESTS.values().forEach(ChestPlaced::updateSign);
               }, 0L, 20L);
               spawnerAPI.reloadPlugin();
               (new Inventories()).prepareFor(this);
               (new ListOf(new Listener[]{new SpawnerClick(spawnerAPI), new SpawnerBreak(spawnerAPI), new SpawnerPlace(spawnerAPI), new ChestClick(spawnerAPI), new ChestBreak(spawnerAPI), new ChestPlace(spawnerAPI), new SignClick(spawnerAPI), new SignBreak(spawnerAPI)})).forEach((listener) -> {
                  this.getServer().getPluginManager().registerEvents((Listener) listener, this);
               });
               CommandChestSpawner commandChestSpawner = new CommandChestSpawner(spawnerAPI);
               CommandItemSpawner commandItemSpawner = new CommandItemSpawner(spawnerAPI);
               itemSpawnerCommand.setExecutor(commandItemSpawner);
               itemSpawnerCommand.setTabCompleter(commandItemSpawner);
               chestSpawnerCommand.setExecutor(commandChestSpawner);
               chestSpawnerCommand.setTabCompleter(commandChestSpawner);
            }
         }
      }
   }
}
