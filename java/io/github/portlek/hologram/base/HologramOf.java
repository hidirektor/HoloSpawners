package io.github.portlek.hologram.base;

import java.util.List;
import org.bukkit.Location;
import org.cactoos.list.ListOf;
import org.jetbrains.annotations.NotNull;

public final class HologramOf extends HologramEnvelope {
   public HologramOf(@NotNull Location location, @NotNull List<String> lines) {
      super(location, lines);
   }

   public HologramOf(@NotNull Location location, @NotNull String... lines) {
      this(location, (List)(new ListOf(lines)));
   }
}
