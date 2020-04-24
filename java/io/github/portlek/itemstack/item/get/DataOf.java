package io.github.portlek.itemstack.item.get;

import io.github.portlek.itemstack.ScalarRuntime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.cactoos.Scalar;
import org.jetbrains.annotations.NotNull;

public final class DataOf extends ScalarRuntime<MaterialData> {
   public DataOf(@NotNull Scalar<ItemStack> itemStackScalar) {
      super(() -> {
         return ((ItemStack)itemStackScalar.value()).getData();
      });
   }

   public DataOf(@NotNull ItemStack itemStack) {
      this(() -> {
         return itemStack;
      });
   }
}
