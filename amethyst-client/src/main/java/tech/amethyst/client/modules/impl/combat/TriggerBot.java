package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import tech.amethyst.Amethyst;
import tech.amethyst.base.events.impl.other.EventTick;
import tech.amethyst.base.player.AttackUtil;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.utility.math.Timer;
@ModuleAnnotation(
   name = "TriggerBot",
   category = Category.COMBAT,
   description = "Automatically attacks entities you look at"
)
public final class TriggerBot extends Module {
   public static final TriggerBot INSTANCE = new TriggerBot();
   private final NumberSetting range = new NumberSetting("Range", 3.0F, 1.0F, 6.0F, 0.1F, "Attack range");
   private final NumberSetting minCPS = new NumberSetting("Min CPS", 8.0F, 1.0F, 20.0F, 1.0F);
   private final NumberSetting maxCPS = new NumberSetting("Max CPS", 12.0F, 1.0F, 20.0F, 1.0F);
   private final BooleanSetting playersOnly = new BooleanSetting("Players Only", true);
   private final BooleanSetting weaponOnly = new BooleanSetting("Weapon Only", false);
   private final BooleanSetting smartDelay = new BooleanSetting("Smart Delay", true);
   private final BooleanSetting antiFriend = new BooleanSetting("Anti Friend", true);
   private final Timer attackTimer = new Timer();
   private long currentDelay = 0;
   private TriggerBot() {
   }
   private void randomizeDelay() {
      float min = Math.min(minCPS.getCurrent(), maxCPS.getCurrent());
      float max = Math.max(minCPS.getCurrent(), maxCPS.getCurrent());
      float cps = min + (float)(Math.random() * (max - min));
      currentDelay = (long)(1000.0F / cps);
   }
   @EventTarget
   public void onUpdate(EventTick event) {
      if (mc.player == null || mc.world == null) return;
      if (mc.currentScreen != null) return;
      if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
         EntityHitResult entityHitResult = (EntityHitResult) mc.crosshairTarget;
         Entity entity = entityHitResult.getEntity();
         if (!(entity instanceof LivingEntity livingEntity)) return;
         if (!livingEntity.isAlive() || livingEntity.getHealth() <= 0) return;
         if (playersOnly.isEnabled() && !(entity instanceof PlayerEntity)) return;
         if (antiFriend.isEnabled() && entity instanceof PlayerEntity) {
            if (Amethyst.getInstance().getFriendManager().isFriend(entity.getName().getString())) return;
         }
         if (mc.player.distanceTo(entity) > range.getCurrent()) return;
         if (smartDelay.isEnabled()) {
            if (mc.player.getAttackCooldownProgress(0.5F) < 0.9F) return;
            mc.interactionManager.attackEntity(mc.player, livingEntity);
            mc.player.swingHand(Hand.MAIN_HAND);
            mc.player.resetLastAttackedTicks();
         } else {
            if (!attackTimer.finished(currentDelay)) return;
            mc.interactionManager.attackEntity(mc.player, livingEntity);
            mc.player.swingHand(Hand.MAIN_HAND);
            mc.player.resetLastAttackedTicks();
            randomizeDelay();
            attackTimer.reset();
         }
      }
   }
   public void onEnable() {
      randomizeDelay();
      super.onEnable();
   }
}



