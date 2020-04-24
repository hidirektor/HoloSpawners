package io.github.portlek.hologram.api;

import io.github.portlek.mcyaml.IYaml;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Hologram {
   void displayTo(@NotNull Player... var1);

   void removeFrom(@NotNull Player... var1);

   void spawn();

   void remove();

   void save(@NotNull IYaml var1, @NotNull UUID var2);

   void removeLines();

   void addLine(@NotNull String... var1);

   void addLines(@NotNull List<String> var1);

   void setLines(@NotNull List<String> var1);
}
