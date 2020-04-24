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
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.menu.Menu;
import io.github.portlek.spawner.spawners.chest.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class GenelAyarlarMenu implements Menu {
   @NotNull
   private SpawnerAPI spawnerAPI;
   @NotNull
   private final IYaml yetkililerMenu;
   @NotNull
   private final IYaml ganimetDeposuMenu;
   @NotNull
   private final IYaml genelAyarlarMenu;
   @NotNull
   private final User user;

   public GenelAyarlarMenu(@NotNull Player player, @NotNull SpawnerAPI spawnerAPI) {
      this.spawnerAPI = spawnerAPI;
      this.yetkililerMenu = spawnerAPI.yetkilerMenu;
      this.ganimetDeposuMenu = spawnerAPI.ganimetDeposuMenu;
      this.genelAyarlarMenu = spawnerAPI.genelAyarlarMenu;
      this.user = spawnerAPI.findByPlayer(player);
   }

   public void show() {
      ItemStack esyaDusmesiItem = this.genelAyarlarMenu.getCustomItemStack("elements.esya-dusmesi-element");
      ItemStack oyuncuEkleItem = this.genelAyarlarMenu.getCustomItemStack("elements.oyuncu-ekle-element");
      this.user.replaceAll(esyaDusmesiItem);
      this.user.replaceAll(oyuncuEkleItem);
      Element esyaDusmesiElement = new BasicElement(esyaDusmesiItem, "esya-dusmesi-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         this.user.changeEsyaDusmesi();
         this.show();
      }, new Requirement[0])});
      Element oyuncuEkleElement = new BasicElement(oyuncuEkleItem, "oyuncu-ekle-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
            (new YetkililerMenu(this, this.spawnerAPI, this.yetkililerMenu, event.player())).show();
         }, 2L);
      }, new Requirement[0])});
      Pane pane = new BasicPane(this.genelAyarlarMenu.getInt("panes.main-pane.x"), this.genelAyarlarMenu.getInt("panes.main-pane.y"), this.genelAyarlarMenu.getInt("panes.main-pane.height"), this.genelAyarlarMenu.getInt("panes.main-pane.length"));
      pane.insert(new BasicElement(this.genelAyarlarMenu.getCustomItemStack("elements.ganimet-deposu-element"), "ganimet-deposu-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         (new GanimetDeposuMenu(this.spawnerAPI, this, this.ganimetDeposuMenu, event.player())).show();
      }, new Requirement[0])}), this.genelAyarlarMenu.getInt("elements.ganimet-deposu-element.x"), this.genelAyarlarMenu.getInt("elements.ganimet-deposu-element.y"), false);
      pane.insert(esyaDusmesiElement, this.genelAyarlarMenu.getInt("elements.esya-dusmesi-element.x"), this.genelAyarlarMenu.getInt("elements.esya-dusmesi-element.y"), false);
      pane.insert(oyuncuEkleElement, this.genelAyarlarMenu.getInt("elements.oyuncu-ekle-element.x"), this.genelAyarlarMenu.getInt("elements.oyuncu-ekle-element.y"), false);
      this.user.openPage(new ControllableDownPage(new ChestPage((String)(new Colored((String)this.genelAyarlarMenu.getString("title").orElse(""))).value(), this.genelAyarlarMenu.getInt("size"), new Pane[]{pane}), new Requirement[]{new NoClickableDownReq()}));
   }
}
