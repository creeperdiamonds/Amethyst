package tech.amethyst.client.modules.impl.render;
import com.darkmagician6.eventapi.EventTarget;
import java.util.List;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.base.events.impl.render.EventCamera;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.MultiBooleanSetting;
@ModuleAnnotation(
   name = "NoRender",
   category = Category.RENDER,
   description = "Removes unnecessary screen elements"
)
public final class NoRender extends Module {
   public static final NoRender INSTANCE = new NoRender();
   private final MultiBooleanSetting settings = MultiBooleanSetting.create("Ð£Ð±Ñ€Ð°Ñ‚ÑŒ", List.of("ÐžÐ³Ð¾Ð½ÑŒ", "ÐŸÐ»Ð¾Ñ…Ð¸Ðµ ÑÑ„Ñ„ÐµÐºÑ‚Ñ‹", "ÐšÐ°Ð¼ÐµÑ€Ð° ÐºÐ»Ð¸Ð¿"));
   @Native
   public boolean isRemoveFire() {
      return this.isEnabled() && this.settings.isEnable(0);
   }
   @Native
   public boolean isRemoveBadEffect() {
      return this.isEnabled() && this.settings.isEnable(1);
   }
   @EventTarget
   @Native
   private void onCamera(EventCamera e) {
      e.setCameraClip(this.settings.isEnable("ÐšÐ°Ð¼ÐµÑ€Ð° ÐºÐ»Ð¸Ð¿"));
      e.cancel();
   }
}



