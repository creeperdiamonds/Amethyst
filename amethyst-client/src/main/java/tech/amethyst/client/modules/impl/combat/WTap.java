package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import tech.amethyst.base.events.impl.player.EventAttack;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.ModeSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.utility.math.Timer;
@ModuleAnnotation(
   name = "WTap",
   category = Category.COMBAT,
   description = "Resets sprint/movement on hit for more knockback"
)
public final class WTap extends Module {
   public static final WTap INSTANCE = new WTap();
   private final ModeSetting mode = new ModeSetting("Mode", "WTap", "STap", "Sneak", "Random");
   private final NumberSetting chance = new NumberSetting("Chance", 100.0F, 0.0F, 100.0F, 1.0F);
   private final NumberSetting delay = new NumberSetting("Delay", 50.0F, 0.0F, 200.0F, 5.0F);
   private final Timer timer = new Timer();
   private WTap() {
      this.setEnabled(false);
   }
   @EventTarget
   public void onAttack(EventAttack event) {
      if (Math.random() * 100.0D > (double)chance.getCurrent()) return;
      if (!timer.finished((long)delay.getCurrent())) return;
      String currentMode = mode.get();
      if (currentMode.equalsIgnoreCase("Random")) {
         String[] modes = {"WTap", "STap", "Sneak"};
         currentMode = modes[(int)(Math.random() * modes.length)];
      }
      switch (currentMode) {
         case "WTap":
            if (mc.player.isSprinting()) {
               mc.player.setSprinting(false);
               mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
               mc.player.setSprinting(true);
               mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            }
            break;
         case "STap":
            mc.options.backKey.setPressed(true);
            new Thread(() -> {
               try {
                  Thread.sleep(30L);
                  mc.options.backKey.setPressed(false);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }).start();
            break;
         case "Sneak":
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
            new Thread(() -> {
               try {
                  Thread.sleep(30L);
                  mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }).start();
            break;
      }
      timer.reset();
   }
}



