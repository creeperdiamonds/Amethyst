package tech.amethyst.utility.game;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import tech.amethyst.Amethyst;
import tech.amethyst.utility.interfaces.IClient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class PlayerUtils implements IClient {
    public static LivingEntity getNearestTarget(float range) {
        if (mc.world == null || mc.player == null) return null;
        List<LivingEntity> targets = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity living && isValid(living, range)) {
                targets.add(living);
            }
        }
        targets.sort(Comparator.comparingDouble(e -> mc.player.distanceTo(e)));
        return targets.isEmpty() ? null : targets.get(0);
    }
    public static boolean isValid(LivingEntity entity, float range) {
        if (entity == mc.player) return false;
        if (!entity.isAlive() || entity.getHealth() <= 0) return false;
        if (mc.player.distanceTo(entity) > range) return false;
        if (entity instanceof PlayerEntity player) {
            if (Amethyst.getInstance().getFriendManager().isFriend(player.getName().getString())) {
                return false;
            }
        }
        return true;
    }
}



