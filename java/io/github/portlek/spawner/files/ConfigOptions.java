package io.github.portlek.spawner.files;

import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.itemstack.util.ColoredList;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.mcyaml.mck.MckFileConfiguration;
import io.github.portlek.spawner.spawners.natural.breaker.SpawnerBreaker;
import io.github.portlek.spawner.spawners.natural.breaker.SpawnerBreakers;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ConfigOptions {
   @NotNull
   private final IYaml yaml;
   @Nullable
   private Config config;

   public ConfigOptions(@NotNull IYaml yaml) {
      this.yaml = yaml;
   }

   @NotNull
   public Config create() {
      if (this.config != null) {
         return this.config;
      } else {
         this.yaml.create();
         String language = (String)this.yaml.getString("language").orElse("tr");
         boolean dropExp = this.yaml.getBoolean("natural-spawner.drop-exp");
         boolean combinedEnabled = this.yaml.getBoolean("natural-spawner.combined-enabled");
         int combinedRadius = this.yaml.getInt("natural-spawner.combined-radius");
         String activeStatu = (String)(new Colored((String)this.yaml.getString("natural-spawner.active-statu").orElse("&bAktif"))).value();
         String deactiveStatu = (String)(new Colored((String)this.yaml.getString("natural-spawner.deactive-statu").orElse("&cKapalı"))).value();
         List<String> combinedHologram = (List)(new ColoredList(this.yaml.getStringList("natural-spawner.combined-hologram"))).value();
         List<String> hologram = (List)(new ColoredList(this.yaml.getStringList("natural-spawner.hologram"))).value();
         int hologramHeight = this.yaml.getInt("natural-spawner.hologram-height");
         boolean spawnerBreakerActive = this.yaml.getBoolean("natural-spawner.spawner-breaker-active");
         ConfigurationSection section = this.yaml.getSection("natural-spawner.spawner-breakers");
         if (section instanceof MckFileConfiguration) {
            section = this.yaml.createSection("natural-spawner.spawner-breakers");
         }

         SpawnerBreakers spawnerBreakers = new SpawnerBreakers(new ListOf(new Mapped<>((key) -> {
            return new SpawnerBreaker((String)this.yaml.getString(key).orElse(""), this.yaml.getCustomItemStack("natural-spawner.spawner-breakers." + key));
         }, section.getKeys(false))));
         List<String> sign = this.yaml.getStringList("chest-spawner.sign");
         String ganimetAnvilAlis = this.c((String)this.yaml.getString("chest-spawner.ganimet-anvil-alis").orElse(""));
         String ganimetAnvilSatis = this.c((String)this.yaml.getString("chest-spawner.ganimet-anvil-satis").orElse(""));
         String yetkiliAnvil = this.c((String)this.yaml.getString("chest-spawner.yetkili-anvil").orElse(""));
         ItemStack chestItem = this.yaml.getCustomItemStack("chest-spawner.chest-item");
         List<String> book = (List)(new ColoredList(this.yaml.getStringList("chest-spawner.book"))).value();
         String yes = this.c((String)this.yaml.getString("chest-spawner.clickyes").orElse("chest-spawner.clickyes"));
         String no = this.c((String)this.yaml.getString("chest-spawner.clickno").orElse("chest-spawner.clickno"));
         this.config = new Config(language, dropExp, combinedEnabled, combinedRadius, activeStatu, deactiveStatu, combinedHologram, hologram, hologramHeight, spawnerBreakerActive, spawnerBreakers, sign, ganimetAnvilAlis, ganimetAnvilSatis, yetkiliAnvil, chestItem, book, yes, no);
         return this.config;
      }
   }

   @NotNull
   public Config getConfig() {
      if (this.config == null) {
         this.create();
      }

      return this.config;
   }

   @NotNull
   private String c(@NotNull String text) {
      return (String)(new Colored(text)).value();
   }
}
