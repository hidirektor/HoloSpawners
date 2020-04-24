package io.github.portlek.spawner.mock;

import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import java.util.List;
import java.util.UUID;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class MckSpawnerType implements SpawnerType {
   @NotNull
   public CreatureSpawner create(@NotNull CreatureSpawner creatureSpawner, int size, @NotNull UUID owner) {
      throw new UnsupportedOperationException();
   }

   @NotNull
   public ItemStack getSpawnerItem() {
      throw new UnsupportedOperationException();
   }

   @NotNull
   public List<String> hologram(int size) {
      throw new UnsupportedOperationException();
   }

   @NotNull
   public String replaceAll(@NotNull String replaced) {
      throw new UnsupportedOperationException();
   }

   @NotNull
   public List<String> replaceAll(@NotNull List<String> replaced) {
      throw new UnsupportedOperationException();
   }
}
