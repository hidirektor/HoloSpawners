package io.github.portlek.nbt.nms.v1_8_R3.number;

import io.github.portlek.nbt.nms.v1_8_R3.NBTNumberEnvelope;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import org.jetbrains.annotations.NotNull;

public class NBTLongOf extends NBTNumberEnvelope<NBTTagLong> {

    public NBTLongOf(@NotNull final NBTTagLong nbt) {
        super(nbt);
    }

}
