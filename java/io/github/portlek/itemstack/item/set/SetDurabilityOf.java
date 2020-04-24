package io.github.portlek.itemstack.item.set;

import io.github.portlek.itemstack.ScalarRuntime;
import org.bukkit.inventory.ItemStack;
import org.cactoos.Scalar;
import org.jetbrains.annotations.NotNull;

public final class SetDurabilityOf extends ScalarRuntime<ItemStack> {
   public SetDurabilityOf(@NotNull Scalar<ItemStack> itemStackScalar, @NotNull Scalar<Short> durabilityScalar) {
      super(() -> {
         ItemStack itemStack = (ItemStack)itemStackScalar.value();
         itemStack.setDurability((Short)durabilityScalar.value());
         return itemStack;
      });
   }

   public SetDurabilityOf(@NotNull ItemStack itemStack, @NotNull Scalar<Short> durability) {
      this(() -> {
         return itemStack;
      }, durability);
   }

   public SetDurabilityOf(@NotNull Scalar<ItemStack> itemStack, @NotNull Short durability) {
      this(itemStack, () -> {
         return durability;
      });
   }

   public SetDurabilityOf(@NotNull ItemStack itemStack, @NotNull Short durability) {
      this(() -> {
         return itemStack;
      }, () -> {
         return durability;
      });
   }
}
