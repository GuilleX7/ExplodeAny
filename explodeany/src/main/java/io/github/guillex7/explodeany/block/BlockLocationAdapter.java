package io.github.guillex7.explodeany.block;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BlockLocationAdapter extends TypeAdapter<BlockLocation> {
	@Override
	public void write(JsonWriter out, BlockLocation value) throws IOException {
		out.value(String.format("%s:%d:%d:%d", value.getWorldUuid().toString(), value.getX(), value.getY(),
				value.getZ()));
	}

	@Override
	public BlockLocation read(JsonReader in) throws IOException {
		String parts[] = in.nextString().split(":");
		return BlockLocation.of(UUID.fromString(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
				Integer.parseInt(parts[3]));
	}
}
