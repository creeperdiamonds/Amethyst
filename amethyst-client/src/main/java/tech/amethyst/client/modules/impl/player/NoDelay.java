package tech.amethyst.client.modules.impl.player;
import com.darkmagician6.eventapi.EventTarget;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "NoJumpDelay",
   category = Category.PLAYER,
   description = "Removes jump delay"
)
public final class NoDelay extends Module {
   public static final NoDelay INSTANCE = new NoDelay();
   @EventTarget
   public void onUpdate(EventUpdate event) {
      if (mc.player != null && mc.world != null) {
         mc.player.jumpingCooldown = 0;
      }
   }
}



