package io.github.portlek.observer;

import org.jetbrains.annotations.NotNull;

public interface Target<T> {
   void update(@NotNull T var1);
}
