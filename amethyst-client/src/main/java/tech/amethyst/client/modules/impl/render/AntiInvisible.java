package tech.amethyst.client.modules.impl.render;
import com.darkmagician6.eventapi.EventTarget;
import lombok.Generated;
import tech.amethyst.base.events.impl.entity.EventEntityColor;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.ColorSetting;
import tech.amethyst.utility.render.display.base.color.ColorRGBA;
@ModuleAnnotation(
   name = "Anti Invisible",
   category = Category.RENDER,
   description = "See invisible players"
)
public final class AntiInvisible extends Module {
   public static final AntiInvisible INSTANCE = new AntiInvisible();
   private final ColorSetting colorSetting;
   private AntiInvisible() {
      this.colorSetting = new ColorSetting("Ð¦Ð²ÐµÑ‚", ColorRGBA.WHITE.mulAlpha(0.5F));
   }
   @EventTarget
   public void onEntityColor(EventEntityColor e) {
      e.setColor(this.colorSetting.getColor().getRGB());
      e.cancel();
   }
   @Generated
   public ColorSetting getColorSetting() {
      return this.colorSetting;
   }
}



