package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import tech.amethyst.base.events.impl.player.EventAttack;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.utility.game.player.PlayerInventoryUtil;
import java.util.List;
@ModuleAnnotation(
   name = "ShieldBreaker",
   category = Category.COMBAT,
   description = "Automatically breaks shields with an axe"
)
public final class ShieldBreaker extends Module {
   public static final ShieldBreaker INSTANCE = new ShieldBreaker();
   private final BooleanSetting silent = new BooleanSetting("Silent", false);
   private final NumberSetting swapDelay = new NumberSetting("Swap Back Delay", 50.0F, 0.0F, 200.0F, 10.0F);
   private ShieldBreaker() {
      this.setEnabled(false);
   }
   @EventTarget
   public void onAttack(EventAttack event) {
      if (event.getTarget() instanceof PlayerEntity) {
         PlayerEntity target = (PlayerEntity) event.getTarget();
         if (target.isBlocking()) {
            int axeSlot = PlayerInventoryUtil.find(List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE), 0, 8);
            if (axeSlot != -1) {
               int oldSlot = mc.player.getInventory().selectedSlot;
               if (silent.isEnabled()) {
                  mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(axeSlot));
               } else {
                  mc.player.getInventory().selectedSlot = axeSlot;
               }
               mc.interactionManager.attackEntity(mc.player, target);
               mc.player.swingHand(Hand.MAIN_HAND);
               new Thread(() -> {
                  try {
                     Thread.sleep((long)swapDelay.getCurrent());
                     if (silent.isEnabled()) {
                        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(oldSlot));
                     } else {
                        mc.player.getInventory().selectedSlot = oldSlot;
                     }
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
               }).start();
            }
         }
      }
   }
}



