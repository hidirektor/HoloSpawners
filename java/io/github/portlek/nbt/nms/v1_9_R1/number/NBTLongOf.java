package io.github.portlek.nbt.nms.v1_9_R1.number;

import io.github.portlek.nbt.nms.v1_9_R1.NBTNumberEnvelope;
import net.minecraft.server.v1_9_R1.NBTTagLong;
import org.jetbrains.annotations.NotNull;

public class NBTLongOf extends NBTNumberEnvelope<NBTTagLong> {

    public NBTLongOf(@NotNull final NBTTagLong nbt) {
        super(nbt);
    }

}
