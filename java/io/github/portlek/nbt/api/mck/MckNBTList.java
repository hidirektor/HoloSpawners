package io.github.portlek.nbt.api.mck;

import io.github.portlek.nbt.api.NBTBase;
import io.github.portlek.nbt.api.NBTCompound;
import io.github.portlek.nbt.api.NBTList;
import org.jetbrains.annotations.NotNull;

public class MckNBTList extends MckNBTBase implements NBTList<NBTBase, NBTBase> {
   public void add(@NotNull NBTBase value) {
   }

   public void add(int key, @NotNull NBTBase value) {
   }

   public void set(int key, @NotNull NBTBase value) {
   }

   public void remove(int key) {
   }

   @NotNull
   public NBTBase get(int key) {
      return new MckNBTBase();
   }

   @NotNull
   public NBTCompound getCompound(int key) {
      return new MckNBTCompound();
   }

   @NotNull
   public NBTList getList(int key) {
      return new MckNBTList();
   }

   public short getShort(int key) {
      return 0;
   }

   public int getInt(int key) {
      return 0;
   }

   @NotNull
   public int[] getIntArray(int key) {
      return new int[0];
   }

   public double getDouble(int key) {
      return 0.0D;
   }

   public float getFloat(int key) {
      return 0.0F;
   }

   @NotNull
   public String getString(int key) {
      return "";
   }

   public int size() {
      return 0;
   }
}
