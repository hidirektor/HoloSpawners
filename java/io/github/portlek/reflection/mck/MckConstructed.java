package io.github.portlek.reflection.mck;

import io.github.portlek.reflection.RefConstructed;
import org.jetbrains.annotations.NotNull;

public class MckConstructed implements RefConstructed {
   @NotNull
   public Object create(@NotNull Object fallback, @NotNull Object... parameters) {
      return fallback;
   }
}
