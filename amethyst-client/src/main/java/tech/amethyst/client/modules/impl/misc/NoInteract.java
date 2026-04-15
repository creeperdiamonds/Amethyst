package tech.amethyst.client.modules.impl.misc;
import tech.amethyst.Amethyst;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
@ModuleAnnotation(
   name = "NoInteract",
   category = Category.MISC,
   description = "Prevents opening containers"
)
public final class NoInteract extends Module {
   private final BooleanSetting onlyOnPvP = new BooleanSetting("Ð¢Ð¾Ð»ÑŒÐºÐ¾ Ð² PvP", false);
   public static final NoInteract INSTANCE = new NoInteract();
   public boolean needToWork() {
      return !this.onlyOnPvP.isEnabled() || Amethyst.getInstance().getServerHandler().isPvp();
   }
}



