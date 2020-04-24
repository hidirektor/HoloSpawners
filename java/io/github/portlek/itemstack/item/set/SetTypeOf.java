package io.github.portlek.itemstack.item.set;

import io.github.portlek.itemstack.ScalarRuntime;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.cactoos.Scalar;
import org.jetbrains.annotations.NotNull;

public final class SetTypeOf extends ScalarRuntime<ItemStack> {
   public SetTypeOf(@NotNull Scalar<ItemStack> itemStackScalar, @NotNull Scalar<Material> materialScalar) {
      super(() -> {
         ItemStack itemStack = (ItemStack)itemStackScalar.value();
         itemStack.setType((Material)materialScalar.value());
         return itemStack;
      });
   }

   public SetTypeOf(@NotNull ItemStack itemStack, @NotNull Material material) {
      this(() -> {
         return itemStack;
      }, () -> {
         return material;
      });
   }

   public SetTypeOf(@NotNull Material material) {
      this(new ItemStack(Material.AIR), material);
   }
}
