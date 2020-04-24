package io.github.portlek.itemstack.item.set;

import io.github.portlek.itemstack.ScalarRuntime;
import io.github.portlek.itemstack.item.get.TypeOf;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.cactoos.Scalar;
import org.jetbrains.annotations.NotNull;

public final class SetDataOf extends ScalarRuntime<ItemStack> {
   public SetDataOf(@NotNull Scalar<ItemStack> itemStackScalar, @NotNull Scalar<Material> materialScalar, @NotNull Scalar<Byte> dataScalar) {
      super(() -> {
         ItemStack itemStack = (ItemStack)itemStackScalar.value();
         itemStack.setData(new MaterialData((Material)materialScalar.value(), (Byte)dataScalar.value()));
         return itemStack;
      });
   }

   public SetDataOf(@NotNull Scalar<ItemStack> itemStack, @NotNull Material material, byte data) {
      this(itemStack, () -> {
         return material;
      }, () -> {
         return data;
      });
   }

   public SetDataOf(@NotNull ItemStack itemStack, @NotNull Material material, byte data) {
      this(() -> {
         return itemStack;
      }, material, data);
   }

   public SetDataOf(@NotNull Scalar<ItemStack> itemStack, byte data) {
      this(itemStack, new TypeOf(itemStack), () -> {
         return data;
      });
   }

   public SetDataOf(@NotNull ItemStack itemStack, byte data) {
      this(() -> {
         return itemStack;
      }, data);
   }
}
