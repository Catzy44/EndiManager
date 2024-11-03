package me.przemovi.struct;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class YChunk{
	private Chunk chunk;
	private int x,y,z;
	
	public YChunk(Location l) {
		this.chunk = l.getChunk();
		this.x = chunk.getX();
		this.y = l.getBlockY() / 20 + 1;
		this.z = chunk.getZ();
	}

	public Chunk getChunk() {
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof YChunk)) {
			return false;
		}
		YChunk y = (YChunk) o;
		return this.x == y.getX() && this.y == y.getY() && this.z == y.getZ();
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+z;
	}
	
}
