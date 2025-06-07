package ace.actually.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

public class FlagBER implements BlockEntityRenderer<FlagBlockEntity> {

    public static double SLOWNESS = 8.0;
    public static double ECCENTRICITY = 5;

    public FlagBER(BlockEntityRendererFactory.Context ctx)
    {

    }

    @Override
    public void render(FlagBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        matrices.push();
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getWorld().getBlockState(entity.getPos().down()), BlockPos.ORIGIN,entity.getWorld(),matrices,vertexConsumers.getBuffer(RenderLayer.getCutout()),true,entity.getWorld().random);
        matrices.pop();
        for (int i = 0; i < entity.list.size(); i++) {

            NbtCompound block = entity.list.getCompound(i);

            BlockState state = Registries.BLOCK.get(Identifier.tryParse(block.getString("id"))).getDefaultState();
            int[] move = block.getIntArray("pos");

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta + move[0]*entity.eccentricity) / entity.slowness) / entity.slowness;


            matrices.push();
            matrices.translate(entity.offset[0],entity.offset[1],entity.offset[2]);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.rotation));
            matrices.translate(move[0], move[1], (move[2]*0.75+offset*move[0]));
            MinecraftClient.getInstance().getBlockRenderManager().renderBlock(state, BlockPos.ORIGIN,entity.getWorld(),matrices,vertexConsumers.getBuffer(RenderLayer.getCutout()),true,entity.getWorld().random);
            matrices.pop();
        }

    }
}
