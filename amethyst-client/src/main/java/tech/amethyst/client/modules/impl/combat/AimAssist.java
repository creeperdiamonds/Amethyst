package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import tech.amethyst.Amethyst;
import tech.amethyst.base.events.impl.other.EventTick;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.utility.game.player.rotation.Rotation;
import net.minecraft.util.math.MathHelper;
@ModuleAnnotation(
   name = "AimAssist",
   category = Category.COMBAT,
   description = "Assists your aiming towards players"
)
public final class AimAssist extends Module {
   public static final AimAssist INSTANCE = new AimAssist();
   private final NumberSetting range = new NumberSetting("Range", 4.0F, 1.0F, 6.0F, 0.1F);
   private final NumberSetting speed = new NumberSetting("Speed", 5.0F, 1.0F, 20.0F, 0.5F);
   private final BooleanSetting onlyClicking = new BooleanSetting("Only Clicking", true);
   private final BooleanSetting weaponOnly = new BooleanSetting("Weapon Only", true);
   private final BooleanSetting invisibleCheck = new BooleanSetting("Invis Check", true);
   private AimAssist() {
   }
   @EventTarget
   public void onUpdate(EventTick event) {
      if (mc.player == null || mc.world == null) return;
      if (mc.currentScreen != null) return;
      if (onlyClicking.isEnabled() && !mc.options.attackKey.isPressed()) return;
      Entity target = null;
      double closest = range.getCurrent();
      for (Entity entity : mc.world.getEntities()) {
         if (entity instanceof PlayerEntity && entity != mc.player && entity.isAlive()) {
            if (invisibleCheck.isEnabled() && entity.isInvisible()) continue;
            if (Amethyst.getInstance().getFriendManager().isFriend(entity.getName().getString())) continue;
            double dist = mc.player.distanceTo(entity);
            if (dist < closest) {
               closest = dist;
               target = entity;
            }
         }
      }
      if (target != null) {
         Rotation targetRot = Rotation.getRotations(target.getBoundingBox().getCenter());
         float yawDist = MathHelper.wrapDegrees(targetRot.getYaw() - mc.player.getYaw());
         float pitchDist = targetRot.getPitch() - mc.player.getPitch();
         float step = speed.getCurrent();
         // Fix for rotation lock: only apply rotation if the distance is within limits
         if (Math.abs(yawDist) < 90.0F) {
            mc.player.setYaw(mc.player.getYaw() + yawDist / step);
            mc.player.setPitch(mc.player.getPitch() + pitchDist / step);
         }
      }
   }
}



