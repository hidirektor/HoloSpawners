package io.github.portlek.nbt.nms.v1_9_R2.number;

import io.github.portlek.nbt.nms.v1_9_R2.NBTNumberEnvelope;
import net.minecraft.server.v1_9_R2.NBTTagLong;
import org.jetbrains.annotations.NotNull;

public class NBTLongOf extends NBTNumberEnvelope<NBTTagLong> {

    public NBTLongOf(@NotNull final NBTTagLong nbt) {
        super(nbt);
    }

}
