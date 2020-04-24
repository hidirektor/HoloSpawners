package io.github.portlek.nbt.api.mck;

import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.api.NBTList;
import io.github.portlek.nbt.api.NBTRegistry;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MckNBTRegistry implements NBTRegistry {
   @NotNull
   public ItemStack toItemStack(@NotNull NBTCompound nbtCompound) {
      return new ItemStack(Material.AIR);
   }

   @NotNull
   public NBTCompound toCompound(@NotNull ItemStack itemStack) {
      return new MckNBTCompound();
   }

   @NotNull
   public NBTCompound toCompound(@NotNull String nbtString) {
      return new MckNBTCompound();
   }

   @NotNull
   public NBTList toTagStringList(@NotNull List<String> nbtString) {
      return new MckNBTList();
   }

   @NotNull
   public NBTList toTagStringList(@NotNull String nbtString) {
      return new MckNBTList();
   }

   @NotNull
   public NBTCompound toCompound(@NotNull CreatureSpawner creatureSpawner) {
      return new MckNBTCompound();
   }

   @NotNull
   public CreatureSpawner toSpawner(@NotNull CreatureSpawner spawner, @NotNull NBTCompound applied) {
      return spawner;
   }

   public NBTCompound toCompound(@NotNull Entity entity) {
      return new MckNBTCompound();
   }

   public Entity toEntity(@NotNull Entity original, @NotNull NBTCompound applied) {
      return original;
   }
}
