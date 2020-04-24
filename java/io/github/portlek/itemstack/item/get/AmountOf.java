package io.github.portlek.itemstack.item.get;

import io.github.portlek.itemstack.ScalarRuntime;
import org.bukkit.inventory.ItemStack;
import org.cactoos.Scalar;
import org.jetbrains.annotations.NotNull;

public final class AmountOf extends ScalarRuntime<Integer> {
   public AmountOf(@NotNull Scalar<ItemStack> itemStackScalar) {
      super(() -> {
         return ((ItemStack)itemStackScalar.value()).getAmount();
      });
   }

   public AmountOf(@NotNull ItemStack itemStack) {
      this(() -> {
         return itemStack;
      });
   }
}
