package me.catzy44.resourcepack.translation;

public class Entry {
	public String path;
	public String name;
	public char ch;
	
	public String getAbsolutePath() {
		return path+"."+name;
	}
	
	public Entry(String[] path) {
		this(path[0],path[1],path[3].toCharArray()[0]);
	}
	public Entry(String path, String name, char ch) {
		this.path = "endi."+path.replace('\\', '.');
		this.name = name;
		this.ch = ch;
	}
}
