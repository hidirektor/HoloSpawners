package io.github.portlek.nbt.nms.v1_15_R1.list;

import io.github.portlek.nbt.nms.v1_15_R1.NBTListEnvelope;
import net.minecraft.server.v1_15_R1.NBTTagInt;
import net.minecraft.server.v1_15_R1.NBTTagIntArray;
import org.jetbrains.annotations.NotNull;

public class NBTIntListOf extends NBTListEnvelope<NBTTagInt, NBTTagIntArray> {

    public NBTIntListOf(@NotNull final NBTTagIntArray nbt) {
        super(nbt);
    }
}
