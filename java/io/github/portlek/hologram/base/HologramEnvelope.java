package io.github.portlek.hologram.base;

import io.github.portlek.hologram.api.Hologram;
import io.github.portlek.hologram.api.IHologram;
import io.github.portlek.hologram.api.MckNMSHologram;
import io.github.portlek.hologram.nms.v1_10_R1.Hologram1_10_R1;
import io.github.portlek.hologram.nms.v1_11_R1.Hologram1_11_R1;
import io.github.portlek.hologram.nms.v1_12_R1.Hologram1_12_R1;
import io.github.portlek.hologram.nms.v1_13_R1.Hologram1_13_R1;
import io.github.portlek.hologram.nms.v1_13_R2.Hologram1_13_R2;
import io.github.portlek.hologram.nms.v1_14_R1.Hologram1_14_R1;
import io.github.portlek.hologram.nms.v1_8_R1.Hologram1_8_R1;
import io.github.portlek.hologram.nms.v1_8_R2.Hologram1_8_R2;
import io.github.portlek.hologram.nms.v1_8_R3.Hologram1_8_R3;
import io.github.portlek.hologram.nms.v1_9_R1.Hologram1_9_R1;
import io.github.portlek.hologram.nms.v1_9_R2.Hologram1_9_R2;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.location.StringOf;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.versionmatched.VersionMatched;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.And;
import org.jetbrains.annotations.NotNull;

public class HologramEnvelope implements Hologram {
   private static final IHologram HOLOGRAM = (IHologram)(new VersionMatched(new MckNMSHologram(), new Class[]{Hologram1_8_R1.class, Hologram1_8_R2.class, Hologram1_8_R3.class, Hologram1_9_R1.class, Hologram1_9_R2.class, Hologram1_10_R1.class, Hologram1_11_R1.class, Hologram1_12_R1.class, Hologram1_13_R1.class, Hologram1_13_R2.class, Hologram1_14_R1.class})).of().instance();
   private static final double OFFSET = 0.23D;
   @NotNull
   private final Location location;
   @NotNull
   private final List<String> lines;
   @NotNull
   private final World world;
   @NotNull
   private final List<Integer> ids = new ArrayList();
   @NotNull
   private final List<Object> entities = new ArrayList();

   public HologramEnvelope(@NotNull Location location, @NotNull List<String> lines) {
      this.location = location;
      this.lines = new ArrayList(lines);
      this.world = (World)Objects.requireNonNull(location.getWorld());
      this.update();
   }

   public void displayTo(@NotNull Player... players) {
      Location current = this.location.clone().add(0.0D, 0.23D * (double)this.lines.size() - 1.97D, 0.0D);
      Iterator var3 = this.lines.iterator();

      while(var3.hasNext()) {
         String str = (String)var3.next();
         Object[] packet = HOLOGRAM.createPacket(current, (String)(new Colored(str)).value());
         this.ids.add((Integer)packet[1]);
         Player[] var6 = players;
         int var7 = players.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Player p = var6[var8];
            HOLOGRAM.sendPacket(p, packet[0]);
         }

         current.subtract(0.0D, 0.23D, 0.0D);
      }

   }

   public void removeFrom(@NotNull Player... players) {
      try {
         (new And((packet) -> {
            (new And((player) -> {
               HOLOGRAM.sendPacket(player, packet);
            }, new IterableOf<>(players))).value();
         }, new Mapped<>(HOLOGRAM::removePacket, this.ids))).value();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void removeLines() {
      this.lines.clear();
      this.update();
   }

   public void addLine(@NotNull String... line) {
      this.addLines(new ListOf(line));
   }

   public void addLines(@NotNull List<String> lines) {
      this.lines.addAll(lines);
      this.update();
   }

   public void setLines(@NotNull List<String> lines) {
      this.removeLines();
      this.addLines(lines);
   }

   public void spawn() {
      Location current = this.location.clone().add(0.0D, 0.23D * (double)this.lines.size() - 1.97D, 0.0D).add(0.0D, 0.23D, 0.0D);
      Iterator var2 = this.lines.iterator();

      while(var2.hasNext()) {
         String line = (String)var2.next();
         this.entities.add(HOLOGRAM.spawnHologram(line, current.subtract(0.0D, 0.23D, 0.0D)));
      }

   }

   public void save(@NotNull IYaml file, @NotNull UUID uuid) {
      file.set("Holograms." + uuid.toString() + ".location", (new StringOf(this.location)).asString());
      file.set("Holograms." + uuid.toString() + ".lines", this.lines);
   }

   public void remove() {
      Iterator var1 = this.entities.iterator();

      while(var1.hasNext()) {
         Object entity = var1.next();
         HOLOGRAM.removeHologram(this.world, entity);
      }

   }

   private void update() {
      if (!this.entities.isEmpty()) {
         Iterator var1 = this.entities.iterator();

         while(var1.hasNext()) {
            Object ent = var1.next();
            HOLOGRAM.removeHologram(this.world, ent);
         }

         Location current = this.location.clone().add(0.0D, 0.23D * (double)this.lines.size() - 1.97D, 0.0D);
         int b = 0;

         for(int j = this.lines.size(); b < j; ++b) {
            String text = (new Colored((String)this.lines.get(b))).toString();
            if (b >= this.entities.size()) {
               HOLOGRAM.spawnHologram(text, current);
            } else {
               HOLOGRAM.configureHologram(this.entities.get(b), text, current);
            }

            current.subtract(0.0D, 0.23D, 0.0D);
         }

      }
   }
}
