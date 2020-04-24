package io.github.portlek.spawner.listener.chest;

import io.github.portlek.itemstack.util.XMaterial;
import io.github.portlek.nbt.base.ItemStackNBTOf;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.mock.MckChestType;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import io.github.portlek.spawner.spawners.chest.type.ChestType;
import io.github.portlek.spawner.util.Util;
import io.github.portlek.versionmatched.Version;
import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Directional;
import org.cactoos.scalar.MaxOf;
import org.jetbrains.annotations.NotNull;

public final class ChestPlace implements Listener {
   @NotNull
   private final SpawnerAPI spawnerAPI;

   public ChestPlace(@NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
   }

   @EventHandler
   public void chestPlace(BlockPlaceEvent event) {
      ChestType chestType = this.spawnerAPI.findById((new ItemStackNBTOf(event.getItemInHand())).nbt().getNBTCompound("tag").getString("chest-spawner-id"));
      if (!(chestType instanceof MckChestType)) {
         Block block = event.getBlockPlaced();
         block.setType(XMaterial.CHEST.parseMaterial());
         Chest chest = (Chest)block.getState();
         Block signBlock;
         if ((new Version()).minor() < 13) {
            signBlock = Util.putSignOn(block, ((Directional)chest.getData()).getFacing(), this.spawnerAPI.getConfigs().sign);
         } else {
            signBlock = Util.putSignOn(block, ((org.bukkit.block.data.Directional)chest.getBlock().getBlockData()).getFacing(), this.spawnerAPI.getConfigs().sign);
         }

         if (signBlock != null) {
            Sign sign = (Sign)signBlock.getState();
            int level = (new ItemStackNBTOf(event.getItemInHand())).nbt().getNBTCompound("tag").getInt("chest-spawner-level");
            this.spawnerAPI.add(new ChestPlaced(this.spawnerAPI, UUID.randomUUID(), event.getPlayer().getUniqueId(), chest, sign, chestType, 0, 0, (new MaxOf(new Integer[]{1, level})).intValue()));
            event.getPlayer().sendMessage(this.spawnerAPI.getLanguage().generalYouPlaceAChestSpawner(chestType.getName()));
         }
      }
   }

   private boolean check(@NotNull Block block) {
      for(int x = 1; x >= -1; --x) {
         for(int y = 1; y >= -1; --y) {
            for(int z = 1; z >= -1; --z) {
               if (x != 0 || y != 0 || z != 0) {
                  Block nearby = block.getRelative(x, y, z);
                  if (nearby.getState() instanceof Chest) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }
}
