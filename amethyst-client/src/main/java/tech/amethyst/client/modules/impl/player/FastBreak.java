package tech.amethyst.client.modules.impl.player;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
@ModuleAnnotation(
   name = "FastBreak",
   category = Category.PLAYER,
   description = "Speeds up block mining"
)
public final class FastBreak extends Module {
   public static final FastBreak INSTANCE = new FastBreak();
   public final BooleanSetting speedMine = new BooleanSetting("Speed Mine", false);
   private FastBreak() {
   }
   @EventTarget
   @Native
   public void onUpdate(EventUpdate event) {
      if (mc.player != null) {
         if (this.speedMine.isEnabled()) {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 1, false, false));
         } else {
            mc.player.removeStatusEffect(StatusEffects.HASTE);
         }
      }
   }
   public void onDisable() {
      super.onDisable();
      if (mc.player != null) {
         mc.player.removeStatusEffect(StatusEffects.HASTE);
      }
   }
}



