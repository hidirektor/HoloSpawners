package io.github.portlek.spawner.listener.natural;

import io.github.portlek.itemstack.util.XMaterial;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.menu.natural.HologramMenu;
import io.github.portlek.spawner.spawners.natural.placed.SpawnerPlaced;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class SpawnerClick implements Listener {
   @NotNull
   private final SpawnerAPI spawnerAPI;

   public SpawnerClick(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
   }

   @EventHandler
   public void click(PlayerInteractEvent event) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == XMaterial.SPAWNER.parseMaterial() && event.getItem() == null) {
         BlockState blockState = event.getClickedBlock().getState();
         SpawnerPlaced placed = this.spawnerAPI.findByLocation(blockState.getLocation());
         if (blockState instanceof CreatureSpawner && event.useInteractedBlock() != Result.DENY && placed != null) {
            (new HologramMenu(this.spawnerAPI, event.getPlayer(), placed)).show();
         }
      }
   }
}
