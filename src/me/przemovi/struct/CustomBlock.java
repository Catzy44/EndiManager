package me.przemovi.struct;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import com.google.gson.JsonObject;

public class CustomBlock {
	private Location loc;
	private Material type;
	private BlockData data;

	public CustomBlock(Location loc, Material type, BlockData data) {
		this.loc = loc;
		this.type = type;
		this.data = data;
	}
	
	public CustomBlock(JsonObject me) {
		loc = deserializeLoc(me.get("loc").getAsJsonObject());
		type = Material.valueOf(me.get("mat").getAsString());
		data = Bukkit.createBlockData(me.get("data").getAsString());
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public BlockData getData() {
		return data;
	}

	public void setData(BlockData data) {
		this.data = data;
	}
	
	public static JsonObject serializeLoc(Location loc) {
		JsonObject me = new JsonObject();
		me.addProperty("x", loc.getX());
		me.addProperty("y", loc.getY());
		me.addProperty("z", loc.getZ());
		me.addProperty("w", loc.getWorld().getName());
		return me;
	}
	
	public static Location deserializeLoc(JsonObject me) {
		return new Location(Bukkit.getWorld(me.get("w").getAsString()),me.get("x").getAsDouble(),me.get("y").getAsDouble(),me.get("z").getAsDouble());
	}
	
	public JsonObject serialize() {
		JsonObject me = new JsonObject();
		me.addProperty("data", data.getAsString());
		me.addProperty("mat", type.toString());
		me.add("loc", serializeLoc(loc));
		return me;
	}

}
