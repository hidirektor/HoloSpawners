package io.github.portlek.nbt.nms.v1_8_R3.list;

import io.github.portlek.nbt.nms.v1_8_R3.NBTListEnvelope;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagByteArray;
import org.jetbrains.annotations.NotNull;

public class NBTByteListOf extends NBTListEnvelope<NBTTagByte, NBTTagByteArray> {

    public NBTByteListOf(@NotNull final NBTTagByteArray nbt) {
        super(nbt);
    }
}
