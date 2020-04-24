package io.github.portlek.spawner.spawners.natural.breaker;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.cactoos.scalar.FirstOf;
import org.jetbrains.annotations.NotNull;

public final class SpawnerBreakers {
   @NotNull
   private final List<SpawnerBreaker> breakers;

   public SpawnerBreakers(@NotNull List<SpawnerBreaker> breakers) {
      this.breakers = breakers;
   }

   @NotNull
   public SpawnerBreaker getBreaker(@NotNull String id) {
      try {
         return (SpawnerBreaker)(new FirstOf<>((spawnerBreaker) -> {
            return spawnerBreaker.is(id);
         }, this.breakers, () -> {
            return new SpawnerBreaker("", new ItemStack(Material.AIR));
         })).value();
      } catch (Exception var3) {
         return new SpawnerBreaker("", new ItemStack(Material.AIR));
      }
   }

   @NotNull
   public List<SpawnerBreaker> getBreakers() {
      return this.breakers;
   }
}
