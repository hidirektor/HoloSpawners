package io.github.portlek.nbt.nms.v1_13_R2;

import net.minecraft.server.v1_13_R2.NBTNumber;
import org.jetbrains.annotations.NotNull;

public abstract class NBTNumberEnvelope<T extends NBTNumber> extends NBTBaseEnvelope<T> implements io.github.portlek.nbt.api.NBTNumber<T> {

    public NBTNumberEnvelope(@NotNull final T nbt) {
        super(nbt);
    }

    @Override
    public long longValue() {
        return value().asLong();
    }

    @Override
    public int intValue() {
        return value().asInt();
    }

    @Override
    public short shortValue() {
        return value().asShort();
    }

    @Override
    public byte byteValue() {
        return value().asByte();
    }

    @Override
    public double doubleValue() {
        return value().asDouble();
    }

    @Override
    public float floatValue() {
        return value().asFloat();
    }
}
