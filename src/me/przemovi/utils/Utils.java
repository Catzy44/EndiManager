package me.przemovi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.google.gson.JsonObject;

import me.przemovi.EndiManager;

public class Utils {
	
	public static List<String> jkeys(JsonObject obj) {
		List<String> keys = obj.entrySet()
			    .stream()
			    .map(i -> i.getKey())
			    .collect(Collectors.toCollection(ArrayList::new));
		return keys;
	}
	
	public static final byte[] fromHexString(final String s) {
	    byte[] arr = new byte[s.length()/2];
	    for ( int start = 0; start < s.length(); start += 2 )
	    {
	        String thisByte = s.substring(start, start+2);
	        arr[start/2] = Byte.parseByte(thisByte, 16);
	    }
	    return arr;
	}
	
	public static boolean isPluginInstalled(String name) {
	    return EndiManager.plugin.getServer().getPluginManager().getPlugin(name) != null;
	}
	
	public static JsonObject locationToJSON(Location loc) {
		JsonObject obj = new JsonObject();
		obj.addProperty("world", loc.getWorld().getName());
		obj.addProperty("x", loc.getBlockX());
		obj.addProperty("y", loc.getBlockY());
		obj.addProperty("z", loc.getBlockZ());
		return obj;
	}
	
	public static Location locationFromJSON(JsonObject obj) {
		return new Location(Bukkit.getWorld( obj.get("world").getAsString()),obj.get("x").getAsInt(),obj.get("y").getAsInt(),obj.get("z").getAsInt());
	}
	
	public static void replaceFileContents(File f, String s) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
		writer.write(s);
		writer.close();
	}

	public static void replaceInFile(File file, String replace, String with) {
		Path path = file.toPath();
		Charset charset = StandardCharsets.UTF_8;
		try {
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(replace, with);
			Files.write(path, content.getBytes(charset));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readEverythingFromFile(File f) throws IOException {
		FileReader reader = new FileReader(f,StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(reader);

		String s = "";

		String line = "";
		while ((line = br.readLine()) != null) {
			s += line;
		}

		br.close();
		reader.close();

		return s;
	}

	public static List<String> readLinesFromFile(File f) throws IOException {
		FileReader reader = new FileReader(f,StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(reader);

		String line;
		List<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		br.close();
		reader.close();

		return lines;
	}
	public static String readEverythingFromURL(String s) throws IOException {
		return readEverythingFromURL(s,false);
	}
	public static String readEverythingFromURL(String s, boolean applyNewlineChars) throws IOException {
		URL url = new URL(s);
		URLConnection con = url.openConnection();
		con.setConnectTimeout(3000);
		con.setReadTimeout(3000);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

		String entirefile = "";

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			entirefile += inputLine;
			if(applyNewlineChars) {
				entirefile += '\n';
			}
		}
		in.close();

		return entirefile;
	}
}
