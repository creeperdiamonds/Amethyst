package tech.amethyst.utility.game;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import tech.amethyst.utility.interfaces.IClient;
import java.util.ArrayList;
import java.util.List;
public class CrystalUtils implements IClient {
    public static List<EndCrystalEntity> getCrystals(float range) {
        List<EndCrystalEntity> crystals = new ArrayList<>();
        if (mc.world == null || mc.player == null) return crystals;
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof EndCrystalEntity crystal && mc.player.distanceTo(crystal) <= range) {
                crystals.add(crystal);
            }
        }
        return crystals;
    }
    public static List<BlockPos> getPlacePositions(float range) {
        List<BlockPos> positions = new ArrayList<>();
        if (mc.world == null || mc.player == null) return positions;
        BlockPos playerPos = mc.player.getBlockPos();
        int r = (int) Math.ceil(range);
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.player.getPos().distanceTo(Vec3d.ofCenter(pos)) > range) continue;
                    if (canPlaceCrystal(pos)) {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }
    public static boolean canPlaceCrystal(BlockPos pos) {
        if (mc.world == null) return false;
        if (!mc.world.getBlockState(pos).isOf(Blocks.OBSIDIAN) && !mc.world.getBlockState(pos).isOf(Blocks.BEDROCK)) return false;
        BlockPos up = pos.up();
        if (!mc.world.isAir(up)) return false;
        return mc.world.getEntitiesByClass(Entity.class, new net.minecraft.util.math.Box(up), e -> true).isEmpty();
    }
}



