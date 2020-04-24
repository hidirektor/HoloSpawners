package io.github.portlek.spawner.menu.chest;

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
import io.github.portlek.spawner.menu.Menu;
import io.github.portlek.spawner.spawners.chest.user.User;
import io.github.portlek.title.base.TitlePlayerOf;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.jetbrains.annotations.NotNull;

public final class YetkililerMenu implements Menu {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final IYaml yetkililerMenu;
   @NotNull
   private final Menu previousMenu;
   @NotNull
   private final User user;

   public YetkililerMenu(@NotNull Menu previousMenu, @NotNull SpawnerAPI spawnerAPI, @NotNull IYaml yetkililerMenu, @NotNull Player player) {
      this.previousMenu = previousMenu;
      this.spawnerAPI = spawnerAPI;
      this.yetkililerMenu = yetkililerMenu;
      this.user = spawnerAPI.findByPlayer(player);
   }

   public void show() {
      Pane pane = new BasicPane(this.yetkililerMenu.getInt("panes.main-pane.x"), this.yetkililerMenu.getInt("panes.main-pane.y"), this.yetkililerMenu.getInt("panes.main-pane.height"), this.yetkililerMenu.getInt("panes.main-pane.length"));
      IntStream.range(1, 6).forEach((value) -> {
         if (value > this.user.yetkililer.size()) {
            pane.insert(this.getYetkiliOlmayanElement(value), this.yetkililerMenu.getInt("yetkili-elements.x" + value), this.yetkililerMenu.getInt("yetkili-elements.y" + value), false);
         } else {
            UUID uuid = ((UUID[])this.user.yetkililer.keySet().toArray(new UUID[0]))[value - 1];
            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (Bukkit.getOfflinePlayer(uuid).getLastPlayed() != 0L && name != null) {
               ItemStack itemStack = this.yetkililerMenu.getCustomItemStack("elements.yetkili-olan-element");
               pane.insert(new BasicElement(this.replaceAll(itemStack, uuid, name), "yetkili-olan-element-" + value, new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
                  event.cancel();
                  event.closeView();
                  this.user.removeYetkili(uuid);
                  Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, this::show, 2L);
               }, new Requirement[0])}), this.yetkililerMenu.getInt("yetkili-elements.x" + value), this.yetkililerMenu.getInt("yetkili-elements.y" + value), false);
            } else {
               pane.insert(this.getYetkiliOlmayanElement(value), this.yetkililerMenu.getInt("yetkili-elements.x" + value), this.yetkililerMenu.getInt("yetkili-elements.y" + value), false);
            }

         }
      });
      pane.insert(new BasicElement(this.yetkililerMenu.getCustomItemStack("elements.back-element"), "back-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         this.previousMenu.show();
      }, new Requirement[0])}), this.yetkililerMenu.getInt("elements.back-element.x"), this.yetkililerMenu.getInt("elements.back-element.y"), false);
      this.user.openPage(new ControllableDownPage(new ChestPage((String)(new Colored((String)this.yetkililerMenu.getString("title").orElse(""))).value(), this.yetkililerMenu.getInt("size"), new Pane[]{pane}), new Requirement[]{new NoClickableDownReq()}));
   }

   @NotNull
   private BasicElement getYetkiliOlmayanElement(int value) {
      return new BasicElement(this.yetkililerMenu.getCustomItemStack("elements.yetkili-olmayan-element"), "yetkili-olmayan-element-" + value, new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         event.closeView();
         Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
            (new AnvilGUI.Builder()).plugin(this.spawnerAPI.plugin).text(this.spawnerAPI.getConfigs().yetkiliAnvil).onComplete((player, s) -> {
               if (s.equalsIgnoreCase(player.getName())) {
                  (new TitlePlayerOf(event.player())).sendTitle(this.spawnerAPI.getLanguage().errorYouCantAddYourself);
                  return AnvilGUI.Response.close();
               } else {
                  Player nullablePlayer = Bukkit.getPlayer(s);
                  if (nullablePlayer != null && !this.user.yetkililer.containsKey(nullablePlayer.getUniqueId())) {
                     this.user.addYetkili(nullablePlayer.getUniqueId());
                     (new TitlePlayerOf(event.player())).sendTitle(this.spawnerAPI.getLanguage().generalPlayerAdded(nullablePlayer.getName()));
                     return AnvilGUI.Response.close();
                  } else if (nullablePlayer != null && this.user.yetkililer.containsKey(nullablePlayer.getUniqueId())) {
                     (new TitlePlayerOf(event.player())).sendTitle(this.spawnerAPI.getLanguage().errorPlayerAlreadyAdded(nullablePlayer.getName()));
                     return AnvilGUI.Response.close();
                  } else {
                     OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
                     if (!this.user.yetkililer.containsKey(offlinePlayer.getUniqueId()) && offlinePlayer.getName() != null && offlinePlayer.getLastPlayed() != 0L) {
                        this.user.addYetkili(offlinePlayer.getUniqueId());
                        (new TitlePlayerOf(event.player())).sendTitle(this.spawnerAPI.getLanguage().generalPlayerAdded(offlinePlayer.getName()));
                        return AnvilGUI.Response.close();
                     } else {
                        (new TitlePlayerOf(event.player())).sendTitle(this.spawnerAPI.getLanguage().errorPlayerNotFound);
                        return AnvilGUI.Response.close();
                     }
                  }
               }
            }).open(event.player());
         }, 2L);
      }, new Requirement[0])});
   }

   @NotNull
   private ItemStack replaceAll(@NotNull ItemStack itemStack, @NotNull UUID uuid, @NotNull String name) {
      return (ItemStack)(new SetMetaOf(itemStack, new SetLoreOf((ItemMeta)(new SetDisplayOf(itemStack, this.replaceAll((String)((Optional)(new DisplayOf(itemStack)).value()).orElse(""), uuid, name))).value(), new ListOf(new Mapped<>((lore) -> {
         return this.replaceAll(lore, uuid, name);
      }, (new LoreOf(itemStack)).value()))))).value();
   }

   @NotNull
   private String replaceAll(@NotNull String text, @NotNull UUID uuid, @NotNull String name) {
      return text.replaceAll("%player%", name).replaceAll("%durum%", Bukkit.getPlayer(uuid) == null ? this.spawnerAPI.getLanguage().menuStatusOffline : this.spawnerAPI.getLanguage().menuStatusOnline).replaceAll("%son-oyuna-giris-tarihi%", this.toHistory(Bukkit.getOfflinePlayer(uuid).getLastPlayed())).replaceAll("%eklenme-tarihi%", (new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss")).format(this.user.yetkililer.getOrDefault(uuid, (new Date()).getTime())));
   }

   @NotNull
   private String toHistory(long history) {
      Date date = new Date((new Date()).getTime() - history - 7200000L);
      Format format = new SimpleDateFormat("HH mm ss");
      return format.format(date);
   }
}
