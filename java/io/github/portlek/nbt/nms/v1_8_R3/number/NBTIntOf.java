package io.github.portlek.nbt.nms.v1_8_R3.number;

import io.github.portlek.nbt.nms.v1_8_R3.NBTNumberEnvelope;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.jetbrains.annotations.NotNull;

public class NBTIntOf extends NBTNumberEnvelope<NBTTagInt> {

    public NBTIntOf(@NotNull final NBTTagInt nbt) {
        super(nbt);
    }

}
