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
import io.github.portlek.inventory.requirement.ClickTypeReq;
import io.github.portlek.inventory.requirement.NoClickableDownReq;
import io.github.portlek.inventory.target.BasicTarget;
import io.github.portlek.inventory.target.ClickTarget;
import io.github.portlek.inventory.target.DragTarget;
import io.github.portlek.itemstack.item.meta.get.DisplayOf;
import io.github.portlek.itemstack.item.meta.get.LoreOf;
import io.github.portlek.itemstack.item.meta.set.SetDisplayOf;
import io.github.portlek.itemstack.item.meta.set.SetLoreOf;
import io.github.portlek.itemstack.item.meta.set.SetMetaOf;
import io.github.portlek.itemstack.item.set.SetTypeOf;
import io.github.portlek.itemstack.util.Colored;
import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.spawner.SpawnerAPI;
import io.github.portlek.spawner.menu.Menu;
import io.github.portlek.spawner.spawners.chest.type.ChestType;
import io.github.portlek.spawner.spawners.chest.user.User;
import io.github.portlek.title.base.TitlePlayerOf;
import java.util.Iterator;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.MinOf;
import org.jetbrains.annotations.NotNull;

public final class GanimetDeposuMenu implements Menu {
   @NotNull
   private final SpawnerAPI spawnerAPI;
   @NotNull
   private final Menu previousMenu;
   @NotNull
   private final IYaml gDYaml;
   @NotNull
   private final User user;

   public GanimetDeposuMenu(@NotNull SpawnerAPI spawnerAPI, @NotNull Menu previousMenu, @NotNull IYaml gDYaml, @NotNull Player player) {
      this.spawnerAPI = spawnerAPI;
      this.previousMenu = previousMenu;
      this.gDYaml = gDYaml;
      this.user = spawnerAPI.findByPlayer(player);
   }

   public void show() {
      Pane pane1 = new BasicPane(this.gDYaml.getInt("panes.pane-1.x"), this.gDYaml.getInt("panes.pane-1.y"), this.gDYaml.getInt("panes.pane-1.height"), this.gDYaml.getInt("panes.pane-1.length"));
      Pane pane2 = new BasicPane(this.gDYaml.getInt("panes.pane-2.x"), this.gDYaml.getInt("panes.pane-2.y"), this.gDYaml.getInt("panes.pane-2.height"), this.gDYaml.getInt("panes.pane-2.length"));
      Pane pane3 = new BasicPane(this.gDYaml.getInt("panes.pane-3.x"), this.gDYaml.getInt("panes.pane-3.y"), this.gDYaml.getInt("panes.pane-3.height"), this.gDYaml.getInt("panes.pane-3.length"));
      Pane pane4 = new BasicPane(this.gDYaml.getInt("panes.pane-4.x"), this.gDYaml.getInt("panes.pane-4.y"), this.gDYaml.getInt("panes.pane-4.height"), this.gDYaml.getInt("panes.pane-4.length"));
      Pane paneGanimet = new BasicPane(this.gDYaml.getInt("panes.pane-ganimet.x"), this.gDYaml.getInt("panes.pane-ganimet.y"), this.gDYaml.getInt("panes.pane-ganimet.height"), this.gDYaml.getInt("panes.pane-ganimet.length"));
      ItemStack ganimetElement = this.gDYaml.getCustomItemStack("elements.ganimet-element");
      Iterator var7 = this.spawnerAPI.chestTypes.entrySet().iterator();

      while(var7.hasNext()) {
         Entry<String, ChestType> entry = (Entry)var7.next();
         ChestType chestType = (ChestType)entry.getValue();
         ItemStack clone = this.replaceAll(ganimetElement.clone(), chestType);
         paneGanimet.add((Element)(new BasicElement(clone, "ganimet-element-" + chestType.getId(), new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget(ElementClickEvent::cancel, new Requirement[0]), new ClickTarget(ElementClickEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
            event.cancel();
            event.closeView();
            Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
               (new AnvilGUI.Builder()).plugin(this.spawnerAPI.plugin).text(this.spawnerAPI.getConfigs().ganimetAnvilAlis).onComplete(this.response(chestType)).open(event.player());
            }, 2L);
         }, new Requirement[]{new ClickTypeReq(ClickType.RIGHT)}), new ClickTarget((event) -> {
            event.cancel();
            event.closeView();
            Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
               (new AnvilGUI.Builder()).plugin(this.spawnerAPI.plugin).text((new MinOf(new Integer[]{this.user.getAvailableAmountForItem(chestType.getDrop()), this.user.getStorageAmountOfChestType(chestType)})).intValue() + "...").onComplete(this.response(chestType)).open(event.player());
            }, 2L);
         }, new Requirement[]{new ClickTypeReq(ClickType.SHIFT_RIGHT)}), new ClickTarget((event) -> {
            event.cancel();
            event.closeView();
            Bukkit.getScheduler().runTaskLater(this.spawnerAPI.plugin, () -> {
               (new AnvilGUI.Builder()).plugin(this.spawnerAPI.plugin).text(this.spawnerAPI.getConfigs().ganimetAnvilSatis).onComplete(this.response(this.spawnerAPI.getLanguage().errorYouCantTakeThisSoMuch, (amount) -> {
                  return amount <= this.user.getStorageAmountOfChestType(chestType);
               }, (amount, player) -> {
                  this.user.sellStorageItem(player, chestType, amount);
               })).open(event.player());
            }, 2L);
         }, new Requirement[]{new ClickTypeReq(ClickType.MIDDLE)})})));
      }

      pane4.insert(new BasicElement(this.gDYaml.getCustomItemStack("elements.back-element"), "back-element", new Target[]{new BasicTarget(ElementBasicEvent::cancel, new Requirement[0]), new DragTarget(ElementDragEvent::cancel, new Requirement[0]), new ClickTarget((event) -> {
         event.cancel();
         this.previousMenu.show();
      }, new Requirement[0])}), this.gDYaml.getInt("elements.back-element.x"), this.gDYaml.getInt("elements.back-element.y"), false);
      this.user.openPage(new ControllableDownPage(new ChestPage((String)(new Colored((String)this.gDYaml.getString("title").orElse(""))).value(), this.gDYaml.getInt("size"), new Pane[]{pane1, pane2, pane3, pane4, paneGanimet}), new Requirement[]{new NoClickableDownReq()}));
   }

   @NotNull
   public ItemStack replaceAll(@NotNull ItemStack item, @NotNull ChestType chestType) {
      return this.user.getPlayer() == null ? item : (ItemStack)(new SetMetaOf(new SetTypeOf(item, chestType.getDrop().getType()), new SetLoreOf((ItemMeta)(new SetDisplayOf(item, this.replaceAll((String)((Optional)(new DisplayOf(item)).value()).orElse(""), chestType))).value(), new ListOf(new Mapped<>((lore) -> {
         return this.replaceAll(lore, chestType);
      }, (new LoreOf(item)).value()))))).value();
   }

   @NotNull
   public String replaceAll(@NotNull String text, @NotNull ChestType chestType) {
      return this.user.getPlayer() == null ? text : text.replaceAll("%chest-name%", chestType.getName()).replaceAll("%chest-amount%", String.valueOf(this.user.getAmountOfChestType(chestType))).replaceAll("%drop-amount%", String.valueOf(this.user.getStorageAmountOfChestType(chestType))).replaceAll("%available-amount%", String.valueOf((new MinOf(new Integer[]{this.user.getAvailableAmountForItem(chestType.getDrop()), this.user.getStorageAmountOfChestType(chestType)})).intValue()));
   }

   @NotNull
   private BiFunction<Player, String, AnvilGUI.Response> response(@NotNull ChestType chestType) {
      return this.response(this.spawnerAPI.getLanguage().errorYouCantTakeThisSoMuch, (amount) -> {
         return amount <= (new MinOf(new Integer[]{this.user.getAvailableAmountForItem(chestType.getDrop()), this.user.getStorageAmountOfChestType(chestType)})).intValue();
      }, (amount, player) -> {
         ItemStack itemStack = chestType.getDrop().clone();
         int amount1 = amount / itemStack.getMaxStackSize();
         int amount2 = amount % itemStack.getMaxStackSize();
         itemStack.setAmount(itemStack.getMaxStackSize());
         IntStream.range(0, amount1).forEach((value) -> {
            player.getInventory().addItem(new ItemStack[]{itemStack});
         });
         itemStack.setAmount(amount2);
         player.getInventory().addItem(new ItemStack[]{itemStack});
         this.user.removeStorage(chestType, amount);
      });
   }

   @NotNull
   private BiFunction<Player, String, AnvilGUI.Response> response(@NotNull String errorTitle, @NotNull Predicate<Integer> function, @NotNull BiConsumer<Integer, Player> consumer) {
      return (player, s) -> {
         int amount;
         try {
            amount = Integer.parseInt(s);
         } catch (Exception var8) {
            (new TitlePlayerOf(player)).sendTitle(this.spawnerAPI.getLanguage().errorInputNumber);
            return AnvilGUI.Response.close();
         }

         if (function.test(amount)) {
            consumer.accept(amount, player);
            return AnvilGUI.Response.close();
         } else {
            (new TitlePlayerOf(player)).sendTitle((String)(new Colored(errorTitle)).value());
            return AnvilGUI.Response.close();
         }
      };
   }
}
