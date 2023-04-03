package io.github.guillex7.explodeany.block;

import java.io.IOException;

import org.bukkit.Material;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BlockStatusAdapter extends TypeAdapter<BlockStatus> {
	@Override
	public void write(JsonWriter out, BlockStatus value) throws IOException {
		out.beginArray();
		out.value(value.getDurability());
		out.value(value.getMaterial().name());
		out.endArray();
	}

	@Override
	public BlockStatus read(JsonReader in) throws IOException {
		in.beginArray();
		double durability = in.nextDouble();
		String materialName = in.nextString();
		in.endArray();

		return BlockStatus.of(Material.valueOf(materialName), durability);
	}
}
