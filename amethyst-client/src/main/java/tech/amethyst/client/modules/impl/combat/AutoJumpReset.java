package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import tech.amethyst.base.events.impl.other.EventTick;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
@ModuleAnnotation(
   name = "AutoJumpReset",
   category = Category.COMBAT,
   description = "Jumps automatically when taking damage to reduce knockback"
)
public final class AutoJumpReset extends Module {
   public static final AutoJumpReset INSTANCE = new AutoJumpReset();
   private final NumberSetting chance = new NumberSetting("Chance", 100.0F, 0.0F, 100.0F, 1.0F);
   private final BooleanSetting onlyWhileMoving = new BooleanSetting("Only Moving", true);
   private AutoJumpReset() {
   }
   @EventTarget
   public void onUpdate(EventTick event) {
      if (mc.player == null) return;
      if (onlyWhileMoving.isEnabled() && mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) return;
      if (mc.player.hurtTime == mc.player.maxHurtTime && mc.player.hurtTime > 0) {
         if (mc.player.isOnGround()) {
            if (Math.random() * 100.0D <= (double)chance.getCurrent()) {
                mc.player.jump();
            }
         }
      }
   }
}



