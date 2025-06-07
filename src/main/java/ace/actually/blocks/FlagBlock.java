package ace.actually.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlagBlock extends BlockWithEntity {
    public FlagBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FlagBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand==Hand.MAIN_HAND)
        {
            FlagBlockEntity be = (FlagBlockEntity) world.getBlockEntity(pos);
            if(player.getStackInHand(hand).isEmpty())
            {
                NbtList list = new NbtList();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        for (int k = -5; k < 5; k++) {
                            if(world.getBlockState(pos.add(i,j,k)).isIn(BlockTags.WOOL))
                            {
                                NbtCompound block = new NbtCompound();
                                block.putIntArray("pos",new int[]{i,j,k});
                                block.putString("id", Registries.BLOCK.getId(world.getBlockState(pos.add(i,j,k)).getBlock()).toString());
                                list.add(block);
                            }
                        }
                    }
                }

                be.setList(list);
                world.updateListeners(pos, state, state, 0);
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
