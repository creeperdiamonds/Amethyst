package tech.amethyst.base.repository;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.base.events.impl.server.EventPacket;
import tech.amethyst.base.notify.NotifyManager;
import tech.amethyst.utility.game.player.PlayerInventoryUtil;
import tech.amethyst.utility.game.server.ServerHandler;
import tech.amethyst.utility.interfaces.IClient;
import tech.amethyst.utility.math.StopWatch;
public class RCTRepository implements IClient {
   private final StopWatch stopWatch = new StopWatch();
   private boolean lobby;
   private int anarchy;
   public RCTRepository() {
      try {
         EventManager.register(this);
      } catch (Exception e) {
         System.err.println("[Javelin] Failed to register RCTRepository in EventManager: " + e.getMessage());
      }
   }
   @EventTarget
   @Native
   public void onPacket(EventPacket e) {
      if (this.anarchy != 0) {
         Packet var3 = e.getPacket();
         if (var3 instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket message = (GameMessageS2CPacket)var3;
            if (e.isReceive()) {
               String text = message.content().getString().toLowerCase();
               if (!text.contains("Ñ…Ð°Ð±") && text.contains("Ð½Ðµ ÑƒÐ´Ð°Ð»Ð¾ÑÑŒ")) {
                  NotifyManager var10000 = NotifyManager.getInstance();
                  String var10002 = String.valueOf(Formatting.RED);
                  var10000.addNotification("[RCT]ï¸", Text.literal(" ÐÐ° Ð´Ð°Ð½Ð½ÑƒÑŽ Ð°Ð½Ð°Ñ€Ñ…Ð¸ÑŽ " + var10002 + "Ð½ÐµÐ»ÑŒÐ·Ñ" + String.valueOf(Formatting.RESET) + " Ð·Ð°Ð¹Ñ‚Ð¸"));
                  this.anarchy = 0;
               }
            }
         }
      }
   }
   @EventTarget
   @Native
   public void onTick(EventUpdate e) {
      if (this.anarchy != 0) {
         ServerHandler serverHandler = Amethyst.getInstance().getServerHandler();
         if (!serverHandler.isHolyWorld()) {
            this.anarchy = 0;
         } else {
            int currentAnarchy = serverHandler.getAnarchy();
            if (this.lobby) {
               if (currentAnarchy == -1) {
                  this.lobby = false;
               } else {
                  mc.player.networkHandler.sendChatCommand("hub");
               }
            } else if (currentAnarchy == this.anarchy) {
               this.anarchy = 0;
            } else {
               Screen var5 = mc.currentScreen;
               if (var5 instanceof GenericContainerScreen) {
                  GenericContainerScreen screen = (GenericContainerScreen)var5;
                  if (screen.getTitle().getString().equals("Ð’Ñ‹Ð±Ð¾Ñ€ Ð›Ð°Ð¹Ñ‚ Ð°Ð½Ð°Ñ€Ñ…Ð¸Ð¸:")) {
                     boolean secondScreen = ((GenericContainerScreenHandler)screen.getScreenHandler()).getInventory().size() < 10;
                     int[] slots = this.anarchy < 15 ? new int[]{0, 0} : (this.anarchy < 33 ? new int[]{1, 14} : (this.anarchy < 48 ? new int[]{2, 32} : new int[]{3, 47}));
                     if (secondScreen) {
                        PlayerInventoryUtil.clickSlot(slots[0], 0, SlotActionType.PICKUP, false);
                     } else {
                        PlayerInventoryUtil.clickSlot(17 + this.anarchy - slots[1], 0, SlotActionType.PICKUP, false);
                     }
                     return;
                  }
               }
               if (this.stopWatch.every(500L)) {
                  mc.player.networkHandler.sendChatCommand("lite");
               }
            }
         }
      }
   }
   @Native
   public void reconnect(int anarchy) {
      if (anarchy > 0 && anarchy < 64) {
         this.anarchy = anarchy;
         this.lobby = true;
      } else {
         NotifyManager.getInstance().addNotification("[RCT]", Text.literal(" ÐÐµ Ð²ÐµÑ€Ð½Ñ‹Ð¹ " + String.valueOf(Formatting.RED) + "Ð»Ð°Ð¹Ñ‚"));
      }
   }
}



