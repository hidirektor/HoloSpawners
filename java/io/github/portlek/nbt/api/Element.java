package io.github.portlek.nbt.api;

import org.jetbrains.annotations.NotNull;

public interface Element<V, T> {
   @NotNull
   T nbt();

   @NotNull
   V apply(@NotNull T var1);

   @NotNull
   V element();
}
