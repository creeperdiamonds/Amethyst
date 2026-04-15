package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import java.util.Comparator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.base.events.impl.input.EventKey;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.KeySetting;
import tech.amethyst.client.modules.api.setting.impl.ModeSetting;
import tech.amethyst.client.modules.impl.movement.AutoSprint;
import tech.amethyst.utility.game.player.PlayerInventoryComponent;
import tech.amethyst.utility.game.player.PlayerInventoryUtil;
@ModuleAnnotation(
   name = "AutoSwap",
   category = Category.COMBAT,
   description = "Ð¡Ð²Ð°Ð¿ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚Ð¾Ð² Ð¿Ð¾ Ð±Ð¸Ð½Ð´Ñƒ"
)
public final class AutoSwap extends Module {
   public static final AutoSwap INSTANCE = new AutoSwap();
   private final ModeSetting itemType = new ModeSetting("ÐŸÑ€ÐµÐ´Ð¼ÐµÑ‚", new String[]{"Ð©Ð¸Ñ‚", "Ð“ÐµÐ¿Ð»Ñ‹", "Ð¢Ð¾Ñ‚ÐµÐ¼", "Ð¨Ð°Ñ€"});
   private final ModeSetting swapType = new ModeSetting("Ð¡Ð²Ð°Ð¿Ð°Ñ‚ÑŒ Ð½Ð°", new String[]{"Ð©Ð¸Ñ‚", "Ð“ÐµÐ¿Ð»Ñ‹", "Ð¢Ð¾Ñ‚ÐµÐ¼", "Ð¨Ð°Ñ€"});
   private final KeySetting keyToSwap = new KeySetting("ÐšÐ½Ð¾Ð¿ÐºÐ°", -1);
   private boolean swap;
   @EventTarget
   public void onKey(EventKey event) {
      if (mc.currentScreen == null) {
         if (event.getAction() == 1) {
            if (event.is(this.keyToSwap.getKeyCode())) {
               this.swap = true;
            }
         }
      }
   }
   @EventTarget
   @Native
   public void onTick(EventUpdate event) {
      if (this.swap) {
         Slot first = PlayerInventoryUtil.getSlot(this.getItemByType(this.itemType.get()), Comparator.comparing((s) -> {
            return s.getStack().hasEnchantments();
         }), (s) -> {
            return s.id != 46 && s.id != 45 && s.id != 5 && s.id != 6 && s.id != 7 && s.id != 8;
         });
         Slot second = PlayerInventoryUtil.getSlot(this.getItemByType(this.swapType.get()), Comparator.comparing((s) -> {
            return s.getStack().hasEnchantments();
         }), (s) -> {
            return s.id != 46 && s.id != 45 && s.id != 5 && s.id != 6 && s.id != 7 && s.id != 8;
         });
         Slot validSlot = first != null && mc.player.getOffHandStack().getItem() != first.getStack().getItem() ? first : second;
         PlayerInventoryComponent.addTask(() -> {
            if (mc.player.isSprinting()) {
               mc.player.setSprinting(false);
               mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
               if (!AutoSprint.INSTANCE.isEnabled()) {
                  mc.options.sprintKey.setPressed(false);
               }
            }
            PlayerInventoryUtil.swapHand(validSlot, Hand.OFF_HAND, false);
            PlayerInventoryUtil.closeScreen(true);
         });
         this.swap = false;
      }
   }
   @Native
   private Item getItemByType(String itemType) {
      byte var3 = -1;
      switch(itemType.hashCode()) {
      case 1056824:
         if (itemType.equals("Ð¨Ð°Ñ€")) {
            var3 = 3;
         }
         break;
      case 1058035:
         if (itemType.equals("Ð©Ð¸Ñ‚")) {
            var3 = 0;
         }
         break;
      case 996396589:
         if (itemType.equals("Ð“ÐµÐ¿Ð»Ñ‹")) {
            var3 = 2;
         }
         break;
      case 1010520205:
         if (itemType.equals("Ð¢Ð¾Ñ‚ÐµÐ¼")) {
            var3 = 1;
         }
      }
      Item var10000;
      switch(var3) {
      case 0:
         var10000 = Items.SHIELD;
         break;
      case 1:
         var10000 = Items.TOTEM_OF_UNDYING;
         break;
      case 2:
         var10000 = Items.GOLDEN_APPLE;
         break;
      case 3:
         var10000 = Items.PLAYER_HEAD;
         break;
      default:
         var10000 = Items.AIR;
      }
      return var10000;
   }
}




