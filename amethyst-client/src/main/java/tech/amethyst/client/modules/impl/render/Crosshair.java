package tech.amethyst.client.modules.impl.render;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.hit.HitResult.Type;
import tech.amethyst.base.events.impl.render.EventHudRender;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.utility.render.display.base.CustomDrawContext;
import tech.amethyst.utility.render.display.base.color.ColorRGBA;
@ModuleAnnotation(
   name = "Crosshair",
   category = Category.RENDER,
   description = "Custom crosshair"
)
public final class Crosshair extends Module {
   public static final Crosshair INSTANCE = new Crosshair();
   private final NumberSetting thickness = new NumberSetting("Ð¢Ð¾Ð»Ñ‰Ð¸Ð½Ð°", 1.0F, 0.5F, 3.0F, 0.1F);
   private final NumberSetting length = new NumberSetting("Ð”Ð»Ð¸Ð½Ð°", 3.0F, 1.0F, 8.0F, 0.5F);
   private final NumberSetting gap = new NumberSetting("Ð Ð°Ð·Ñ€Ñ‹Ð²", 2.0F, 0.0F, 5.0F, 0.5F);
   private final BooleanSetting dynamicGap = new BooleanSetting("Ð”Ð¸Ð½Ð°Ð¼Ð¸Ñ‡ÐµÑÐºÐ¸Ð¹ Ñ€Ð°Ð·Ñ€Ñ‹Ð²", false);
   private final BooleanSetting useEntityColor = new BooleanSetting("Ð¦Ð²ÐµÑ‚ Ð¿Ñ€Ð¸ Ð½Ð°Ð²ÐµÐ´ÐµÐ½Ð¸Ð¸", false);
   private final ColorRGBA entityColor = new ColorRGBA(255, 0, 0, 255);
   private Crosshair() {
   }
   @EventTarget
   public void onRender(EventHudRender event) {
      if (mc.player != null && mc.world != null) {
         if (mc.options.getPerspective() == Perspective.FIRST_PERSON) {
            CustomDrawContext ctx = event.getContext();
            float x = (float)mc.getWindow().getScaledWidth() / 2.0F;
            float y = (float)mc.getWindow().getScaledHeight() / 2.0F;
            float currentGap = this.gap.getCurrent();
            float currentThickness;
            if (this.dynamicGap.isEnabled()) {
               currentThickness = 1.0F - mc.player.getAttackCooldownProgress(0.0F);
               currentGap += 8.0F * currentThickness;
            }
            currentThickness = this.thickness.getCurrent();
            float currentLength = this.length.getCurrent();
            ColorRGBA color = this.useEntityColor.isEnabled() && mc.crosshairTarget != null && mc.crosshairTarget.getType() == Type.ENTITY ? this.entityColor : new ColorRGBA(255, 255, 255, 255);
            this.drawLine(ctx, x - currentThickness / 2.0F, y - currentGap - currentLength, currentThickness, currentLength, color);
            this.drawLine(ctx, x - currentThickness / 2.0F, y + currentGap, currentThickness, currentLength, color);
            this.drawLine(ctx, x - currentGap - currentLength, y - currentThickness / 2.0F, currentLength, currentThickness, color);
            this.drawLine(ctx, x + currentGap, y - currentThickness / 2.0F, currentLength, currentThickness, color);
         }
      }
   }
   private void drawLine(CustomDrawContext ctx, float x, float y, float width, float height, ColorRGBA color) {
      ctx.drawRect(x, y, width, height, color);
   }
}



