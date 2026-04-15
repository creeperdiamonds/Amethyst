package tech.amethyst.base.notify;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import java.util.Iterator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.base.events.impl.other.EventModuleToggle;
import tech.amethyst.base.events.impl.player.EventPickupItem;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.base.events.impl.server.EventPacket;
import tech.amethyst.client.hud.elements.component.NotifyComponent;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.utility.game.other.NetworkUtils;
public class NotifyManager {
   private static NotifyManager instance;
   private NotifyComponent notifyComponent;
   private boolean notifedHelmet;
   private boolean notifedChestplate;
   private boolean notifedLeggings;
   private boolean notifedBoots;
   public NotifyManager() {
      EventManager.register(this);
   }
   public static NotifyManager getInstance() {
      if (instance == null) {
         instance = new NotifyManager();
      }
      return instance;
   }
   public void setNotifyComponent(NotifyComponent component) {
      this.notifyComponent = component;
   }
   @EventTarget
   private void onUpdate(EventUpdate e) {
      if (this.notifyComponent != null && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
         if (!MinecraftClient.getInstance().player.getInventory().getArmorStack(3).isEmpty() && Math.abs(MinecraftClient.getInstance().player.getInventory().getArmorStack(3).getDamage() - MinecraftClient.getInstance().player.getInventory().getArmorStack(3).getMaxDamage()) < MinecraftClient.getInstance().player.getInventory().getArmorStack(3).getMaxDamage() / 5) {
            if (!this.notifedHelmet) {
               this.notifyComponent.addTextNotification("\uf06a", Text.of("Ð¨Ð»ÐµÐ¼ ÑÐºÐ¾Ñ€Ð¾ ÑÐ»Ð¾Ð¼Ð°ÐµÑ‚ÑÑ"));
               this.notifedHelmet = true;
            }
         } else {
            this.notifedHelmet = false;
         }
         if (!MinecraftClient.getInstance().player.getInventory().getArmorStack(2).isEmpty() && Math.abs(MinecraftClient.getInstance().player.getInventory().getArmorStack(2).getDamage() - MinecraftClient.getInstance().player.getInventory().getArmorStack(2).getMaxDamage()) < MinecraftClient.getInstance().player.getInventory().getArmorStack(2).getMaxDamage() / 5) {
            if (!this.notifedChestplate) {
               this.notifyComponent.addTextNotification("\uf06a", Text.of(" ÐÐ°Ð³Ñ€ÑƒÐ´Ð½Ð¸Ðº ÑÐºÐ¾Ñ€Ð¾ ÑÐ»Ð¾Ð¼Ð°ÐµÑ‚ÑÑ"));
               this.notifedChestplate = true;
            }
         } else {
            this.notifedChestplate = false;
         }
         if (!MinecraftClient.getInstance().player.getInventory().getArmorStack(1).isEmpty() && Math.abs(MinecraftClient.getInstance().player.getInventory().getArmorStack(1).getDamage() - MinecraftClient.getInstance().player.getInventory().getArmorStack(1).getMaxDamage()) < MinecraftClient.getInstance().player.getInventory().getArmorStack(1).getMaxDamage() / 5) {
            if (!this.notifedLeggings) {
               this.notifyComponent.addTextNotification("\uf06a", Text.of("ÐŸÐ¾Ð½Ð¾Ð¶Ð¸ ÑÐºÐ¾Ñ€Ð¾ ÑÐ»Ð¾Ð¼Ð°ÑŽÑ‚ÑÑ"));
               this.notifedLeggings = true;
            }
         } else {
            this.notifedLeggings = false;
         }
         if (!MinecraftClient.getInstance().player.getInventory().getArmorStack(0).isEmpty() && Math.abs(MinecraftClient.getInstance().player.getInventory().getArmorStack(0).getDamage() - MinecraftClient.getInstance().player.getInventory().getArmorStack(0).getMaxDamage()) < MinecraftClient.getInstance().player.getInventory().getArmorStack(0).getMaxDamage() / 5) {
            if (!this.notifedBoots) {
               this.notifyComponent.addTextNotification("\uf06a", Text.of("Ð‘Ð¾Ñ‚Ð¸Ð½ÐºÐ¸ ÑÐºÐ¾Ñ€Ð¾ ÑÐ»Ð¾Ð¼Ð°ÑŽÑ‚ÑÑ"));
               this.notifedBoots = true;
            }
         } else {
            this.notifedBoots = false;
         }
      }
   }
   @EventTarget
   @Native
   private void onPacket(EventPacket e) {
      if (this.notifyComponent != null && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
         Packet var3;
         if (e.isSent()) {
            var3 = e.getPacket();
            if (var3 instanceof RequestCommandCompletionsC2SPacket) {
               RequestCommandCompletionsC2SPacket packet = (RequestCommandCompletionsC2SPacket)var3;
               if (MinecraftClient.getInstance().getCurrentServerEntry() != null && MinecraftClient.getInstance().getCurrentServerEntry().address.contains("legendsgrief")) {
                  NetworkUtils.sendSilentPacket(new RequestCommandCompletionsC2SPacket(packet.getCompletionId(), packet.getPartialCommand().replace("[", "").replace("]", "")));
                  e.cancel();
               }
            }
         }
         var3 = e.getPacket();
         if (var3 instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket message = (GameMessageS2CPacket)var3;
            Iterator var7 = MinecraftClient.getInstance().world.getPlayers().iterator();
            label70:
            while(true) {
               PlayerEntity player;
               do {
                  do {
                     if (!var7.hasNext()) {
                        break label70;
                     }
                     player = (PlayerEntity)var7.next();
                  } while(!message.content().getString().contains(player.getNameForScoreboard()));
               } while(!message.content().getString().contains("ÑÐ¿ÐµÐº") && !message.content().getString().contains("ÑÐ¿Ðµk") && !message.content().getString().contains("spec") && !message.content().getString().contains("spek") && !message.content().getString().contains("911"));
               this.notifyComponent.addTextNotification("\uf06a", Text.of("Ð˜Ð³Ñ€Ð¾Ðº %s Ð¿Ñ€Ð¾ÑÐ¸Ñ‚ Ð¾ Ð½Ð°Ð±Ð»ÑŽÐ´ÐµÐ½Ð¸Ð¸".formatted(new Object[]{player.getNameForScoreboard()})));
            }
         }
         var3 = e.getPacket();
         if (var3 instanceof EntityStatusS2CPacket) {
            EntityStatusS2CPacket status = (EntityStatusS2CPacket)var3;
            if (status.getStatus() == 35) {
               Entity var8 = status.getEntity(MinecraftClient.getInstance().world);
               if (!(var8 instanceof PlayerEntity)) {
                  return;
               }
               PlayerEntity player = (PlayerEntity)var8;
               if (player == MinecraftClient.getInstance().player) {
                  return;
               }
               this.notifyComponent.addTotemNotification(player.getNameForScoreboard(), player.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING ? player.getMainHandStack().hasEnchantments() : player.getOffHandStack().hasEnchantments());
            }
         }
      }
   }
   @EventTarget
   @Native
   private void onPickup(EventPickupItem e) {
      if (this.notifyComponent != null && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
         if (e.getEntity() == MinecraftClient.getInstance().player && (!e.getItemStack().getName().getSiblings().isEmpty() || e.getItemStack().getItem() == Items.ELYTRA)) {
            this.notifyComponent.addTextNotification("\uf05a", Text.of("ÐŸÐ¾Ð´Ð¾Ð±Ñ€Ð°Ð½ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚").copy().append(e.getItemStack().getName()));
         }
      }
   }
   @EventTarget
   @Native
   public void onModuleToggle(EventModuleToggle event) {
      if (this.notifyComponent != null) {
         this.notifyComponent.addNotification(event.getModule(), event.isEnabled());
      }
   }
   public void addNotification(Module module, boolean enabled) {
      if (this.notifyComponent != null) {
         this.notifyComponent.addNotification(module, enabled);
      }
   }
   public void addNotification(String icon, Text module) {
      if (this.notifyComponent != null) {
         this.notifyComponent.addTextNotification(icon, module);
      }
   }
}



