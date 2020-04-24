package io.github.portlek.nbt.nms.v1_11_R1.number;

import io.github.portlek.nbt.nms.v1_11_R1.NBTNumberEnvelope;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import org.jetbrains.annotations.NotNull;

public class NBTIntOf extends NBTNumberEnvelope<NBTTagInt> {

    public NBTIntOf(@NotNull final NBTTagInt nbt) {
        super(nbt);
    }

}
