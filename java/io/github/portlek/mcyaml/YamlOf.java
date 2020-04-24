package io.github.portlek.mcyaml;

import java.io.File;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class YamlOf extends YamlEnvelope {
   public YamlOf(@NotNull Plugin plugin, @NotNull File file, @NotNull String resourcePath) {
      super(plugin, file, resourcePath.endsWith(".yml") ? resourcePath : resourcePath + ".yml");
   }

   public YamlOf(@NotNull Plugin plugin, @NotNull String resourcePath, @NotNull String fileName) {
      this(plugin, new File(plugin.getDataFolder().getAbsolutePath() + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath), fileName.endsWith(".yml") ? fileName : fileName + ".yml"), resourcePath.isEmpty() ? fileName : (resourcePath.endsWith("/") ? resourcePath + fileName : resourcePath + "/" + fileName));
   }

   public YamlOf(@NotNull Plugin plugin, @NotNull String fileName) {
      this(plugin, "", fileName);
   }
}
