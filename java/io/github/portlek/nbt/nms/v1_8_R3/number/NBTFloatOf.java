package io.github.portlek.nbt.nms.v1_8_R3.number;

import io.github.portlek.nbt.nms.v1_8_R3.NBTNumberEnvelope;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import org.jetbrains.annotations.NotNull;

public class NBTFloatOf extends NBTNumberEnvelope<NBTTagFloat> {

    public NBTFloatOf(@NotNull final NBTTagFloat nbt) {
        super(nbt);
    }

}
