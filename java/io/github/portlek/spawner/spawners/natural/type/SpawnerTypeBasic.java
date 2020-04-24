package io.github.portlek.spawner.spawners.natural.type;

import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.itemstack.util.ColoredList;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.base.ItemStackNBTOf;
import io.github.portlek.nbt.base.SpawnerNBTOf;
import io.github.portlek.spawner.files.Config;
import io.github.portlek.spawner.spawners.natural.drop.Drop;
import java.util.List;
import java.util.UUID;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.cactoos.list.Mapped;
import org.jetbrains.annotations.NotNull;

public final class SpawnerTypeBasic implements SpawnerType {
   @NotNull
   private final Config config;
   @NotNull
   private final String id;
   @NotNull
   private final String name;
   @NotNull
   private final ItemStack spawnerItem;
   @NotNull
   private final Drop drop;
   private final int speed;
   private final int range;
   private final int requiredRange;
   private final int count;
   private final int maxSpawn;

   public SpawnerTypeBasic(@NotNull Config config, @NotNull String id, @NotNull String name, @NotNull ItemStack spawnerItem, @NotNull Drop drop, int speed, int range, int requiredRange, int count, int maxSpawn) {
      this.config = config;
      this.id = id;
      this.name = name;
      this.spawnerItem = spawnerItem;
      this.drop = drop;
      this.speed = speed;
      this.range = range;
      this.requiredRange = requiredRange;
      this.count = count;
      this.maxSpawn = maxSpawn;
   }

   @NotNull
   public CreatureSpawner create(@NotNull CreatureSpawner creatureSpawner, int size, @NotNull UUID owner) {
      if (size < 1) {
         size = 1;
      }

      SpawnerNBTOf spawnerNBTOf = new SpawnerNBTOf(creatureSpawner);
      NBTCompound nbtCompound = spawnerNBTOf.nbt();
      NBTCompound spawnDataCompound = nbtCompound.getNBTCompound("SpawnData");
      spawnDataCompound.setInt("zpawner-size", size);
      spawnDataCompound.setString("zpawner-spawner-id", this.id);
      spawnDataCompound.setString("zpawner-owner", owner.toString());
      nbtCompound.set("SpawnData", spawnDataCompound);
      nbtCompound.setShort("Delay", (short)(this.speed * 20 / size));
      nbtCompound.setShort("MinSpawnDelay", (short)(this.speed * 20 / size));
      nbtCompound.setShort("MaxSpawnDelay", (short)(this.speed * 20 / size));
      nbtCompound.setShort("SpawnCount", (short)this.count);
      nbtCompound.setShort("MaxNearbyEntities", (short)this.maxSpawn);
      nbtCompound.setShort("RequiredPlayerRange", (short)this.requiredRange);
      nbtCompound.setShort("SpawnRange", (short)this.range);
      return spawnerNBTOf.apply(this.drop.apply(nbtCompound));
   }

   @NotNull
   public ItemStack getSpawnerItem() {
      ItemStackNBTOf itemStackNBTOf = new ItemStackNBTOf(this.spawnerItem);
      NBTCompound nbtCompound = itemStackNBTOf.nbt();
      NBTCompound tagCompound = nbtCompound.getNBTCompound("tag");
      tagCompound.setString("zpawner-spawner-id", this.id);
      nbtCompound.set("tag", tagCompound);
      return itemStackNBTOf.apply(nbtCompound);
   }

   @NotNull
   public List<String> hologram(int size) {
      return size > 1 ? (List)(new ColoredList(new Mapped<>((hologram) -> {
         return hologram.replaceAll("%count%", String.valueOf(size));
      }, this.config.combinedHologram))).value() : (List)(new ColoredList(this.config.hologram)).value();
   }

   @NotNull
   public String replaceAll(@NotNull String replaced) {
      return (String)(new Colored(this.drop.replaceAll(replaced.replaceAll("%spawner-name%", this.name).replaceAll("%spawner-id%", this.id)))).value();
   }

   @NotNull
   public List<String> replaceAll(@NotNull List<String> replaced) {
      return new Mapped<>(this::replaceAll, replaced);
   }
}
