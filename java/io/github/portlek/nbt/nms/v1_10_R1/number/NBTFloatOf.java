package io.github.portlek.nbt.nms.v1_10_R1.number;

import io.github.portlek.nbt.nms.v1_10_R1.NBTNumberEnvelope;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import org.jetbrains.annotations.NotNull;

public class NBTFloatOf extends NBTNumberEnvelope<NBTTagFloat> {

    public NBTFloatOf(@NotNull final NBTTagFloat nbt) {
        super(nbt);
    }

}
