package io.github.portlek.spawner.menu.chest;

import io.github.portlek.inventory.Element;
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
import io.github.portlek.itemstack.item.meta.get.DisplayOf;
import io.github.portlek.itemstack.item.meta.get.LoreOf;
import io.github.portlek.itemstack.item.meta.set.SetDisplayOf;
import io.github.portlek.itemstack.item.meta.set.SetLoreOf;
import io.github.portlek.itemstack.item.meta.set.SetMetaOf;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.spawners.chest.placed.ChestPlaced;
import io.github.portlek.spawner.spawners.chest.user.User;
import io.github.portlek.spawner.util.Util;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.SumOf;
import org.jetbrains.annotations.NotNull;
import xyz.upperlevel.spigot.book.BookUtil;

public final class ChestIciMenu {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final IYaml chestIciMenu;
   @NotNull
   private final User user;
   @NotNull
   private final ChestPlaced placed;

   public ChestIciMenu(@NotNull SpawnerAPI spawnerAPI, @NotNull Player player, @NotNull ChestPlaced placed) {
      this.spawnerAPI = spawnerAPI;
      this.chestIciMenu = spawnerAPI.chestIciMenu;
      this.user = spawnerAPI.findByPlayer(player);
      this.placed = placed;
   }

   public void show() {
      Pane pane = new BasicPane(this.chestIciMenu.getInt("panes.main-pane.x"), this.chestIciMenu.getInt("panes.main-pane.y"), this.chestIciMenu.getInt("panes.main-pane.height"), this.chestIciMenu.getInt("panes.main-pane.length"));
      Pane elementsPane = new BasicPane(this.chestIciMenu.getInt("panes.elements-pane.x"), this.chestIciMenu.getInt("panes.elements-pane.y"), this.chestIciMenu.getInt("panes.elements-pane.height"), this.chestIciMenu.getInt("panes.elements-pane.length"));
      this.placed.chest.getBlockInventory().forEach((itemStack) -> {
         if (itemStack != null) {
            pane.add((Element)(new BasicElement(itemStack, false, new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
               event.cancel();
               if (event.player().getInventory().firstEmpty() != -1) {
                  this.placed.chest.getBlockInventory().remove(event.currentItem());
                  event.player().getInventory().addItem(new ItemStack[]{event.currentItem()});
                  event.closeView();
                  Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, this::show, 2L);
               }
            }, new Requirement[0])})));
         }
      });
      elementsPane.insert(new BasicElement(this.chestIciMenu.getCustomItemStack("elements.spawner-settings-element"), new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
            (new ChestSettingsMenu(this.spawnerAPI, event.player(), this.placed)).show();
         }, 2L);
      }, new Requirement[0])}), this.chestIciMenu.getInt("elements.spawner-settings-element.x"), this.chestIciMenu.getInt("elements.spawner-settings-element.y"), false);
      elementsPane.insert(new BasicElement(this.chestIciMenu.getCustomItemStack("elements.general-settings-element"), new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
            (new GenelAyarlarMenu(event.player(), this.spawnerAPI)).show();
         }, 2L);
      }, new Requirement[0])}), this.chestIciMenu.getInt("elements.general-settings-element.x"), this.chestIciMenu.getInt("elements.general-settings-element.y"), false);
      elementsPane.insert(new BasicElement(this.replaceAll(this.chestIciMenu.getCustomItemStack("elements.sell-element")), new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, this::openBook, 2L);
      }, new Requirement[0])}), this.chestIciMenu.getInt("elements.sell-element.x"), this.chestIciMenu.getInt("elements.sell-element.y"), false);
      this.user.openPage(new ControllableDownPage(new ChestPage(this.replaceAll((String)this.chestIciMenu.getString("title").orElse("")), this.chestIciMenu.getInt("size"), new Pane[]{pane, elementsPane}), new Requirement[]{new NoClickableDownReq()}));
   }

   @NotNull
   public ItemStack replaceAll(@NotNull ItemStack item) {
      return (ItemStack)(new SetMetaOf(item, new SetLoreOf((ItemMeta)(new SetDisplayOf(item, this.replaceAll((String)((Optional)(new DisplayOf(item)).value()).orElse("")))).value(), new ListOf(new Mapped<>(this::replaceAll, (new LoreOf(item)).value()))))).value();
   }

   @NotNull
   private String replaceAll(@NotNull String text) {
      String durum = Util.getStorageContents(this.placed.chest.getBlockInventory()).length == 0 ? (String)this.chestIciMenu.getString("sell-element-durum-1").orElse("") : (String)this.chestIciMenu.getString("sell-element-durum-2").orElse("");
      return (String)(new Colored(text.replaceAll("%spawner-name%", this.placed.chestType.getName()).replaceAll("%spawner-level%", String.valueOf(this.placed.level)).replaceAll("%money%", String.valueOf((new SumOf(new Mapped<>(ItemStack::getAmount, new Filtered(Objects::nonNull, Util.getStorageContents(this.placed.chest.getBlockInventory()))))).intValue() * this.placed.chestType.money())).replaceAll("%amount%", String.valueOf((new SumOf(new Mapped<>(ItemStack::getAmount, new Filtered(Objects::nonNull, Util.getStorageContents(this.placed.chest.getBlockInventory()))))).intValue())).replaceAll("%durum%", durum.replaceAll("%amount%", String.valueOf((new SumOf(new Mapped<>(ItemStack::getAmount, new Filtered(Objects::nonNull, Util.getStorageContents(this.placed.chest.getBlockInventory()))))).intValue())).replaceAll("%spawner-name%", this.placed.chestType.getName())))).value();
   }

   private void openBook() {
      if (this.user.getPlayer() != null) {
         List<String> pages = this.spawnerAPI.getConfigs().book;
         BookUtil.PageBuilder pageBuilder = new BookUtil.PageBuilder();
         Iterator var3 = pages.iterator();

         while(var3.hasNext()) {
            String list = (String)var3.next();
            if (!list.contains("%yesno%")) {
               pageBuilder.add(BookUtil.TextBuilder.of(this.replaceAll(list)).build()).newLine();
            } else {
               pageBuilder.add(BookUtil.TextBuilder.of(this.spawnerAPI.getConfigs().yes).onClick(BookUtil.ClickAction.runCommand("/chestsp sell " + this.placed.uuid.toString())).build()).add(BookUtil.TextBuilder.of(this.spawnerAPI.getConfigs().no).onClick(BookUtil.ClickAction.runCommand("/chestsp closeInventory")).build()).newLine();
            }
         }

         BookUtil.openPlayer(this.user.getPlayer(), BookUtil.writtenBook().author("").title("").pages(pageBuilder.build()).build());
      }
   }
}
