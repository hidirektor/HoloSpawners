package io.github.portlek.nbt.nms.v1_9_R2.number;

import io.github.portlek.nbt.nms.v1_9_R2.NBTNumberEnvelope;
import net.minecraft.server.v1_9_R2.NBTTagByte;
import org.jetbrains.annotations.NotNull;

public class NBTByteOf extends NBTNumberEnvelope<NBTTagByte> {

    public NBTByteOf(@NotNull final NBTTagByte nbt) {
        super(nbt);
    }

}
