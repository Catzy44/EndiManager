package me.przemovi.utils;

public class AlpChar {
	public char orig;
	public char mod;
	public int width;

	public AlpChar(char orig, char mod, int width) {
		this.orig = orig;
		this.mod = mod;
		this.width = width;
	}
	
	public AlpChar(String orig, String mod, int width) {
		this.orig = orig.toCharArray()[0];
		this.mod = mod.toCharArray()[0];
		this.width = width;
	}

}
