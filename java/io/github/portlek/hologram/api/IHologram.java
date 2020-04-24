package io.github.portlek.hologram.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface IHologram {
   @NotNull
   Object spawnHologram(@NotNull String var1, @NotNull Location var2);

   void removeHologram(@NotNull World var1, @NotNull Object var2);

   @NotNull
   Object[] createPacket(@NotNull Location var1, @NotNull String var2);

   @NotNull
   Object removePacket(int var1);

   void configureHologram(@NotNull Object var1, @NotNull String var2, @NotNull Location var3);

   void sendPacket(@NotNull Player var1, @NotNull Object var2);
}
