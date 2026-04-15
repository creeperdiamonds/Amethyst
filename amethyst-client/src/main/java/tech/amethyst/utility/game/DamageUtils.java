package tech.amethyst.utility.game;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import tech.amethyst.utility.interfaces.IClient;
import java.util.Objects;
public class DamageUtils implements IClient {
    public static double crystalDamage(LivingEntity player, Vec3d crystal, boolean predictMovement, BlockPos obsidianPos, boolean ignoreTerrain) {
        if (mc.world == null || player == null || (player instanceof PlayerEntity pe && pe.isCreative())) return 0;
        Vec3d v = player.getPos();
        if (predictMovement) v = v.add(player.getVelocity());
        double modDistance = Math.sqrt(v.squaredDistanceTo(crystal));
        if (modDistance > 12) return 0;
        double exposure = getExposure(crystal, player, predictMovement, obsidianPos, ignoreTerrain);
        double impact = (1 - (modDistance / 12)) * exposure;
        double damage = ((impact * impact + impact) / 2 * 7 * (6 * 2) + 1);
        damage = getDamageForDifficulty(damage);
        float armor = (float) player.getArmor();
        float toughness = (float) Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS)).getValue();
        // damage = DamageUtil.getInflictedDamage((float) damage, armor, toughness);
        damage = resistanceReduction(player, damage);
        damage = protectionReduction(player, (float) damage, mc.world.getDamageSources().generic());
        return damage < 0 ? 0 : damage;
    }
    private static double getDamageForDifficulty(double damage) {
        if (mc.world == null) return damage;
        return switch (mc.world.getDifficulty()) {
            case PEACEFUL -> 0;
            case EASY -> Math.min(damage / 2 + 1, damage);
            case HARD -> damage * 3 / 2;
            default -> damage;
        };
    }
    private static double resistanceReduction(LivingEntity player, double damage) {
        if (player.hasStatusEffect(StatusEffects.RESISTANCE)) {
            int lvl = Objects.requireNonNull(player.getStatusEffect(StatusEffects.RESISTANCE)).getAmplifier() + 1;
            damage *= (1 - (lvl * 0.2));
        }
        return Math.max(damage, 0);
    }
    private static float protectionReduction(LivingEntity player, float damage, DamageSource source) {
        return DamageUtil.getInflictedDamage(damage, 0);
    }
    private static double getExposure(Vec3d source, Entity entity, boolean predictMovement, BlockPos obsidianPos, boolean ignoreTerrain) {
        Box box = entity.getBoundingBox();
        if (predictMovement) box = box.offset(entity.getVelocity());
        double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
        if (d >= 0 && e >= 0 && f >= 0) {
            int i = 0, j = 0;
            for (double k = 0.0; k <= 1.0; k += d) {
                for (double l = 0.0; l <= 1.0; l += e) {
                    for (double m = 0.0; m <= 1.0; m += f) {
                        Vec3d pos = new Vec3d(MathHelper.lerp(k, box.minX, box.maxX) + g, MathHelper.lerp(l, box.minY, box.maxY), MathHelper.lerp(m, box.minZ, box.maxZ) + h);
                        RaycastContext ctx = new RaycastContext(pos, source, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity);
                        if (raycast(ctx, obsidianPos, ignoreTerrain).getType() == HitResult.Type.MISS) i++;
                        j++;
                    }
                }
            }
            return (double) i / j;
        }
        return 0;
    }
    private static BlockHitResult raycast(RaycastContext context, BlockPos obsidianPos, boolean ignoreTerrain) {
        return BlockView.raycast(context.getStart(), context.getEnd(), context, (ctx, pos) -> {
            BlockState state;
            if (pos.equals(obsidianPos)) state = Blocks.OBSIDIAN.getDefaultState();
            else {
                state = mc.world.getBlockState(pos);
                if (ignoreTerrain && state.getBlock().getBlastResistance() < 600) state = Blocks.AIR.getDefaultState();
            }
            VoxelShape shape = ctx.getBlockShape(state, mc.world, pos);
            return mc.world.raycastBlock(ctx.getStart(), ctx.getEnd(), pos, shape, state);
        }, (ctx) -> {
            Vec3d d = ctx.getStart().subtract(ctx.getEnd());
            return BlockHitResult.createMissed(ctx.getEnd(), Direction.getFacing(d.x, d.y, d.z), BlockPos.ofFloored(ctx.getEnd()));
        });
    }
}



