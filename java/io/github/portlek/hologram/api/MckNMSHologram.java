package io.github.portlek.hologram.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MckNMSHologram implements IHologram {
   @NotNull
   public Object spawnHologram(@NotNull String paramString, @NotNull Location paramLocation) {
      return "";
   }

   public void removeHologram(@NotNull World paramWorld, @NotNull Object paramObject) {
   }

   @NotNull
   public Object[] createPacket(@NotNull Location paramLocation, @NotNull String paramString) {
      return new Object[0];
   }

   @NotNull
   public Object removePacket(int paramInt) {
      return "";
   }

   public void configureHologram(@NotNull Object paramObject, @NotNull String paramString, @NotNull Location paramLocation) {
   }

   public void sendPacket(@NotNull Player player, @NotNull Object object) {
   }
}
