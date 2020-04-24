package io.github.portlek.mcyaml.mck;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class MckFileConfiguration extends FileConfiguration {
   @NotNull
   public String saveToString() {
      return "";
   }

   public void loadFromString(@NotNull String s) {
   }

   @NotNull
   protected String buildHeader() {
      return "";
   }
}
