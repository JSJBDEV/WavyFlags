package ace.actually.items;

import ace.actually.WavyFlags;
import ace.actually.blocks.FlagBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WrenchItem extends Item {

    int mode;
    public WrenchItem(int mode) {
        super(new Settings());
        this.mode=mode;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient && context.getHand()== Hand.MAIN_HAND && context.getWorld().getBlockState(context.getBlockPos()).isOf(WavyFlags.FLAG_BLOCK))
        {
            NbtCompound compound = context.getStack().getOrCreateNbt();
            BlockPos b = context.getBlockPos();
            compound.putIntArray("pos",new int[]{b.getX(),b.getY(),b.getZ()});
            ItemStack stack = context.getStack();
            stack.setNbt(compound);
            context.getPlayer().setStackInHand(Hand.MAIN_HAND,stack);
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && hand==Hand.MAIN_HAND)
        {
            if(user.getStackInHand(hand).hasNbt())
            {
                int[] v = user.getStackInHand(hand).getNbt().getIntArray("pos");
                FlagBlockEntity be = (FlagBlockEntity) world.getBlockEntity(new BlockPos(v[0],v[1],v[2]));

                switch (mode)
                {
                    case 0 ->
                    {
                        if(user.isSneaking())
                        {
                            be.setRotation(be.getRotation()+1);
                        }
                        else
                        {
                            be.setRotation(be.getRotation()-1);
                        }
                    }
                    case 1 ->
                    {
                        if(user.isSneaking())
                        {
                            be.setSlowness(be.getSlowness()+1);
                        }
                        else
                        {
                            be.setSlowness(be.getSlowness()-1);
                        }
                    }
                    case 2 ->
                    {
                        if(user.isSneaking())
                        {
                            be.setEccentricity(be.getEccentricity()+1);
                        }
                        else
                        {
                            be.setEccentricity(be.getEccentricity()-1);
                        }
                    }
                    case 3 ->
                    {
                         Vec3i v3i = user.getHorizontalFacing().getVector();
                         int[] z = be.getOffset();
                         z[0]+=v3i.getX();
                         z[1]+=v3i.getY();
                         z[2]+=v3i.getZ();
                         be.setOffset(z);

                    }
                }
                world.updateListeners(be.getPos(),be.getCachedState(),be.getCachedState(),0);

            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if(stack.hasNbt())
        {
            int[] v = stack.getNbt().getIntArray("pos");
            tooltip.add(Text.translatable("wf.wrench.text"));
            tooltip.add(Text.of(v[0]+" "+v[1]+" "+v[2]));
        }

    }
}
