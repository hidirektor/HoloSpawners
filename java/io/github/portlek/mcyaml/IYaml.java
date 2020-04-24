package io.github.portlek.mcyaml;

import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IYaml {
   void create();

   void reload();

   void save();

   @NotNull
   Object get(@NotNull String var1, @NotNull Object var2);

   @NotNull
   <T> T getOrSet(@NotNull String var1, @NotNull T var2);

   @NotNull
   Location getLocation(@NotNull String var1);

   void setLocation(@NotNull String var1, @NotNull Location var2);

   @NotNull
   ItemStack getCustomItemStack(@NotNull String var1);

   void setCustomItemStack(@NotNull String var1, @NotNull ItemStack var2);

   @NotNull
   Optional<String> getString(@NotNull String var1);

   void set(@NotNull String var1, @NotNull Object var2);

   @NotNull
   List<String> getStringList(@NotNull String var1);

   int getInt(@NotNull String var1);

   double getDouble(@NotNull String var1);

   long getLong(@NotNull String var1);

   byte getByte(@NotNull String var1);

   short getShort(@NotNull String var1);

   boolean getBoolean(@NotNull String var1);

   @NotNull
   ConfigurationSection createSection(@NotNull String var1);

   @NotNull
   ConfigurationSection getSection(@NotNull String var1);
}
