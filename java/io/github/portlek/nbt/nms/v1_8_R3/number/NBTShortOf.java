package io.github.portlek.nbt.nms.v1_8_R3.number;

import io.github.portlek.nbt.nms.v1_8_R3.NBTNumberEnvelope;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import org.jetbrains.annotations.NotNull;

public class NBTShortOf extends NBTNumberEnvelope<NBTTagShort> {

    public NBTShortOf(@NotNull final NBTTagShort nbt) {
        super(nbt);
    }

}
