package io.github.portlek.title.base;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TitlePlayerOf extends TitlePlayerEnvelope {
   public TitlePlayerOf(@NotNull Player player) {
      super(player);
   }
}
