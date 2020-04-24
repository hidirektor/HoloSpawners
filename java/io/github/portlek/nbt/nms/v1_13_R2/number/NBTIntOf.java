package io.github.portlek.nbt.nms.v1_13_R2.number;

import io.github.portlek.nbt.nms.v1_13_R2.NBTNumberEnvelope;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import org.jetbrains.annotations.NotNull;

public class NBTIntOf extends NBTNumberEnvelope<NBTTagInt> {

    public NBTIntOf(@NotNull final NBTTagInt nbt) {
        super(nbt);
    }

}
