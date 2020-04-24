package io.github.portlek.nbt.api;

public interface NBTNumber<T> extends NBTBase<T> {
   long longValue();

   int intValue();

   short shortValue();

   byte byteValue();

   double doubleValue();

   float floatValue();
}
