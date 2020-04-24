package io.github.portlek.nbt.nms.v1_9_R2.number;

import io.github.portlek.nbt.nms.v1_9_R2.NBTNumberEnvelope;
import net.minecraft.server.v1_9_R2.NBTTagFloat;
import org.jetbrains.annotations.NotNull;

public class NBTFloatOf extends NBTNumberEnvelope<NBTTagFloat> {

    public NBTFloatOf(@NotNull final NBTTagFloat nbt) {
        super(nbt);
    }

}
