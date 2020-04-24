package io.github.portlek.nbt.nms.v1_13_R2.base;

import io.github.portlek.nbt.nms.v1_13_R2.NBTBaseEnvelope;
import net.minecraft.server.v1_13_R2.NBTTagString;
import org.jetbrains.annotations.NotNull;

public class NBTStringOf extends NBTBaseEnvelope<NBTTagString> {

    public NBTStringOf(@NotNull final NBTTagString nbt) {
        super(nbt);
    }

}
