package ace.actually.blocks;

import ace.actually.WavyFlags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {
    NbtList list = new NbtList();
    double slowness = 8;
    double eccentricity = 5;
    int rotation = 0;
    int[] offset = new int[]{0,0,0};
    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(WavyFlags.FLAG_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("list",list);
        nbt.putDouble("slowness",slowness);
        nbt.putDouble("eccentricity",eccentricity);
        nbt.putInt("rotation",rotation);
        nbt.putIntArray("offset",offset);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        list=nbt.getList("list", NbtElement.COMPOUND_TYPE);
        slowness=nbt.getDouble("slowness");
        eccentricity=nbt.getDouble("eccentricity");
        rotation=nbt.getInt("rotation");
        offset=nbt.getIntArray("offset");
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void setList(NbtList list) {
        this.list = list;
        markDirty();
    }

    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
        markDirty();
    }

    public void setSlowness(double slowness) {
        this.slowness = slowness;
        markDirty();
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        markDirty();
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public double getSlowness() {
        return slowness;
    }

    public int getRotation() {
        return rotation;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
        markDirty();
    }

    public int[] getOffset() {
        return offset;
    }
}
