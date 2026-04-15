package tech.amethyst.client.modules.impl.render;
import tech.amethyst.Amethyst;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "Menu",
   category = Category.RENDER,
   description = "Cheat menu"
)
public final class Menu extends Module {
   public static final Menu INSTANCE = new Menu();
   private Menu() {
      this.setKeyCode(344);
   }
   public void onEnable() {
      if (mc.world == null) {
         this.setEnabled(false);
      } else {
         if (mc.currentScreen != Amethyst.getInstance().getMenuScreen()) {
            mc.setScreen(Amethyst.getInstance().getMenuScreen());
            super.onEnable();
         }
      }
   }
   public void onDisable() {
      super.onDisable();
   }
   public void setKeyCode(int keyCode) {
      if (keyCode != -1) {
         super.setKeyCode(keyCode);
      }
   }
}



