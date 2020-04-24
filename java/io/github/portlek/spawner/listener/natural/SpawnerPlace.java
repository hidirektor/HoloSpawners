package io.github.portlek.spawner.listener.natural;

import io.github.portlek.hologram.base.Holograms;
import io.github.portlek.itemstack.util.XMaterial;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.base.ItemStackNBTOf;
import io.github.portlek.nbt.base.SpawnerNBTOf;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.files.Config;
import io.github.portlek.spawner.files.Language;
import io.github.portlek.spawner.mock.MckSpawnerType;
import io.github.portlek.spawner.spawners.natural.placed.SpawnerPlaced;
import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import io.github.portlek.spawner.util.NearbySpawner;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.cactoos.collection.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.MaxOf;
import org.jetbrains.annotations.NotNull;

public final class SpawnerPlace implements Listener {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final Config config;
   @NotNull
   private final Language language;
   @NotNull
   private final Holograms holograms;

   public SpawnerPlace(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
      this.config = spawnerAPI.getConfigs();
      this.language = spawnerAPI.getLanguage();
      this.holograms = spawnerAPI.holograms;
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void blockPlace(BlockPlaceEvent event) {
      Block block = event.getBlockPlaced();
      ItemStack itemStack = event.getItemInHand();
      if (!event.isCancelled()) {
         NBTCompound tagCompound = (new ItemStackNBTOf(itemStack)).nbt().getNBTCompound("tag");
         if (tagCompound.has("zpawner-spawner-id")) {
            String spawnerId = tagCompound.getString("zpawner-spawner-id");
            SpawnerType spawnerType = this.spawnerAPI.findSpawnerTypeById(spawnerId);
            if (!(spawnerType instanceof MckSpawnerType)) {
               if (this.config.combinedRadius > 0 && this.config.combinedEnabled) {
                  List<CreatureSpawner> nearbySpawners = (new NearbySpawner(block, this.config.combinedRadius)).value();
                  if (!nearbySpawners.isEmpty()) {
                     List<CreatureSpawner> nearbyCreatureSpawnerList = new ListOf(new Filtered(Objects::nonNull, new Mapped<>((creatureSpawnerx) -> {
                        return !(new SpawnerNBTOf(creatureSpawnerx)).nbt().getNBTCompound("SpawnData").getString("zpawner-spawner-id").equals(spawnerId) ? null : creatureSpawnerx;
                     }, nearbySpawners)));
                     if (!nearbyCreatureSpawnerList.isEmpty()) {
                        CreatureSpawner creatureSpawner = (CreatureSpawner)nearbyCreatureSpawnerList.get(0);
                        SpawnerPlaced placed = this.spawnerAPI.findByLocation(creatureSpawner.getLocation());
                        if (placed == null) {
                           return;
                        }

                        SpawnerNBTOf spawnerNBTOf = new SpawnerNBTOf(creatureSpawner);
                        NBTCompound nbtCompound = spawnerNBTOf.nbt();
                        NBTCompound spawnDataCompound = nbtCompound.getNBTCompound("SpawnData");
                        int amount = (new MaxOf(new Integer[]{spawnDataCompound.getInt("zpawner-size"), 1})).intValue();
                        spawnDataCompound.setInt("zpawner-size", amount + 1);
                        nbtCompound.set("SpawnData", spawnDataCompound);
                        spawnerNBTOf.apply(nbtCompound);
                        event.setCancelled(true);
                        placed.setSize(amount + 1);
                        placed.update();
                        event.getPlayer().sendMessage(placed.spawnerType.replaceAll(this.language.generalCombined(creatureSpawner.getX(), creatureSpawner.getY(), creatureSpawner.getZ())));
                        return;
                     }
                  }
               }

               block.setType(XMaterial.SPAWNER.parseMaterial());
               Bukkit.getScheduler().runTask(this.spawnerAPI.plugin, () -> {
                  spawnerType.create((CreatureSpawner)block.getState(), 1, event.getPlayer().getUniqueId());
               });
               this.spawnerAPI.add(new SpawnerPlaced(this.spawnerAPI, UUID.randomUUID(), event.getPlayer().getUniqueId(), (CreatureSpawner)block.getState(), spawnerType, this.holograms));
            }
         }
      }
   }
}
