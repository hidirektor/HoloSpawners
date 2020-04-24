package io.github.portlek.reflection;

import org.jetbrains.annotations.NotNull;

public interface RefMethodExecuted {
   @NotNull
   Object call(@NotNull Object var1, @NotNull Object... var2);
}
