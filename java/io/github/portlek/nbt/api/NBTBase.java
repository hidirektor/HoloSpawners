package io.github.portlek.nbt.api;

import org.jetbrains.annotations.NotNull;

public interface NBTBase<T> {
   @NotNull
   T value();

   @NotNull
   String toString();
}
