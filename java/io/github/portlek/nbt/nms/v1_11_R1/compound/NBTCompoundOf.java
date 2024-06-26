package io.github.portlek.nbt.nms.v1_11_R1.compound;

import io.github.portlek.nbt.api.NBTBase;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.api.NBTList;
import io.github.portlek.nbt.api.NBTType;
import io.github.portlek.nbt.api.mck.MckNBTBase;
import io.github.portlek.nbt.api.mck.NBTEndOf;
import io.github.portlek.nbt.nms.v1_11_R1.NBTBaseEnvelope;
import io.github.portlek.nbt.nms.v1_11_R1.base.NBTStringOf;
import io.github.portlek.nbt.nms.v1_11_R1.list.NBTByteListOf;
import io.github.portlek.nbt.nms.v1_11_R1.list.NBTIntListOf;
import io.github.portlek.nbt.nms.v1_11_R1.list.NBTTagListOf;
import io.github.portlek.nbt.nms.v1_11_R1.number.*;
import net.minecraft.server.v1_11_R1.*;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class NBTCompoundOf extends NBTBaseEnvelope<NBTTagCompound> implements NBTCompound<NBTTagCompound> {

    public NBTCompoundOf(@NotNull final NBTTagCompound nbtTagCompound) {
        super(nbtTagCompound);
    }

    @Override
    public void set(@NotNull String key, @NotNull NBTBase value) {
        if (!(value.value() instanceof net.minecraft.server.v1_11_R1.NBTBase))
            return;

        value().set(key, (net.minecraft.server.v1_11_R1.NBTBase) value.value());
    }

    @Override
    public void setByte(@NotNull String key, byte value) {
        value().setByte(key, value);
    }

    @Override
    public void setShort(@NotNull String key, short value) {
        value().setShort(key, value);
    }

    @Override
    public void setInt(@NotNull String key, int value) {
        value().setInt(key, value);
    }

    @Override
    public void setLong(@NotNull String key, long value) {
        value().setLong(key, value);
    }

    @Override
    public void setFloat(@NotNull String key, float value) {
        value().setFloat(key, value);
    }

    @Override
    public void setDouble(@NotNull String key, double value) {
        value().setDouble(key, value);
    }

    @Override
    public void setString(@NotNull String key, @NotNull String value) {
        value().setString(key, value);
    }

    @Override
    public void setByteArray(@NotNull String key, @NotNull byte[] value) {
        value().setByteArray(key, value);
    }

    @Override
    public void setIntArray(@NotNull String key, @NotNull int[] value) {
        value().setIntArray(key, value);
    }

    @Override
    public void setIntList(@NotNull String key, @NotNull List<Integer> value) {
        throw new IllegalStateException("Unsupported operation to set int list!");
    }

    @Override
    public void setLongArray(@NotNull String key, @NotNull long[] value) {
        throw new IllegalStateException("Unsupported operation to set long array!");
    }

    @Override
    public void setLongList(@NotNull String key, @NotNull List<Long> value) {
        throw new IllegalStateException("Unsupported operation to set long list!");
    }

    @Override
    public void setBoolean(@NotNull String key, boolean value) {
        value().setBoolean(key, value);
    }

    @NotNull
    @Override
    public NBTBase get(@NotNull String key) {
        return new MapOf<>(
            new Mapped<>(
                value -> {
                    final net.minecraft.server.v1_11_R1.NBTBase base = value().get(value);
                    if (base == null)
                        return new MapEntry<>(key, new MckNBTBase());

                    switch (base.getTypeId()) {
                        case 0:
                            return new MapEntry<>(key, new NBTEndOf());
                        case 1:
                            return new MapEntry<>(key, new NBTByteOf((NBTTagByte) base));
                        case 2:
                            return new MapEntry<>(key, new NBTShortOf((NBTTagShort) base));
                        case 3:
                            return new MapEntry<>(key, new NBTIntOf((NBTTagInt) base));
                        case 4:
                            return new MapEntry<>(key, new NBTLongOf((NBTTagLong) base));
                        case 5:
                            return new MapEntry<>(key, new NBTFloatOf((NBTTagFloat) base));
                        case 6:
                            return new MapEntry<>(key, new NBTDoubleOf((NBTTagDouble) base));
                        case 7:
                            return new MapEntry<>(key, new NBTByteListOf((NBTTagByteArray) base));
                        case 8:
                            return new MapEntry<>(key, new NBTStringOf((NBTTagString) base));
                        case 9:
                            return new MapEntry<>(key, new NBTTagListOf((NBTTagList) base));
                        case 10:
                            return new MapEntry<>(key, new NBTCompoundOf((NBTTagCompound) base));
                        case 11:
                            return new MapEntry<>(key, new NBTIntListOf((NBTTagIntArray) base));
                        default:
                            return new MapEntry<>(key, new MckNBTBase());
                    }
                },
                keys()
            )
        ).getOrDefault(key, new MckNBTBase());
    }

    @Override
    public byte getByte(@NotNull String key) {
        return value().getByte(key);
    }

    @Override
    public short getShort(@NotNull String key) {
        return value().getShort(key);
    }

    @Override
    public int getInt(@NotNull String key) {
        return value().getInt(key);
    }

    @Override
    public long getLong(@NotNull String key) {
        return value().getLong(key);
    }

    @Override
    public float getFloat(@NotNull String key) {
        return value().getFloat(key);
    }

    @Override
    public double getDouble(@NotNull String key) {
        return value().getDouble(key);
    }

    @NotNull
    @Override
    public String getString(@NotNull String key) {
        return value().getString(key);
    }

    @Override
    public byte[] getByteArray(@NotNull String key) {
        return value().getByteArray(key);
    }

    @Override
    public int[] getIntArray(@NotNull String key) {
        return value().getIntArray(key);
    }

    @Override
    public long[] getLongArray(@NotNull String key) {
        throw new IllegalStateException("Unsupported operation to get long array!");
    }

    @NotNull
    @Override
    public NBTCompound getNBTCompound(@NotNull String key) {
        return new NBTCompoundOf(
            value().getCompound(key)
        );
    }

    @NotNull
    @Override
    public NBTList getList(@NotNull String key, @NotNull NBTType value) {
        return new NBTTagListOf(
            value().getList(key, value.getId())
        );
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return value().getBoolean(key);
    }

    @NotNull
    @Override
    public Set<String> keys() {
        return value().c();
    }

    @Override
    public boolean has(@NotNull String key) {
        return value().hasKey(key);
    }

    @Override
    public void remove(@NotNull String key) {
        value().remove(key);
    }

}
