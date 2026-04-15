package tech.amethyst.client.modules.impl.render;
import com.darkmagician6.eventapi.EventTarget;
import tech.amethyst.Amethyst;
import tech.amethyst.base.events.impl.render.EventFog;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
@ModuleAnnotation(
   name = "CustomFog",
   category = Category.RENDER,
   description = "Changes fog rendering"
)
public class CustomFog extends Module {
   public static final CustomFog INSTANCE = new CustomFog();
   public final NumberSetting distanceSetting = new NumberSetting("Ð”Ð¸ÑÑ‚Ð°Ð½Ñ†Ð¸Ñ Ñ‚ÑƒÐ¼Ð°Ð½Ð°", 80.0F, 10.0F, 255.0F, 5.0F);
   @EventTarget
   public void onFog(EventFog e) {
      e.setDistance(this.distanceSetting.getCurrent());
      e.setColor(Amethyst.getInstance().getThemeManager().getCurrentTheme().getColor().getRGB());
      e.setCancelled(true);
   }
}



