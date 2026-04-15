package tech.amethyst.utility.game;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tech.amethyst.utility.interfaces.IClient;
public class InvUtils implements IClient {
    public static void selectItemFromHotbar(Item item) {
        if (mc.player == null) return;
        int slot = findItemInHotbar(item);
        if (slot != -1) {
            mc.player.getInventory().selectedSlot = slot;
        }
    }
    public static int findItemInHotbar(Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    public static void setInvSlot(int slot) {
        if (mc.player == null) return;
        if (slot >= 0 && slot < 9) {
            mc.player.getInventory().selectedSlot = slot;
        }
    }
}



