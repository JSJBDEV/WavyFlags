package ace.actually;

import ace.actually.blocks.FlagBlock;
import ace.actually.blocks.FlagBlockEntity;
import ace.actually.items.WrenchItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
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
	public static final WrenchItem ROT = new WrenchItem(0);
	public static final WrenchItem SLOW = new WrenchItem(1);
	public static final WrenchItem ECC = new WrenchItem(2);
	public static final WrenchItem OFF = new WrenchItem(3);

	public static final ItemGroup TAB = FabricItemGroup.builder()
			.icon(() -> new ItemStack(WavyFlags.FLAG_BLOCK))
			.displayName(Text.translatable("itemGroup.wavyflags"))
			.entries((context, entries) -> {
				entries.add(WavyFlags.FLAG_BLOCK);
				entries.add(WavyFlags.ROT);
				entries.add(WavyFlags.SLOW);
				entries.add(WavyFlags.ECC);
				entries.add(WavyFlags.OFF);
			})
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registries.BLOCK, Identifier.of("wavyflags","flag_block"),FLAG_BLOCK);

		Registry.register(Registries.ITEM,Identifier.of("wavyflags","rot"),ROT);
		Registry.register(Registries.ITEM,Identifier.of("wavyflags","slow"),SLOW);
		Registry.register(Registries.ITEM,Identifier.of("wavyflags","ecc"),ECC);
		Registry.register(Registries.ITEM,Identifier.of("wavyflags","off"),OFF);
		Registry.register(Registries.ITEM,Identifier.of("wavyflags","flag_block"),new BlockItem(FLAG_BLOCK,new Item.Settings()));
		Registry.register(Registries.ITEM_GROUP,Identifier.of("wavyflags","tab"), TAB);
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