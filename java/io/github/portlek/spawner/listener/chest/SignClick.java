package io.github.portlek.spawner.listener.chest;

import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.menu.chest.ChestSettingsMenu;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import io.github.portlek.spawner.spawners.chest.user.User;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public final class SignClick implements Listener {
   @NotNull
   private final SpawnerAPI spawnerAPI;

   public SignClick(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
   }

   @EventHandler
   public void signClick(PlayerInteractEvent event) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
         Block block = event.getClickedBlock();
         BlockState blockState = block.getState();
         if (blockState instanceof Sign) {
            Sign sign = (Sign)blockState;
            ChestPlaced placed = this.spawnerAPI.findChestBySignLocation(sign.getLocation());
            if (placed != null) {
               User user = this.spawnerAPI.findUserByUUID(placed.owner);
               event.setCancelled(true);
               if (placed.owner.equals(event.getPlayer().getUniqueId()) || user.yetkililer.containsKey(event.getPlayer().getUniqueId())) {
                  (new ChestSettingsMenu(this.spawnerAPI, event.getPlayer(), placed)).show();
               }
            }
         }
      }
   }
}
