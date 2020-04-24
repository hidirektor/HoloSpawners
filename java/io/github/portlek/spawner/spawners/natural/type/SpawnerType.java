package io.github.portlek.spawner.spawners.natural.type;

import java.util.List;
import java.util.UUID;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface SpawnerType {
   @NotNull
   CreatureSpawner create(@NotNull CreatureSpawner var1, int var2, @NotNull UUID var3);

   @NotNull
   ItemStack getSpawnerItem();

   @NotNull
   List<String> hologram(int var1);

   @NotNull
   String replaceAll(@NotNull String var1);

   @NotNull
   List<String> replaceAll(@NotNull List<String> var1);
}
