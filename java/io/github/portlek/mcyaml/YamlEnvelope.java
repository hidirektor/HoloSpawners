package io.github.portlek.mcyaml;

import com.google.common.base.Charsets;
import io.github.portlek.itemstack.item.ItemStackOf;
import io.github.portlek.itemstack.item.add.AddEnchantOf;
import io.github.portlek.itemstack.item.get.AmountOf;
import io.github.portlek.itemstack.item.get.DataOf;
import io.github.portlek.itemstack.item.get.DurabilityOf;
import io.github.portlek.itemstack.item.get.EnchantOf;
import io.github.portlek.itemstack.item.get.TypeOf;
import io.github.portlek.itemstack.item.meta.get.DisplayOf;
import io.github.portlek.itemstack.item.meta.get.LoreOf;
import io.github.portlek.itemstack.item.meta.get.MetaOf;
import io.github.portlek.itemstack.item.meta.set.SetDisplayOf;
import io.github.portlek.itemstack.item.meta.set.SetLoreOf;
import io.github.portlek.itemstack.item.meta.set.SetMetaOf;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.itemstack.util.ColoredList;
import io.github.portlek.itemstack.util.XMaterial;
import io.github.portlek.location.LocationOf;
import io.github.portlek.location.StringOf;
import io.github.portlek.mcyaml.mck.MckFileConfiguration;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.cactoos.io.InputOf;
import org.cactoos.io.InputStreamOf;
import org.cactoos.io.ReaderOf;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.jetbrains.annotations.NotNull;

public abstract class YamlEnvelope implements IYaml {
   @NotNull
   private final Plugin plugin;
   @NotNull
   private final File file;
   @NotNull
   private final String resourcePath;
   @NotNull
   private FileConfiguration fileConfiguration = new MckFileConfiguration();

   YamlEnvelope(@NotNull Plugin plugin, @NotNull File file, @NotNull String resourcePath) {
      this.plugin = plugin;
      this.file = file;
      this.resourcePath = resourcePath;
   }

   public void create() {
      if (this.file.exists()) {
         this.reload();
      } else {
         try {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
         } catch (Exception var2) {
            throw new RuntimeException(var2);
         }

         this.copy(new InputStreamOf(new InputOf(this.plugin.getResource(this.resourcePath))));
         this.reload();
      }
   }

   public void reload() {
      this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
      this.fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new ReaderOf(new InputOf(this.plugin.getResource(this.resourcePath)), Charsets.UTF_8)));
   }

   public void save() {
      try {
         if (this.fileConfiguration instanceof MckFileConfiguration) {
            this.reload();
         }

         this.fileConfiguration.save(this.file);
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   @NotNull
   public Object get(@NotNull String path, @NotNull Object fallback) {
      Object object = this.fileConfiguration.get(path, fallback);
      return object == null ? fallback : object;
   }

   @NotNull
   public <T> T getOrSet(@NotNull String path, @NotNull T fallback) {
      T object = (T)this.fileConfiguration.get(path, (Object)null);
      if (object != null) {
         return object;
      } else {
         this.set(path, fallback);
         return fallback;
      }
   }

   @NotNull
   public Location getLocation(@NotNull String path) {
      return (new LocationOf((String)this.getString(path).orElse(""))).value();
   }

   public void setLocation(@NotNull String path, @NotNull Location location) {
      this.set(path, (new StringOf(location)).asString());
   }

   @NotNull
   public ItemStack getCustomItemStack(@NotNull String path) {
      if (!this.getString(path).isPresent()) {
         return new ItemStack(Material.AIR);
      } else {
         Optional<String> materialString = this.getString(path + ".material");
         if (!materialString.isPresent()) {
            return new ItemStack(Material.AIR);
         } else {
            int amount = this.getInt(path + ".amount");
            ItemStackOf itemStackOf = new ItemStackOf(XMaterial.matchXMaterial((String)materialString.get()).parseMaterial(), amount == 0 ? 1 : amount, this.getShort(path + ".damage"), this.getByte(path + ".data"));
            this.getString(path + ".display-name").ifPresent((s) -> {
               ItemStack var10000 = (ItemStack)(new SetMetaOf(itemStackOf, new SetDisplayOf(new MetaOf(itemStackOf), new Colored(s)))).value();
            });
            (new SetMetaOf(itemStackOf, new SetLoreOf(new MetaOf(itemStackOf), new ColoredList(this.getStringList(path + ".lore"))))).value();
            if (!(this.getSection(path + ".enchants") instanceof MckFileConfiguration)) {
               new AddEnchantOf(itemStackOf, new MapOf(new Mapped<>((enchant) -> {
                  return new MapEntry(Enchantment.getByName(enchant), this.getInt(path + ".enchants." + enchant));
               }, new Filtered<>((key) -> {
                  return Enchantment.getByName(key) != null;
               }, this.getSection(path + ".enchants").getKeys(false)))));
            }

            return (ItemStack)itemStackOf.value();
         }
      }
   }

   public void setCustomItemStack(@NotNull String path, @NotNull ItemStack itemStack) {
      this.set(path + ".material", ((Material)(new TypeOf(itemStack)).value()).name());
      this.set(path + ".amount", (new AmountOf(itemStack)).value());
      this.set(path + ".data", ((MaterialData)(new DataOf(itemStack)).value()).getData());
      this.set(path + ".damage", (new DurabilityOf(itemStack)).value());
      ((Optional)(new DisplayOf(itemStack)).value()).ifPresent((s) -> {
         this.set(path + ".display-name", s);
      });
      this.set(path + ".lore", (new LoreOf(itemStack)).value());
      ((new EnchantOf(itemStack)).value()).forEach((enchantment, integer) -> {
         this.set(path + ".enchants." + enchantment.getName(), integer);
      });
   }

   @NotNull
   public Optional<String> getString(@NotNull String path) {
      return Optional.ofNullable(this.fileConfiguration.getString(path));
   }

   public void set(String path, Object object) {
      this.fileConfiguration.set(path, object);
      this.save();
   }

   @NotNull
   public List<String> getStringList(@NotNull String path) {
      return this.fileConfiguration.getStringList(path);
   }

   public int getInt(@NotNull String path) {
      return this.fileConfiguration.getInt(path);
   }

   public double getDouble(@NotNull String path) {
      return this.fileConfiguration.getDouble(path);
   }

   public long getLong(@NotNull String path) {
      return this.fileConfiguration.getLong(path);
   }

   public byte getByte(@NotNull String path) {
      return (byte)this.fileConfiguration.getInt(path);
   }

   public short getShort(@NotNull String path) {
      return (short)this.fileConfiguration.getInt(path);
   }

   public boolean getBoolean(@NotNull String path) {
      return this.fileConfiguration.getBoolean(path);
   }

   @NotNull
   public ConfigurationSection createSection(@NotNull String path) {
      ConfigurationSection configurationSection = this.fileConfiguration.createSection(path);
      this.save();
      return configurationSection;
   }

   @NotNull
   public ConfigurationSection getSection(@NotNull String path) {
      ConfigurationSection configurationSection = this.fileConfiguration.getConfigurationSection(path);
      return (ConfigurationSection)(configurationSection == null ? new MckFileConfiguration() : configurationSection);
   }

   private void copy(@NotNull InputStream inputStream) {
      try {
         OutputStream out = new FileOutputStream(this.file);
         Throwable var3 = null;

         try {
            byte[] buf = new byte[1024];

            int len;
            while((len = inputStream.read(buf)) > 0) {
               out.write(buf, 0, len);
            }

            out.close();
            inputStream.close();
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (out != null) {
               if (var3 != null) {
                  try {
                     out.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  out.close();
               }
            }

         }
      } catch (Exception var16) {
         throw new RuntimeException(var16);
      }
   }
}
