package io.github.portlek.spawner.spawners.natural.placed;

import io.github.portlek.hologram.base.Holograms;
import io.github.portlek.location.StringOf;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import java.util.UUID;
import org.bukkit.block.CreatureSpawner;
import org.jetbrains.annotations.NotNull;

public final class SpawnerPlaced {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   public final UUID uuid;
   @NotNull
   public final UUID owner;
   @NotNull
   public final CreatureSpawner creatureSpawner;
   @NotNull
   public final SpawnerType spawnerType;
   @NotNull
   public final Holograms holograms;
   public boolean hologramActive;
   public int size;

   public SpawnerPlaced(@NotNull SpawnerAPI spawnerAPI, @NotNull UUID uuid, @NotNull UUID owner, @NotNull CreatureSpawner creatureSpawner, @NotNull SpawnerType spawnerType, @NotNull Holograms holograms, int size) {
      this.spawnerAPI = spawnerAPI;
      this.uuid = uuid;
      this.owner = owner;
      this.creatureSpawner = creatureSpawner;
      this.spawnerType = spawnerType;
      this.holograms = holograms;
      this.size = size;
   }

   public SpawnerPlaced(@NotNull SpawnerAPI spawnerAPI, @NotNull UUID uuid, @NotNull UUID owner, @NotNull CreatureSpawner creatureSpawner, @NotNull SpawnerType spawnerType, @NotNull Holograms holograms) {
      this(spawnerAPI, uuid, owner, creatureSpawner, spawnerType, holograms, 1);
   }

   public void remove() {
      this.hologramActive = false;
      this.holograms.removeHologram(this.uuid);
   }

   public void setSize(int size) {
      this.size = size;
   }

   public void update() {
      IYaml placed = this.spawnerAPI.placedSpawner;
      this.spawnerType.create(this.creatureSpawner, this.size, this.owner);
      placed.set("Spawners." + this.uuid.toString() + ".location", (new StringOf(this.creatureSpawner.getLocation())).asKey());
      placed.set("Spawners." + this.uuid.toString() + ".size", this.size);
   }

   public void saveTo(@NotNull IYaml yaml) {
      yaml.set("Spawners." + this.uuid.toString() + ".location", (new StringOf(this.creatureSpawner.getLocation())).asKey());
      yaml.set("Spawners." + this.uuid.toString() + ".size", this.size);
   }
}
