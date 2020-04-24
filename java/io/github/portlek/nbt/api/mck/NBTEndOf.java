package io.github.portlek.nbt.api.mck;

import io.github.portlek.nbt.api.NBTBase;
import org.jetbrains.annotations.NotNull;

public class NBTEndOf implements NBTBase<NBTBase> {
   @NotNull
   public NBTBase value() {
      return this;
   }

   @NotNull
   public String toString() {
      return "";
   }
}
