package io.github.portlek.spawner.files;

import io.github.portlek.spawner.spawners.natural.breaker.SpawnerBreakers;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class Config {
   @NotNull
   public final String language;
   public final boolean dropExp;
   public final boolean combinedEnabled;
   public final int combinedRadius;
   @NotNull
   public final String activeStatu;
   @NotNull
   public final String deactiveStatu;
   @NotNull
   public final List<String> combinedHologram;
   @NotNull
   public final List<String> hologram;
   public final int hologramHeight;
   public final boolean spawnerBreakerActive;
   @NotNull
   public final SpawnerBreakers spawnerBreakers;
   @NotNull
   public final List<String> sign;
   @NotNull
   public final String ganimetAnvilAlis;
   @NotNull
   public final String ganimetAnvilSatis;
   @NotNull
   public final String yetkiliAnvil;
   @NotNull
   public final ItemStack chestItem;
   @NotNull
   public final List<String> book;
   @NotNull
   public final String yes;
   @NotNull
   public final String no;

   public Config(@NotNull String language, boolean dropExp, boolean combinedEnabled, int combinedRadius, @NotNull String activeStatu, @NotNull String deactiveStatu, @NotNull List<String> combinedHologram, @NotNull List<String> hologram, int hologramHeight, boolean spawnerBreakerActive, @NotNull SpawnerBreakers spawnerBreakers, @NotNull List<String> sign, @NotNull String ganimetAnvilAlis, @NotNull String ganimetAnvilSatis, @NotNull String yetkiliAnvil, @NotNull ItemStack chestItem, @NotNull List<String> book, @NotNull String yes, @NotNull String no) {
      this.language = language;
      this.dropExp = dropExp;
      this.combinedEnabled = combinedEnabled;
      this.combinedRadius = combinedRadius;
      this.activeStatu = activeStatu;
      this.deactiveStatu = deactiveStatu;
      this.combinedHologram = combinedHologram;
      this.hologram = hologram;
      this.hologramHeight = hologramHeight;
      this.spawnerBreakerActive = spawnerBreakerActive;
      this.spawnerBreakers = spawnerBreakers;
      this.sign = sign;
      this.ganimetAnvilAlis = ganimetAnvilAlis;
      this.ganimetAnvilSatis = ganimetAnvilSatis;
      this.yetkiliAnvil = yetkiliAnvil;
      this.chestItem = chestItem;
      this.book = book;
      this.yes = yes;
      this.no = no;
   }
}
