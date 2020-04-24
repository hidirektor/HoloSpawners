package io.github.portlek.spawner.menu.natural;

import io.github.portlek.inventory.Pane;
import io.github.portlek.inventory.Requirement;
import io.github.portlek.inventory.Target;
import io.github.portlek.inventory.element.BasicElement;
import io.github.portlek.inventory.event.ElementBasicEvent;
import io.github.portlek.inventory.event.ElementDragEvent;
import io.github.portlek.inventory.page.ChestPage;
import io.github.portlek.inventory.page.ControllableDownPage;
import io.github.portlek.inventory.pane.BasicPane;
import io.github.portlek.inventory.requirement.NoClickableDownReq;
import io.github.portlek.inventory.target.BasicTarget;
import io.github.portlek.inventory.target.ClickTarget;
import io.github.portlek.inventory.target.DragTarget;
import io.github.portlek.itemstack.item.meta.set.SetDisplayOf;
import io.github.portlek.itemstack.item.meta.set.SetLoreOf;
import io.github.portlek.itemstack.item.meta.set.SetMetaOf;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.itemstack.util.ColoredList;
import io.github.portlek.location.CenteredOf;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.spawners.natural.placed.SpawnerPlaced;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.text.Replaced;
import org.cactoos.text.TextOf;
import org.jetbrains.annotations.NotNull;

public final class HologramMenu {
   @NotNull
   private final String active;
   @NotNull
   private final String deactive;
   @NotNull
   private final IYaml yaml;
   private final int hologramHeight;
   @NotNull
   private final Player player;
   @NotNull
   private final SpawnerPlaced placed;

   public HologramMenu(@NotNull SpawnerAPI spawnerAPI, @NotNull Player player, @NotNull SpawnerPlaced placed) {
      this.active = spawnerAPI.getConfigs().activeStatu;
      this.deactive = spawnerAPI.getConfigs().deactiveStatu;
      this.yaml = spawnerAPI.hologramMenu;
      this.hologramHeight = spawnerAPI.getConfigs().hologramHeight;
      this.player = player;
      this.placed = placed;
   }

   public void show() {
      ItemStack item = this.yaml.getCustomItemStack("elements.hologram-element");
      ItemMeta itemMeta = item.getItemMeta();
      if (itemMeta == null) {
         this.openWith(item);
      } else {
         String isActive = this.placed.hologramActive ? this.active : this.deactive;
         this.openWith((ItemStack)(new SetMetaOf(item, new SetLoreOf(new SetDisplayOf(item, new Colored((new Replaced(new TextOf(itemMeta.getDisplayName()), "%statu%", isActive)).toString())), new ColoredList((List)(itemMeta.getLore() == null ? new ListOf(new String[0]) : new Mapped<>((lore) -> {
            return (new Replaced(new TextOf(lore), "%statu%", isActive)).toString();
         }, itemMeta.getLore())))))).value());
      }
   }

   private void openWith(ItemStack item) {
      Pane pane = new BasicPane(this.yaml.getInt("panes.main-pane.x"), this.yaml.getInt("panes.main-pane.y"), this.yaml.getInt("panes.main-pane.height"), this.yaml.getInt("panes.main-pane.length"));
      pane.insert(new BasicElement(item, "hologram-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         this.placed.hologramActive = !this.placed.hologramActive;
         if (this.placed.hologramActive) {
            this.placed.holograms.createHologramWithId(this.placed.uuid, (new CenteredOf(this.placed.creatureSpawner.getLocation().clone().add(0.0D, (double)this.hologramHeight, 0.0D))).in(), this.replaceAll((List)(new ListOf(this.placed.spawnerType.replaceAll(this.placed.spawnerType.hologram(this.placed.size)))))).spawn();
         } else {
            this.placed.holograms.removeHologram(this.placed.uuid);
         }

      }, new Requirement[0])}), this.yaml.getInt("elements.hologram-element.x"), this.yaml.getInt("elements.hologram-element.y"), false);
      (new ControllableDownPage(new ChestPage(this.replaceAll((String)this.yaml.getString("title").orElse("")), this.yaml.getInt("size"), new Pane[]{pane}), new Requirement[]{new NoClickableDownReq()})).showTo(this.player);
   }

   @NotNull
   private List<String> replaceAll(@NotNull List<String> replaced) {
      return new Mapped<>(this::replaceAll, replaced);
   }

   @NotNull
   private String replaceAll(@NotNull String replaced) {
      return (String)(new Colored(this.placed.spawnerType.replaceAll(replaced.replaceAll("%spawner-owner%", (String)Optional.ofNullable(Bukkit.getOfflinePlayer(this.placed.owner).getName()).orElse("UNKNOWN"))))).value();
   }
}
