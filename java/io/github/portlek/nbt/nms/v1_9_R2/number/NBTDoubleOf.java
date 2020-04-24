package io.github.portlek.nbt.nms.v1_9_R2.number;

import io.github.portlek.nbt.nms.v1_9_R2.NBTNumberEnvelope;
import net.minecraft.server.v1_9_R2.NBTTagDouble;
import org.jetbrains.annotations.NotNull;

public class NBTDoubleOf extends NBTNumberEnvelope<NBTTagDouble> {

    public NBTDoubleOf(@NotNull final NBTTagDouble nbt) {
        super(nbt);
    }

}
