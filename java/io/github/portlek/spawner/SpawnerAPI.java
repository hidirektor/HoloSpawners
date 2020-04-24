package io.github.portlek.spawner;

import io.github.portlek.hologram.base.Holograms;
import io.github.portlek.location.LocationOf;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.mcyaml.YamlOf;
import io.github.portlek.mcyaml.mck.MckFileConfiguration;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.base.SpawnerNBTOf;
import io.github.portlek.spawner.files.Config;
import io.github.portlek.spawner.files.ConfigOptions;
import io.github.portlek.spawner.files.Language;
import io.github.portlek.spawner.files.LanguageOptions;
import io.github.portlek.spawner.hook.VaultWrapper;
import io.github.portlek.spawner.mock.MckChestType;
import io.github.portlek.spawner.mock.MckSpawnerType;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import io.github.portlek.spawner.spawners.chest.type.ChestType;
import io.github.portlek.spawner.spawners.chest.type.ChestTypeBasic;
import io.github.portlek.spawner.spawners.chest.user.User;
import io.github.portlek.spawner.spawners.natural.drop.Drop;
import io.github.portlek.spawner.spawners.natural.placed.SpawnerPlaced;
import io.github.portlek.spawner.spawners.natural.type.SpawnerType;
import io.github.portlek.spawner.spawners.natural.type.SpawnerTypeBasic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.scalar.And;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpawnerAPI {
   protected static final Map<UUID, SpawnerPlaced> PLACED_SPAWNERS = new HashMap();
   protected static final Map<UUID, ChestPlaced> PLACED_CHESTS = new HashMap();
   @NotNull
   public final Plugin plugin;
   @NotNull
   public final VaultWrapper vaultWrapper;
   @NotNull
   public final IYaml hologramMenu;
   @NotNull
   public final IYaml chestIciMenu;
   @NotNull
   public final IYaml genelAyarlarMenu;
   @NotNull
   public final IYaml ganimetDeposuMenu;
   @NotNull
   public final IYaml sandikSettingsMenu;
   @NotNull
   public final IYaml yetkilerMenu;
   @NotNull
   public final IYaml spawners;
   @NotNull
   public final IYaml chests;
   @NotNull
   public final IYaml usersFile;
   @NotNull
   public final Holograms holograms;
   @NotNull
   private final ConfigOptions configOptions;
   @NotNull
   private final LanguageOptions languageOptions;
   @NotNull
   public final IYaml placedSpawner;
   @NotNull
   public final IYaml placedChest;
   @NotNull
   public final Map<String, SpawnerType> spawnerTypes = new HashMap();
   @NotNull
   public final Map<String, ChestType> chestTypes = new HashMap();
   @NotNull
   public final Map<UUID, User> users = new HashMap();

   public SpawnerAPI(@NotNull Plugin plugin, @NotNull VaultWrapper vaultWrapper, @NotNull IYaml hologramMenu, @NotNull IYaml chestIciMenu, @NotNull IYaml genelAyarlarMenu, @NotNull IYaml ganimetDeposuMenu, @NotNull IYaml sandikSettingsMenu, @NotNull IYaml yetkilerMenu, @NotNull IYaml spawners, @NotNull IYaml chests, @NotNull IYaml usersFile, @NotNull Holograms holograms, @NotNull ConfigOptions configOptions, @NotNull IYaml placedSpawner, @NotNull IYaml placedChest) {
      this.plugin = plugin;
      this.vaultWrapper = vaultWrapper;
      this.hologramMenu = hologramMenu;
      this.chestIciMenu = chestIciMenu;
      this.genelAyarlarMenu = genelAyarlarMenu;
      this.ganimetDeposuMenu = ganimetDeposuMenu;
      this.sandikSettingsMenu = sandikSettingsMenu;
      this.yetkilerMenu = yetkilerMenu;
      this.spawners = spawners;
      this.chests = chests;
      this.usersFile = usersFile;
      this.holograms = holograms;
      this.configOptions = configOptions;
      this.languageOptions = new LanguageOptions(new YamlOf(plugin, "languages", this.getConfigs().language));
      this.placedSpawner = placedSpawner;
      this.placedChest = placedChest;
   }

   public void reloadPlugin() {
      this.configOptions.create();
      this.languageOptions.create();
      this.spawners.create();
      this.chests.create();
      this.usersFile.create();
      this.placedSpawner.create();
      this.placedSpawner.createSection("Spawners");
      this.placedChest.create();
      if (this.placedChest.getSection("Spawners") instanceof MckFileConfiguration) {
         this.placedChest.createSection("Spawners");
      }

      PLACED_CHESTS.values().forEach(ChestPlaced::close);
      PLACED_CHESTS.clear();
      PLACED_SPAWNERS.clear();
      this.holograms.initHologram();
      this.hologramMenu.create();
      this.chestIciMenu.create();
      this.genelAyarlarMenu.create();
      this.ganimetDeposuMenu.create();
      this.sandikSettingsMenu.create();
      this.yetkilerMenu.create();
      this.loadNaturalSpawners();
      this.loadChestSpawners();
      this.loadUsers();
   }

   public void add(@NotNull SpawnerPlaced placed) {
      PLACED_SPAWNERS.put(placed.uuid, placed);
      placed.saveTo(this.placedSpawner);
   }

   public void add(@NotNull ChestPlaced placed) {
      PLACED_CHESTS.put(placed.uuid, placed);
      placed.saveTo(this.placedChest);
      placed.update();
      User user = this.findUserByUUID(placed.owner);
      user.placeds.add(placed);
      user.saveTo(this.usersFile);
   }

   public void remove(@NotNull SpawnerPlaced placed) {
      placed.remove();
      PLACED_SPAWNERS.remove(placed.uuid);
      this.placedSpawner.set("Spawners." + placed.uuid.toString(), (Object)null);
   }

   public void remove(@NotNull ChestPlaced placed) {
      placed.remove();
      PLACED_CHESTS.remove(placed.uuid);
      this.placedChest.set("Spawners." + placed.uuid.toString(), (Object)null);
      User user = this.findUserByUUID(placed.owner);
      user.placeds.remove(placed);
      user.saveTo(this.usersFile);
   }

   public void remove(@NotNull CreatureSpawner creatureSpawner) {
      SpawnerPlaced placed = this.findByLocation(creatureSpawner.getLocation());
      if (placed != null) {
         this.remove(placed);
      }

   }

   @NotNull
   public SpawnerType findSpawnerTypeById(@NotNull String id) {
      return (SpawnerType)this.spawnerTypes.getOrDefault(id, new MckSpawnerType());
   }

   @NotNull
   public User findUserByUUID(@NotNull UUID uuid) {
      Iterator var2 = this.users.values().iterator();

      User user;
      do {
         if (!var2.hasNext()) {
            User usr = new User(this, uuid, new HashMap(), new ArrayList(), this.getDurumFromInt(0), 0);
            this.users.put(uuid, usr);
            usr.saveTo(this.usersFile);
            return usr;
         }

         user = (User)var2.next();
      } while(!user.uuid.equals(uuid));

      return user;
   }

   @NotNull
   public User findByPlayer(@NotNull Player player) {
      return this.findUserByUUID(player.getUniqueId());
   }

   @Nullable
   public ChestPlaced findChestBySignLocation(@NotNull Location location) {
      Iterator var2 = PLACED_CHESTS.values().iterator();

      ChestPlaced placed;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         placed = (ChestPlaced)var2.next();
      } while(!placed.sign.getLocation().equals(location));

      return placed;
   }

   @Nullable
   public ChestPlaced findChestByLocation(@NotNull Location location) {
      Iterator var2 = PLACED_CHESTS.values().iterator();

      ChestPlaced placed;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         placed = (ChestPlaced)var2.next();
      } while(!placed.chest.getLocation().equals(location));

      return placed;
   }

   @Nullable
   public SpawnerPlaced findByLocation(@NotNull Location location) {
      Iterator var2 = PLACED_SPAWNERS.values().iterator();

      SpawnerPlaced placed;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         placed = (SpawnerPlaced)var2.next();
      } while(!placed.creatureSpawner.getLocation().equals(location));

      return placed;
   }

   @NotNull
   public ChestType findById(@NotNull String id) {
      return (ChestType)this.chestTypes.getOrDefault(id, new MckChestType());
   }

   @Nullable
   public ChestPlaced findByUUID(@NotNull UUID uuid) {
      Iterator var2 = PLACED_CHESTS.values().iterator();

      ChestPlaced placed;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         placed = (ChestPlaced)var2.next();
      } while(!placed.uuid.equals(uuid));

      return placed;
   }

   @Nullable
   public ChestPlaced findByUUID(@NotNull String uuid) {
      return this.findByUUID(UUID.fromString(uuid));
   }

   @NotNull
   public Config getConfigs() {
      return this.configOptions.getConfig();
   }

   @NotNull
   public Language getLanguage() {
      return this.languageOptions.getLanguage();
   }

   private void loadNaturalSpawners() {
      this.spawnerTypes.clear();
      Iterator var1 = this.spawners.getSection("").getKeys(false).iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         this.spawnerTypes.put(key, new SpawnerTypeBasic(this.getConfigs(), key, (String)this.spawners.getString(key + ".name").orElse(""), this.spawners.getCustomItemStack(key + ".spawner-item"), new Drop(key, EntityType.valueOf((String)this.spawners.getString(key + ".entity-type").orElse("PIG")), this.spawners.getCustomItemStack(key + ".item")), this.spawners.getInt(key + ".speed"), this.spawners.getInt(key + ".spawn-range"), this.spawners.getInt(key + ".required-spawn-range"), this.spawners.getInt(key + ".count"), this.spawners.getInt(key + ".max-entity-spawn")));
      }

      And spawnerLoad = new And((world) -> {
         return (new And((chunk) -> {
            return (new And((blockState) -> {
               CreatureSpawner creatureSpawner = (CreatureSpawner)blockState;
               NBTCompound spawnDataCompound = (new SpawnerNBTOf(creatureSpawner)).nbt().getNBTCompound("SpawnData");
               if (spawnDataCompound.has("zpawner-spawner-id")) {
                  SpawnerType spawnerType = (SpawnerType)this.spawnerTypes.getOrDefault(spawnDataCompound.getString("zpawner-spawner-id"), new MckSpawnerType());
                  int size = spawnDataCompound.getInt("zpawner-size");

                  UUID owner;
                  try {
                     owner = UUID.fromString(spawnDataCompound.getString("zpawner-owner"));
                  } catch (Exception var8) {
                     owner = UUID.randomUUID();
                  }

                  spawnerType.create(creatureSpawner, size, owner);
                  this.add(new SpawnerPlaced(this, UUID.randomUUID(), owner, creatureSpawner, spawnerType, this.holograms, size));
               }
            }, new Filtered((tile) -> {
               return tile instanceof CreatureSpawner;
            }, chunk.getTileEntities()))).value();
         }, new IterableOf<>(world.getLoadedChunks()))).value();
      }, Bukkit.getWorlds());

      try {
         spawnerLoad.value();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void loadChestSpawners() {
      this.chestTypes.clear();
      Iterator var1 = this.chests.getSection("").getKeys(false).iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         this.chestTypes.put(key, new ChestTypeBasic(this, key, (String)this.chests.getString(key + ".name").orElse(key), new ListOf(new Mapped((levelKey) -> {
            return this.chests.getInt(key + "." + levelKey + ".speed");
         }, new Filtered<>((filtered) -> {
            return filtered.startsWith("level-");
         }, this.chests.getSection(key).getKeys(false)))), new ListOf(new Mapped((levelKey) -> {
            return this.chests.getInt(key + "." + levelKey + ".amount");
         }, new Filtered<>((filtered) -> {
            return filtered.startsWith("level-");
         }, this.chests.getSection(key).getKeys(false)))), this.chests.getInt(key + ".money"), this.chests.getCustomItemStack(key + ".item")));
      }

      Function<String, UUID> uuid = UUID::fromString;
      Function<String, UUID> owner = (s) -> {
         return UUID.fromString((String)this.placedChest.getString("Spawners." + s + ".owner").orElse(""));
      };
      Function<String, BlockState> chest = (s) -> {
         return (new LocationOf((String)this.placedChest.getString("Spawners." + s + ".chest-location").orElse(""))).value().getBlock().getState();
      };
      Function<String, BlockState> sign = (s) -> {
         return (new LocationOf((String)this.placedChest.getString("Spawners." + s + ".sign-location").orElse(""))).value().getBlock().getState();
      };
      Function<String, ChestType> chestType = (s) -> {
         return this.findById((String)this.placedChest.getString("Spawners." + s + ".type").orElse(""));
      };
      ToIntFunction<String> storage = (s) -> {
         return this.placedChest.getInt("Spawners." + s + ".storage");
      };
      ToIntFunction<String> durum = (s) -> {
         return this.placedChest.getInt("Spawners." + s + ".durum");
      };
      ToIntFunction<String> level = (s) -> {
         return this.placedChest.getInt("Spawners." + s + ".level");
      };
      And chestLoad = new And((filtered) -> {
         ChestPlaced placed = new ChestPlaced(this, (UUID)uuid.apply(filtered), (UUID)owner.apply(filtered), (Chest)chest.apply(filtered), (Sign)sign.apply(filtered), (ChestType)chestType.apply(filtered), storage.applyAsInt(filtered), durum.applyAsInt(filtered), level.applyAsInt(filtered));
         PLACED_CHESTS.put(placed.uuid, placed);
         placed.saveTo(this.placedChest);
         placed.update();
      }, new Filtered<>((keyx) -> {
         return owner.apply(keyx) != null && !(chestType.apply(keyx) instanceof MckChestType) && chest.apply(keyx) instanceof Chest && sign.apply(keyx) instanceof Sign;
      }, this.placedChest.getSection("Spawners").getKeys(false)));

      try {
         chestLoad.value();
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   private void loadUsers() {
      this.users.clear();
      Iterator var1 = this.usersFile.getSection("Users").getKeys(false).iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         this.users.put(UUID.fromString(key), new User(this, UUID.fromString(key), new HashMap(new MapOf(new org.cactoos.list.Mapped<>((yetkili) -> {
            return new MapEntry(UUID.fromString(yetkili), this.usersFile.getLong("Users." + key + ".yetkililer." + yetkili));
         }, this.usersFile.getSection("Users." + key + ".yetkililer").getKeys(false)))), new ArrayList(new org.cactoos.list.Mapped<>(this::findByUUID, new Filtered<>((placed) -> {
            return Objects.nonNull(this.findByUUID(placed));
         }, this.usersFile.getStringList("Users." + key + ".placeds")))), this.getDurumFromInt(this.usersFile.getInt("Users." + key + ".durum")), this.usersFile.getInt("Users." + key + ".durum")));
      }

   }

   @NotNull
   public String getDurumFromInt(int durum) {
      if (durum == 0) {
         return this.getLanguage().chestStatusIn;
      } else {
         return durum == 1 ? this.getLanguage().chestStatusOut : this.getLanguage().chestStatusStorage;
      }
   }
}
