package tech.amethyst.client.modules.impl.misc;
import lombok.Generated;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.screen.slot.Slot;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.ColorSetting;
import tech.amethyst.utility.render.display.base.color.ColorRGBA;
@ModuleAnnotation(
   name = "AH Helper",
   category = Category.MISC,
   description = "Helper for finding cheap items"
)
public final class AHHelper extends Module {
   public static final AHHelper INSTANCE = new AHHelper();
   private final ColorSetting cheapSlotColor = new ColorSetting("Ð¦Ð²ÐµÑ‚ Ð´ÐµÑˆÐµÐ²Ð¾Ð³Ð¾", new ColorRGBA(64, 255, 64, 140));
   private final ColorSetting goodSlotColor = new ColorSetting("Ð¦Ð²ÐµÑ‚ Ð²Ñ‹Ð³Ð¾Ð´Ð½Ð¾Ð³Ð¾", new ColorRGBA(255, 255, 64, 140));
   private AHHelper() {
   }
   @Native
   public void renderCheat(DrawContext context, Slot slot) {
      context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, this.cheapSlotColor.getIntColor());
   }
   @Native
   public void renderGood(DrawContext context, Slot slot) {
      context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, this.goodSlotColor.getIntColor());
   }
   @Generated
   public ColorSetting getCheapSlotColor() {
      return this.cheapSlotColor;
   }
   @Generated
   public ColorSetting getGoodSlotColor() {
      return this.goodSlotColor;
   }
}



