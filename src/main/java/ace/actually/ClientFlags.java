package ace.actually;

import ace.actually.blocks.FlagBER;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ClientFlags implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(WavyFlags.FLAG_BLOCK_ENTITY, FlagBER::new);
    }
}
