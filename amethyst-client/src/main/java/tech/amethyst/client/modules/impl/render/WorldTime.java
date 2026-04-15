package tech.amethyst.client.modules.impl.render;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
@ModuleAnnotation(
   name = "WorldTime",
   description = "Changes the time of day",
   category = Category.RENDER
)
public class WorldTime extends Module {
   public static final WorldTime INSTANCE = new WorldTime();
   public final NumberSetting timeSetting = new NumberSetting("Ð’Ñ€ÐµÐ¼Ñ", 12.0F, 0.0F, 24.0F, 1.0F);
}



