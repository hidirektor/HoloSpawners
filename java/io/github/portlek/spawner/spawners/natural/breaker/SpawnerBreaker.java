package io.github.portlek.spawner.spawners.natural.breaker;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public final class SpawnerBreaker {
   @NotNull
   private final String id;
   @NotNull
   private final ItemStack itemStack;

   public SpawnerBreaker(@NotNull String id, @NotNull ItemStack itemStack) {
      this.id = id;
      this.itemStack = itemStack;
   }

   public boolean is(@NotNull ItemStack itemStack) {
      return this.itemStack.isSimilar(itemStack);
   }

   public boolean is(@NotNull String id) {
      return this.id.equals(id);
   }

   public boolean startsWithIgnoreCase(@NotNull String lastWord) {
      return StringUtil.startsWithIgnoreCase(this.id, lastWord);
   }

   @NotNull
   public String getId() {
      return this.id;
   }

   @NotNull
   public ItemStack getItemStack() {
      return this.itemStack;
   }
}
