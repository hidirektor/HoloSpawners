package io.github.portlek.observer.source;

import io.github.portlek.observer.Source;
import io.github.portlek.observer.Target;
import java.util.Iterator;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class BasicSource<T> implements Source<T> {
   @NotNull
   private final Vector<Target<T>> targets = new Vector();

   public void subscribe(@NotNull Target<T> target) {
      if (!this.targets.contains(target)) {
         this.targets.add(target);
      }

   }

   public void unsubscribe(@NotNull Target<T> target) {
      this.targets.remove(target);
   }

   public void notifyTargets(@NotNull T argument) {
      Iterator var2 = this.targets.iterator();

      while(var2.hasNext()) {
         Target<T> target = (Target)var2.next();
         target.update(argument);
      }

   }
}
