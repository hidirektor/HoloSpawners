package io.github.portlek.nbt.nms.v1_12_R1;

import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.api.NBTNumber;
import io.github.portlek.nbt.api.mck.MckNBTCompound;
import io.github.portlek.nbt.api.mck.MckNBTList;
import io.github.portlek.nbt.nms.v1_12_R1.compound.NBTCompoundOf;
import io.github.portlek.nbt.nms.v1_12_R1.list.NBTByteListOf;
import io.github.portlek.nbt.nms.v1_12_R1.list.NBTIntListOf;
import io.github.portlek.nbt.nms.v1_12_R1.list.NBTLongListOf;
import io.github.portlek.nbt.nms.v1_12_R1.list.NBTTagListOf;
import io.github.portlek.reflection.RefClass;
import io.github.portlek.reflection.RefField;
import io.github.portlek.reflection.clazz.ClassOf;
import net.minecraft.server.v1_12_R1.*;
import org.cactoos.list.ListOf;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class NBTListEnvelope<V extends NBTBase, T extends NBTBase> extends NBTBaseEnvelope<T> implements io.github.portlek.nbt.api.NBTList<V, T> {

    public NBTListEnvelope(@NotNull T nbt) {
        super(nbt);
    }

    @Override
    public void add(@NotNull V value) {
        if (value() instanceof NBTTagList)
            ((NBTTagList) value()).add(value);
    }

    @Override
    public void add(int key, @NotNull V value) {
        if (value() instanceof NBTTagList) {
            ((NBTTagList) value()).add(value);
        }
    }

    @Override
    public void set(int key, @NotNull V value) {
        if (value() instanceof NBTTagList)
            ((NBTTagList) value()).a(key, value);
    }

    @Override
    public void remove(int key) {
        if (value() instanceof NBTTagList)
            ((NBTTagList) value()).remove(key);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public V get(int key) {
        final RefClass refClass = new ClassOf(NBTTagList.class);
        final RefField refField = refClass.getField("list");
        final List<V> list = (List<V>) refField.of(value()).get(new ListOf<>());

        return list.get(key);
    }

    @NotNull
    @Override
    public NBTCompound getCompound(int key) {
        if (get(key) instanceof NBTTagCompound)
            return new NBTCompoundOf((NBTTagCompound) get(key));

        return new MckNBTCompound();

    }

    @NotNull
    @Override
    public io.github.portlek.nbt.api.NBTList getList(int key) {
        final V list = get(key);

        if (list instanceof NBTTagList)
            return new NBTTagListOf((NBTTagList) list);

        if (list instanceof NBTTagByteArray)
            return new NBTByteListOf((NBTTagByteArray) list);

        if (list instanceof NBTTagIntArray)
            return new NBTIntListOf((NBTTagIntArray) list);

        if (list instanceof NBTTagLongArray)
            return new NBTLongListOf((NBTTagLongArray) list);

        return new MckNBTList();
    }

    @Override
    public short getShort(int key) {
        if (get(key) instanceof NBTNumber)
            return ((NBTNumber) get(key)).shortValue();
        return 0;
    }

    @Override
    public int getInt(int key) {
        if (get(key) instanceof NBTNumber)
            return ((NBTNumber) get(key)).intValue();
        return 0;
    }

    @NotNull
    @Override
    public int[] getIntArray(int key) {
        if (get(key) instanceof NBTTagIntArray)
            return ((NBTTagIntArray) get(key)).d();

        return new int[0];
    }

    @Override
    public double getDouble(int key) {
        if (get(key) instanceof NBTNumber)
            return ((NBTTagDouble) get(key)).asDouble();
        return 0;
    }

    @Override
    public float getFloat(int key) {
        if (get(key) instanceof NBTNumber)
            return ((NBTNumber) get(key)).floatValue();
        return 0;
    }

    @NotNull
    @Override
    public String getString(int key) {
        if (get(key) instanceof NBTTagString)
            return ((NBTTagString) get(key)).c_();
        return "";
    }

    @Override
    public int size() {
        if (value() instanceof NBTTagList)
            return ((NBTTagList) value()).size();
        return 0;
    }

}