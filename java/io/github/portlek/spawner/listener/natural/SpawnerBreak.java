package io.github.portlek.spawner.listener.natural;

import io.github.portlek.itemstack.item.get.DurabilityOf;
import io.github.portlek.itemstack.item.set.SetDurabilityOf;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.base.SpawnerNBTOf;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.files.Config;
import io.github.portlek.spawner.spawners.natural.placed.SpawnerPlaced;
import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import io.github.portlek.spawner.util.HandItem;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.cactoos.scalar.Or;
import org.jetbrains.annotations.NotNull;

public final class SpawnerBreak implements Listener {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final Config config;

   public SpawnerBreak(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
      this.config = spawnerAPI.getConfigs();
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void blockBreak(BlockBreakEvent event) {
      BlockState blockState = event.getBlock().getState();
      if (blockState instanceof CreatureSpawner && !event.isCancelled()) {
         SpawnerPlaced placed = this.spawnerAPI.findByLocation(blockState.getLocation());
         if (placed != null) {
            SpawnerType spawner = placed.spawnerType;
            SpawnerNBTOf spawnerNBTOf = new SpawnerNBTOf((CreatureSpawner)blockState);
            NBTCompound nbtCompound = spawnerNBTOf.nbt();
            NBTCompound spawnDataCompound = nbtCompound.getNBTCompound("SpawnData");
            int amount = spawnDataCompound.getInt("zpawner-size") - 1;
            if (!this.config.dropExp) {
               event.setExpToDrop(0);
            }

            if (this.config.spawnerBreakerActive && !event.getPlayer().hasPermission("zpawner.bypass")) {
               Player player = event.getPlayer();
               ItemStack handItem = (new HandItem(player)).value();

               boolean or;
               try {
                  or = (new Or((breaker) -> {
                     return breaker.is((ItemStack)(new SetDurabilityOf(handItem.clone(), new DurabilityOf(breaker.getItemStack()))).value());
                  }, this.config.spawnerBreakers.getBreakers())).value();
               } catch (Exception var13) {
                  return;
               }

               if (!or) {
                  event.setCancelled(true);
               } else {
                  if (amount > 0) {
                     event.setCancelled(true);
                     placed.setSize(amount);
                     placed.update();
                  } else {
                     this.spawnerAPI.remove((CreatureSpawner)blockState);
                  }

                  event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), spawner.getSpawnerItem());
               }
            } else {
               if (amount > 0) {
                  event.setCancelled(true);
                  placed.setSize(amount);
                  placed.update();
               } else {
                  this.spawnerAPI.remove((CreatureSpawner)blockState);
               }

               event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), spawner.getSpawnerItem());
            }
         }
      }
   }
}
