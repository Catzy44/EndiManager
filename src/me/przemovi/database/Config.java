package me.przemovi.database;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.przemovi.EndiManager;
import me.przemovi.utils.Utils;

public class Config {
	private static JsonObject db;
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	static File f = new File(EndiManager.plugin.getDataFolder(),"database.json");
	public static JsonObject getDatabase() {
		return db;
	}
	public static void save() {
		try {
			//Utils.replaceFileContents(f, gson.toJson(db));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void load() {
		if(!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
				db = new JsonObject();
				db.add("players",new JsonObject());
				db.add("packs",new JsonArray());
				save();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			//db = (JSONObject) pa.parse(Utils.readEverythingFromFile(f));
			db = gson.fromJson(Utils.readEverythingFromFile(f), JsonObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
