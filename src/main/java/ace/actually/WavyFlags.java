package ace.actually;

import ace.actually.blocks.FlagBlock;
import ace.actually.blocks.FlagBlockEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WavyFlags implements ModInitializer {
	public static final String MOD_ID = "wavyflags";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final FlagBlock FLAG_BLOCK = new FlagBlock(AbstractBlock.Settings.create());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registries.BLOCK, Identifier.of("wavyflags","flag_block"),FLAG_BLOCK);
		LOGGER.info("Hello Fabric world!");
	}

	public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("tutorial", path), blockEntityType);
	}

	public static final BlockEntityType<FlagBlockEntity> FLAG_BLOCK_ENTITY = register(
			"flag_block_entity",
			FabricBlockEntityTypeBuilder.create(FlagBlockEntity::new, FLAG_BLOCK).build()
	);
}