package tech.amethyst.client.modules.impl.misc;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.base.events.impl.server.EventPacket;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "AutoAuth",
   category = Category.MISC,
   description = "Auto registration"
)
public final class AutoAuth extends Module {
   public static final AutoAuth INSTANCE = new AutoAuth();
   private AutoAuth() {
   }
   @EventTarget
   @Native
   public void onReceive(EventPacket event) {
      if (event.isReceive()) {
         Packet var3 = event.getPacket();
         if (var3 instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket chatMessagePacket = (GameMessageS2CPacket)var3;
            if (mc.getNetworkHandler() == null) {
               return;
            }
            String password = "123123qq";
            String content = chatMessagePacket.content().getString().toLowerCase();
            if (content.contains("Ð·Ð°Ñ€ÐµÐ³Ð¸ÑÑ‚Ñ€Ð¸Ñ€ÑƒÐ¹Ñ‚ÐµÑÑŒ") || content.contains("/register")) {
               mc.getNetworkHandler().sendChatCommand("register %s %s".formatted(new Object[]{password, password}));
            }
         }
      }
   }
}



