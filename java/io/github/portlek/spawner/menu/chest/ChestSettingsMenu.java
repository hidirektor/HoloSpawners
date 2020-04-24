package io.github.portlek.spawner.menu.chest;

import io.github.portlek.inventory.Element;
import io.github.portlek.inventory.Pane;
import io.github.portlek.inventory.Requirement;
import io.github.portlek.inventory.Target;
import io.github.portlek.inventory.element.BasicElement;
import io.github.portlek.inventory.event.ElementBasicEvent;
import io.github.portlek.inventory.event.ElementClickEvent;
import io.github.portlek.inventory.event.ElementDragEvent;
import io.github.portlek.inventory.page.ChestPage;
import io.github.portlek.inventory.page.ControllableDownPage;
import io.github.portlek.inventory.pane.BasicPane;
import io.github.portlek.inventory.requirement.NoClickableDownReq;
import io.github.portlek.inventory.target.BasicTarget;
import io.github.portlek.inventory.target.ClickTarget;
import io.github.portlek.inventory.target.DragTarget;
import io.github.portlek.itemstack.item.meta.get.DisplayOf;
import io.github.portlek.itemstack.item.meta.get.LoreOf;
import io.github.portlek.itemstack.item.meta.set.SetDisplayOf;
import io.github.portlek.itemstack.item.meta.set.SetLoreOf;
import io.github.portlek.itemstack.item.meta.set.SetMetaOf;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.menu.Menu;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import io.github.portlek.spawner.spawners.chest.user.User;
import io.github.portlek.spawner.util.Util;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOfInts;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.jetbrains.annotations.NotNull;

public final class ChestSettingsMenu implements Menu {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final User user;
   @NotNull
   private final ChestPlaced placed;
   @NotNull
   private final IYaml sandikSettingsMenu;
   @NotNull
   private final IYaml ganimetDeposuMenu;

   public ChestSettingsMenu(@NotNull SpawnerAPI spawnerAPI, @NotNull Player player, @NotNull ChestPlaced placed) {
      this.spawnerAPI = spawnerAPI;
      this.user = spawnerAPI.findByPlayer(player);
      this.placed = placed;
      this.sandikSettingsMenu = spawnerAPI.sandikSettingsMenu;
      this.ganimetDeposuMenu = spawnerAPI.ganimetDeposuMenu;
   }

   public void show() {
      Pane pane = new BasicPane(this.sandikSettingsMenu.getInt("panes.main-pane.x"), this.sandikSettingsMenu.getInt("panes.main-pane.y"), this.sandikSettingsMenu.getInt("panes.main-pane.height"), this.sandikSettingsMenu.getInt("panes.main-pane.length"));
      ItemStack esyaDusmesiItem = this.sandikSettingsMenu.getCustomItemStack("elements.esya-dusmesi-element");
      this.placed.replaceAll(esyaDusmesiItem);
      Element esyaDusmesiElement = new BasicElement(this.replaceAll(esyaDusmesiItem), "esya-dusmesi-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         this.placed.changeEsyaDusmesi();
         this.show();
      }, new Requirement[0])});
      pane.insert(new BasicElement(this.replaceAll(this.sandikSettingsMenu.getCustomItemStack("elements.ganimet-deposu-element")), "ganimet-deposu-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         (new GanimetDeposuMenu(this.spawnerAPI, this, this.ganimetDeposuMenu, event.player())).show();
      }, new Requirement[0])}), this.sandikSettingsMenu.getInt("elements.ganimet-deposu-element.x"), this.sandikSettingsMenu.getInt("elements.ganimet-deposu-element.y"), false);
      pane.insert(esyaDusmesiElement, this.sandikSettingsMenu.getInt("elements.esya-dusmesi-element.x"), this.sandikSettingsMenu.getInt("elements.esya-dusmesi-element.y"), false);
      if (this.placed.level >= this.placed.chestType.maxLevel()) {
         pane.insert(new BasicElement(this.replaceAll(this.sandikSettingsMenu.getCustomItemStack("elements.cant-level-element")), "cant-level-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget(ElementClickEvent::cancel, new Requirement[0])}), this.sandikSettingsMenu.getInt("elements.cant-level-element.x"), this.sandikSettingsMenu.getInt("elements.cant-level-element.y"), false);
      } else {
         pane.insert(new BasicElement(this.replaceAll(this.sandikSettingsMenu.getCustomItemStack("elements.level-element")), "level-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
            event.cancel();
            event.closeView();
            PlayerInventory inventory = event.player().getInventory();
            List<Integer> filtered = new ListOf(new Filtered<>((i) -> {
               ItemStack itemStack = inventory.getItem(i);
               return itemStack != null && itemStack.isSimilar(this.placed.chestType.getChestItem(1));
            }, new IterableOfInts(IntStream.range(0, Util.getStorageContents(inventory).length).toArray())));
            if (filtered.isEmpty()) {
               event.player().sendMessage(this.spawnerAPI.getLanguage().errorNotEnoughItem);
            } else {
               ItemStack itemStack = inventory.getItem((Integer)filtered.get(0));
               if (itemStack == null) {
                  event.player().sendMessage(this.spawnerAPI.getLanguage().errorNotEnoughItem);
               } else {
                  itemStack.setAmount(itemStack.getAmount() - 1);
                  if (itemStack.getAmount() > 0) {
                     event.player().getInventory().setItem((Integer)filtered.get(0), itemStack);
                  } else {
                     event.player().getInventory().setItem((Integer)filtered.get(0), (ItemStack)null);
                  }

                  Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
                     this.placed.levelUp();
                     this.show();
                  }, 2L);
               }
            }
         }, new Requirement[0])}), this.sandikSettingsMenu.getInt("elements.level-element.x"), this.sandikSettingsMenu.getInt("elements.level-element.y"), false);
      }

      this.user.openPage(new ControllableDownPage(new ChestPage(this.replaceAll((String)this.sandikSettingsMenu.getString("title").orElse("")), this.sandikSettingsMenu.getInt("size"), new Pane[]{pane}), new Requirement[]{new NoClickableDownReq()}));
   }

   @NotNull
   public ItemStack replaceAll(@NotNull ItemStack item) {
      return (ItemStack)(new SetMetaOf(item, new SetLoreOf((ItemMeta)(new SetDisplayOf(item, this.replaceAll((String)((Optional)(new DisplayOf(item)).value()).orElse("")))).value(), new ListOf(new Mapped<>(this::replaceAll, (new LoreOf(item)).value()))))).value();
   }

   @NotNull
   private String replaceAll(@NotNull String text) {
      return (String)(new Colored(text.replaceAll("%spawner-name%", this.placed.chestType.getName()).replaceAll("%spawner-level%", String.valueOf(this.placed.level)))).value();
   }
}
