package io.github.guillex7.explodeany.block;

import java.io.IOException;

import org.bukkit.Material;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class BlockStatusAdapter extends TypeAdapter<BlockStatus> {
    @Override
    public void write(final JsonWriter out, final BlockStatus value) throws IOException {
        out.beginArray();
        out.value(value.getRawDurability());
        out.value(value.getMaterial().name());
        out.value(value.getLastDamaged());
        out.endArray();
    }

    @Override
    public BlockStatus read(final JsonReader in) throws IOException {
        in.beginArray();
        final double durability = in.nextDouble();
        final Material material = Material.getMaterial(in.nextString());
        long lastDamaged = -1;
        if (in.hasNext() && in.peek() == JsonToken.NUMBER) {
            lastDamaged = in.nextLong();
        }
        in.endArray();

        return BlockStatus.of(material, durability, lastDamaged);
    }
}
