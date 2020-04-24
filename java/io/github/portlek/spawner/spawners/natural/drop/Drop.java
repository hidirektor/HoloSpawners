package io.github.portlek.spawner.spawners.natural.drop;

import io.github.portlek.itemstack.item.meta.get.DisplayOf;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.base.ItemStackNBTOf;
import io.github.portlek.versionmatched.Version;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class Drop {
   private final Version version = new Version();
   @NotNull
   private final String id;
   @NotNull
   private final EntityType entityType;
   @NotNull
   private final ItemStack itemStack;

   public Drop(@NotNull String id, @NotNull EntityType entityType, @NotNull ItemStack itemStack) {
      this.id = id;
      this.entityType = entityType;
      this.itemStack = itemStack;
   }

   @NotNull
   public NBTCompound apply(@NotNull NBTCompound nbtCompound) {
      NBTCompound spawnData = nbtCompound.getNBTCompound("SpawnData");
      if (this.version.minor() > 13) {
         spawnData.setString("id", this.entityType.getKey().getNamespace() + ":" + this.entityType.getKey().getKey());
      } else if (this.version.minor() > 12) {
         spawnData.setString("id", "minecraft:" + this.entityType.getName());
      } else if (this.version.minor() > 11) {
         spawnData.setString("id", "minecraft:" + this.entityType.getName());
      } else if (this.version.minor() > 8) {
         spawnData.setString("id", this.entityType.getName() == null ? "" : this.entityType.getName());
      } else {
         nbtCompound.setString("EntityId", this.entityType.getName() == null ? "Pig" : this.entityType.getName());
      }

      if (this.entityType == EntityType.DROPPED_ITEM && this.itemStack.getType() != Material.AIR) {
         spawnData.set("Item", (new ItemStackNBTOf(this.itemStack)).nbt());
      }

      nbtCompound.set("SpawnData", spawnData);
      return nbtCompound;
   }

   @NotNull
   public String replaceAll(@NotNull String replaced) {
      if (this.entityType != EntityType.DROPPED_ITEM) {
         return replaced.replaceAll("%spawner-drop-type%", this.entityType.name());
      } else {
         return this.itemStack.getType() != Material.AIR ? replaced.replaceAll("%spawner-drop-type%", (String)((Optional)(new DisplayOf(this.itemStack)).value()).orElse(this.itemStack.getType().name())) : replaced;
      }
   }
}
