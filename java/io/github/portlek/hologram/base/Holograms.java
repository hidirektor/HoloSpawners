package io.github.portlek.hologram.base;

import io.github.portlek.hologram.api.Hologram;
import io.github.portlek.hologram.api.MckHologram;
import io.github.portlek.location.LocationOf;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.mcyaml.mck.MckFileConfiguration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class Holograms implements Listener {
   private final HashMap<UUID, Hologram> holograms = new HashMap();
   @NotNull
   private final Plugin plugin;
   @NotNull
   private final IYaml yaml;

   public Holograms(@NotNull Plugin plugin, @NotNull IYaml yaml) {
      this.plugin = plugin;
      this.yaml = yaml;
   }

   public void initHologram() {
      (new ListenerBasic<>(PluginDisableEvent.class, (event) -> {
         if (event.getPlugin().equals(this.plugin)) {
            this.holograms.values().forEach(Hologram::remove);
         }
      })).register(this.plugin);
      this.reloadHolograms();
   }

   public void reloadHolograms() {
      ConfigurationSection section = this.yaml.getSection("Holograms");
      if (section instanceof MckFileConfiguration) {
         section = this.yaml.createSection("Holograms");
      }

      Iterator var2 = section.getKeys(false).iterator();

      while(var2.hasNext()) {
         String id = (String)var2.next();
         List<String> lines = this.yaml.getStringList("Holograms." + id + ".lines");
         Location location = (new LocationOf((String)this.yaml.getString("Holograms." + id + ".location").orElse(""))).value();
         Hologram hologram = new HologramOf(location, lines);
         this.holograms.put(UUID.fromString(id), hologram);
         hologram.spawn();
      }

   }

   @NotNull
   public Hologram getHologram(@NotNull UUID uuid) {
      return (Hologram)this.holograms.getOrDefault(uuid, new MckHologram());
   }

   @NotNull
   public Hologram createHologram(@NotNull Location location, @NotNull List<String> lines) {
      return this.createHologramWithId(UUID.randomUUID(), location, lines);
   }

   @NotNull
   public Hologram createHologramWithId(@NotNull UUID uuid, @NotNull Location location, @NotNull List<String> lines) {
      HologramOf hologram = new HologramOf(location, lines);
      this.holograms.put(uuid, hologram);
      this.saveHologram();
      return hologram;
   }

   public void removeHologram(@NotNull UUID uuid) {
      this.yaml.set("Holograms." + uuid, (Object)null);
      if (this.holograms.containsKey(uuid)) {
         this.getHologram(uuid).remove();
      }

      this.holograms.remove(uuid);
      this.saveHologram();
   }

   public void removeHolograms() {
      this.yaml.set("Holograms", (Object)null);
      this.holograms.values().forEach(Hologram::remove);
      this.holograms.clear();
   }

   private void saveHologram() {
      this.holograms.forEach((uuid, hologram) -> {
         hologram.save(this.yaml, uuid);
      });
   }
}
