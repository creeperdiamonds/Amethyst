package tech.amethyst.utility.game;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import tech.amethyst.utility.interfaces.IClient;
public class BlockUtils implements IClient {
    public static boolean isBlock(Block block, BlockPos pos) {
        if (mc.world == null) return false;
        return mc.world.getBlockState(pos).isOf(block);
    }
}



