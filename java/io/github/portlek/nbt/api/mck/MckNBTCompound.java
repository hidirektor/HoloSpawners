package io.github.portlek.nbt.api.mck;

import io.github.portlek.nbt.api.NBTBase;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.api.NBTList;
import io.github.portlek.nbt.api.NBTType;
import java.util.List;
import java.util.Set;
import org.cactoos.set.SetOf;
import org.jetbrains.annotations.NotNull;

public class MckNBTCompound extends MckNBTBase implements NBTCompound<NBTBase> {
   public void set(@NotNull String key, @NotNull NBTBase value) {
   }

   public void setByte(@NotNull String key, byte value) {
   }

   public void setShort(@NotNull String key, short value) {
   }

   public void setInt(@NotNull String key, int value) {
   }

   public void setLong(@NotNull String key, long value) {
   }

   public void setFloat(@NotNull String key, float value) {
   }

   public void setDouble(@NotNull String key, double value) {
   }

   public void setString(@NotNull String key, @NotNull String value) {
   }

   public void setByteArray(@NotNull String key, @NotNull byte[] value) {
   }

   public void setIntArray(@NotNull String key, @NotNull int[] value) {
   }

   public void setIntList(@NotNull String key, @NotNull List value) {
   }

   public void setLongArray(@NotNull String key, @NotNull long[] value) {
   }

   public void setLongList(@NotNull String key, @NotNull List value) {
   }

   public void setBoolean(@NotNull String key, boolean value) {
   }

   @NotNull
   public NBTBase get(@NotNull String key) {
      return new MckNBTBase();
   }

   public byte getByte(@NotNull String key) {
      return 0;
   }

   public short getShort(@NotNull String key) {
      return 0;
   }

   public int getInt(@NotNull String key) {
      return 0;
   }

   public long getLong(@NotNull String key) {
      return 0L;
   }

   public float getFloat(@NotNull String key) {
      return 0.0F;
   }

   public double getDouble(@NotNull String key) {
      return 0.0D;
   }

   @NotNull
   public String getString(@NotNull String key) {
      return "";
   }

   public byte[] getByteArray(@NotNull String key) {
      return new byte[0];
   }

   public int[] getIntArray(@NotNull String key) {
      return new int[0];
   }

   public long[] getLongArray(@NotNull String key) {
      return new long[0];
   }

   @NotNull
   public NBTCompound getNBTCompound(@NotNull String key) {
      return new MckNBTCompound();
   }

   @NotNull
   public NBTList getList(@NotNull String key, @NotNull NBTType value) {
      return new MckNBTList();
   }

   public boolean getBoolean(@NotNull String key) {
      return false;
   }

   @NotNull
   public Set<String> keys() {
      return new SetOf(new String[0]);
   }

   public boolean has(@NotNull String key) {
      return false;
   }

   public void remove(@NotNull String key) {
   }
}
