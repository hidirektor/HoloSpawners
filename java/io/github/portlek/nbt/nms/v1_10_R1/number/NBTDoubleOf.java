package io.github.portlek.nbt.nms.v1_10_R1.number;

import io.github.portlek.nbt.nms.v1_10_R1.NBTNumberEnvelope;
import net.minecraft.server.v1_10_R1.NBTTagDouble;
import org.jetbrains.annotations.NotNull;

public class NBTDoubleOf extends NBTNumberEnvelope<NBTTagDouble> {

    public NBTDoubleOf(@NotNull final NBTTagDouble nbt) {
        super(nbt);
    }

}
