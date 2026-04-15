package tech.amethyst.client.modules.impl.misc;
import com.darkmagician6.eventapi.EventTarget;
import tech.amethyst.base.events.impl.input.EventMouse;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "ItemScroller",
   description = "Move items without delay",
   category = Category.MISC
)
public final class ItemScroller extends Module {
   public static final ItemScroller INSTANCE = new ItemScroller();
   public boolean mouseHold;
   @EventTarget
   private void onMouse(EventMouse e) {
      if (e.getButton() == 0 && e.getAction() == 1) {
         this.mouseHold = true;
      }
      if (this.mouseHold && e.getAction() == 0) {
         this.mouseHold = false;
      }
   }
}



