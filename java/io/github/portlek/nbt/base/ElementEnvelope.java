package io.github.portlek.nbt.base;

import io.github.portlek.nbt.api.Element;
import io.github.portlek.nbt.api.NBTRegistry;
import io.github.portlek.nbt.api.mck.MckNBTRegistry;
import io.github.portlek.nbt.nms.v1_10_R1.NBTRegistry1_10_R1;
import io.github.portlek.nbt.nms.v1_11_R1.NBTRegistry1_11_R1;
import io.github.portlek.nbt.nms.v1_12_R1.NBTRegistry1_12_R1;
import io.github.portlek.nbt.nms.v1_13_R1.NBTRegistry1_13_R1;
import io.github.portlek.nbt.nms.v1_13_R2.NBTRegistry1_13_R2;
import io.github.portlek.nbt.nms.v1_14_R1.NBTRegistry1_14_R1;
import io.github.portlek.nbt.nms.v1_8_R1.NBTRegistry1_8_R1;
import io.github.portlek.nbt.nms.v1_8_R2.NBTRegistry1_8_R2;
import io.github.portlek.nbt.nms.v1_8_R3.NBTRegistry1_8_R3;
import io.github.portlek.nbt.nms.v1_9_R1.NBTRegistry1_9_R1;
import io.github.portlek.nbt.nms.v1_9_R2.NBTRegistry1_9_R2;
import io.github.portlek.versionmatched.VersionMatched;
import org.jetbrains.annotations.NotNull;

public abstract class ElementEnvelope<V, T> implements Element<V, T> {
   @NotNull
   public static final NBTRegistry REGISTRY = (NBTRegistry)(new VersionMatched(new MckNBTRegistry(), new Class[]{NBTRegistry1_14_R1.class, NBTRegistry1_13_R2.class, NBTRegistry1_13_R1.class, NBTRegistry1_12_R1.class, NBTRegistry1_11_R1.class, NBTRegistry1_10_R1.class, NBTRegistry1_9_R2.class, NBTRegistry1_9_R1.class, NBTRegistry1_8_R3.class, NBTRegistry1_8_R2.class, NBTRegistry1_8_R1.class})).of().instance();
   @NotNull
   private final V element;

   public ElementEnvelope(@NotNull V element) {
      this.element = element;
   }

   @NotNull
   public V element() {
      return this.element;
   }
}
