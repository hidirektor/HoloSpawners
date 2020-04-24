package io.github.portlek.reflection.mck;

import io.github.portlek.reflection.RefField;
import io.github.portlek.reflection.RefFieldExecuted;
import org.jetbrains.annotations.NotNull;

public class MckField implements RefField {
   public void set(@NotNull Object value) {
   }

   @NotNull
   public Object get(@NotNull Object fallback) {
      return fallback;
   }

   @NotNull
   public RefFieldExecuted of(@NotNull Object object) {
      return new RefFieldExecuted() {
         public void set(@NotNull Object value) {
         }

         @NotNull
         public Object get(@NotNull Object fallback) {
            return fallback;
         }
      };
   }
}
