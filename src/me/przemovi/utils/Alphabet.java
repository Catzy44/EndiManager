package me.przemovi.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.przemovi.EndiManager;

public class Alphabet {
	private static JsonObject db;
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	static File f;
	public static JsonObject getDatabase() {
		return db;
	}
	public static void save() {
		try {
			Utils.replaceFileContents(f, gson.toJson(db));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static  Map<String,List<AlpChar>> alphabets = new HashMap<String,List<AlpChar>>();
	public static void load() {
		f = new File(EndiManager.plugin.getDataFolder(),"alphabets.json");
		if(!f.exists()) {
			System.out.println("RPHOOKS FAILED TO LOAD ALPHABET JSON!");
			return;
		}
		try {
			//db = (JSONObject) pa.parse(Utils.readEverythingFromFile(f));
			db = gson.fromJson(Utils.readEverythingFromFile(f), JsonObject.class);
			
			for(String name : Utils.jkeys(db)) {
				List<AlpChar> chars;
				if(alphabets.containsKey(name)) {
					chars = alphabets.get(name);
				} else {
					chars = new ArrayList<AlpChar>();
					alphabets.put(name, chars);
				}
				
				JsonObject alpha = db.get(name).getAsJsonObject();
				for(String cha : Utils.jkeys(alpha)) {
					JsonObject charInfo = alpha.get(cha).getAsJsonObject();
					chars.add(new AlpChar(cha,
							charInfo.get("char").getAsString(),
							charInfo.get("width").getAsInt()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testload() {
		//db = (JSONObject) pa.parse(Utils.readEverythingFromFile(f));
		db = gson.fromJson("{\"normal\":{\"a\":{\"char\":\"î”€\",\"width\":8.0},\"b\":{\"char\":\"î”�\",\"width\":8.0},\"c\":{\"char\":\"î”‚\",\"width\":8.0},\"d\":{\"char\":\"î”�\",\"width\":8.0},\"e\":{\"char\":\"î”„\",\"width\":6.0},\"f\":{\"char\":\"î”…\",\"width\":6.0},\"g\":{\"char\":\"î”†\",\"width\":8.0},\"h\":{\"char\":\"î”‡\",\"width\":8.0},\"i\":{\"char\":\"î”�\",\"width\":6.0},\"j\":{\"char\":\"î”‰\",\"width\":8.0},\"k\":{\"char\":\"î”Š\",\"width\":8.0},\"l\":{\"char\":\"î”‹\",\"width\":6.0},\"m\":{\"char\":\"î”Ś\",\"width\":10.0},\"n\":{\"char\":\"î”Ť\",\"width\":8.0},\"o\":{\"char\":\"î”Ž\",\"width\":8.0},\"p\":{\"char\":\"î”Ź\",\"width\":8.0},\"q\":{\"char\":\"î”�\",\"width\":8.0},\"r\":{\"char\":\"î”‘\",\"width\":8.0},\"s\":{\"char\":\"î”’\",\"width\":8.0},\"t\":{\"char\":\"î”“\",\"width\":6.0},\"u\":{\"char\":\"î””\",\"width\":8.0},\"v\":{\"char\":\"î”•\",\"width\":10.0},\"w\":{\"char\":\"î”–\",\"width\":10.0},\"x\":{\"char\":\"î”—\",\"width\":6.0},\"y\":{\"char\":\"î”�\",\"width\":10.0},\"z\":{\"char\":\"î”™\",\"width\":8.0}},\"silver\":{\"a\":{\"char\":\"î�€\",\"width\":8.0},\"b\":{\"char\":\"î��\",\"width\":8.0},\"c\":{\"char\":\"î�‚\",\"width\":8.0},\"d\":{\"char\":\"î��\",\"width\":8.0},\"e\":{\"char\":\"î�„\",\"width\":6.0},\"f\":{\"char\":\"î�…\",\"width\":6.0},\"g\":{\"char\":\"î�†\",\"width\":8.0},\"h\":{\"char\":\"î�‡\",\"width\":8.0},\"i\":{\"char\":\"î��\",\"width\":6.0},\"j\":{\"char\":\"î�‰\",\"width\":8.0},\"k\":{\"char\":\"î�Š\",\"width\":8.0},\"l\":{\"char\":\"î�‹\",\"width\":6.0},\"m\":{\"char\":\"î�Ś\",\"width\":10.0},\"n\":{\"char\":\"î�Ť\",\"width\":8.0},\"o\":{\"char\":\"î�Ž\",\"width\":8.0},\"p\":{\"char\":\"î�Ź\",\"width\":8.0},\"q\":{\"char\":\"î��\",\"width\":8.0},\"r\":{\"char\":\"î�‘\",\"width\":8.0},\"s\":{\"char\":\"î�’\",\"width\":8.0},\"t\":{\"char\":\"î�“\",\"width\":6.0},\"u\":{\"char\":\"î�”\",\"width\":8.0},\"v\":{\"char\":\"î�•\",\"width\":10.0},\"w\":{\"char\":\"î�–\",\"width\":10.0},\"x\":{\"char\":\"î�—\",\"width\":6.0},\"y\":{\"char\":\"î��\",\"width\":10.0},\"z\":{\"char\":\"î�™\",\"width\":8.0}},\"dark_rose\":{\"a\":{\"char\":\"îś€\",\"width\":8.0},\"b\":{\"char\":\"îś�\",\"width\":8.0},\"c\":{\"char\":\"îś‚\",\"width\":8.0},\"d\":{\"char\":\"îś�\",\"width\":8.0},\"e\":{\"char\":\"îś„\",\"width\":6.0},\"f\":{\"char\":\"îś…\",\"width\":6.0},\"g\":{\"char\":\"îś†\",\"width\":8.0},\"h\":{\"char\":\"îś‡\",\"width\":8.0},\"i\":{\"char\":\"îś�\",\"width\":6.0},\"j\":{\"char\":\"îś‰\",\"width\":8.0},\"k\":{\"char\":\"îśŠ\",\"width\":8.0},\"l\":{\"char\":\"îś‹\",\"width\":6.0},\"m\":{\"char\":\"îśŚ\",\"width\":10.0},\"n\":{\"char\":\"îśŤ\",\"width\":8.0},\"o\":{\"char\":\"îśŽ\",\"width\":8.0},\"p\":{\"char\":\"îśŹ\",\"width\":8.0},\"q\":{\"char\":\"îś�\",\"width\":8.0},\"r\":{\"char\":\"îś‘\",\"width\":8.0},\"s\":{\"char\":\"îś’\",\"width\":8.0},\"t\":{\"char\":\"îś“\",\"width\":6.0},\"u\":{\"char\":\"îś”\",\"width\":8.0},\"v\":{\"char\":\"îś•\",\"width\":10.0},\"w\":{\"char\":\"îś–\",\"width\":10.0},\"x\":{\"char\":\"îś—\",\"width\":6.0},\"y\":{\"char\":\"îś�\",\"width\":10.0},\"z\":{\"char\":\"îś™\",\"width\":8.0}},\"rose\":{\"a\":{\"char\":\"î €\",\"width\":8.0},\"b\":{\"char\":\"î �\",\"width\":8.0},\"c\":{\"char\":\"î ‚\",\"width\":8.0},\"d\":{\"char\":\"î �\",\"width\":8.0},\"e\":{\"char\":\"î „\",\"width\":6.0},\"f\":{\"char\":\"î …\",\"width\":6.0},\"g\":{\"char\":\"î †\",\"width\":8.0},\"h\":{\"char\":\"î ‡\",\"width\":8.0},\"i\":{\"char\":\"î �\",\"width\":6.0},\"j\":{\"char\":\"î ‰\",\"width\":8.0},\"k\":{\"char\":\"î Š\",\"width\":8.0},\"l\":{\"char\":\"î ‹\",\"width\":6.0},\"m\":{\"char\":\"î Ś\",\"width\":10.0},\"n\":{\"char\":\"î Ť\",\"width\":8.0},\"o\":{\"char\":\"î Ž\",\"width\":8.0},\"p\":{\"char\":\"î Ź\",\"width\":8.0},\"q\":{\"char\":\"î �\",\"width\":8.0},\"r\":{\"char\":\"î ‘\",\"width\":8.0},\"s\":{\"char\":\"î ’\",\"width\":8.0},\"t\":{\"char\":\"î “\",\"width\":6.0},\"u\":{\"char\":\"î ”\",\"width\":8.0},\"v\":{\"char\":\"î •\",\"width\":10.0},\"w\":{\"char\":\"î –\",\"width\":10.0},\"x\":{\"char\":\"î —\",\"width\":6.0},\"y\":{\"char\":\"î �\",\"width\":10.0},\"z\":{\"char\":\"î ™\",\"width\":8.0}},\"gold\":{\"a\":{\"char\":\"î¤€\",\"width\":8.0},\"b\":{\"char\":\"î¤�\",\"width\":8.0},\"c\":{\"char\":\"î¤‚\",\"width\":8.0},\"d\":{\"char\":\"î¤�\",\"width\":8.0},\"e\":{\"char\":\"î¤„\",\"width\":6.0},\"f\":{\"char\":\"î¤…\",\"width\":6.0},\"g\":{\"char\":\"î¤†\",\"width\":8.0},\"h\":{\"char\":\"î¤‡\",\"width\":8.0},\"i\":{\"char\":\"î¤�\",\"width\":6.0},\"j\":{\"char\":\"î¤‰\",\"width\":8.0},\"k\":{\"char\":\"î¤Š\",\"width\":8.0},\"l\":{\"char\":\"î¤‹\",\"width\":6.0},\"m\":{\"char\":\"î¤Ś\",\"width\":10.0},\"n\":{\"char\":\"î¤Ť\",\"width\":8.0},\"o\":{\"char\":\"î¤Ž\",\"width\":8.0},\"p\":{\"char\":\"î¤Ź\",\"width\":8.0},\"q\":{\"char\":\"î¤�\",\"width\":8.0},\"r\":{\"char\":\"î¤‘\",\"width\":8.0},\"s\":{\"char\":\"î¤’\",\"width\":8.0},\"t\":{\"char\":\"î¤“\",\"width\":6.0},\"u\":{\"char\":\"î¤”\",\"width\":8.0},\"v\":{\"char\":\"î¤•\",\"width\":10.0},\"w\":{\"char\":\"î¤–\",\"width\":10.0},\"x\":{\"char\":\"î¤—\",\"width\":6.0},\"y\":{\"char\":\"î¤�\",\"width\":10.0},\"z\":{\"char\":\"î¤™\",\"width\":8.0}}}", JsonObject.class);
		
		for(String name : Utils.jkeys(db)) {
			List<AlpChar> chars;
			if(alphabets.containsKey(name)) {
				chars = alphabets.get(name);
			} else {
				chars = new ArrayList<AlpChar>();
				alphabets.put(name, chars);
			}
			
			JsonObject alpha = db.get(name).getAsJsonObject();
			for(String cha : Utils.jkeys(alpha)) {
				JsonObject charInfo = alpha.get(cha).getAsJsonObject();
				chars.add(new AlpChar(cha,
						charInfo.get("char").getAsString(),
						charInfo.get("width").getAsInt()));
			}
		}
	}
	
	static String x = "{\" \":\"4\",\"!\":\"2\",\"\\\"\":\"5\",\"#\":\"6\",\"$\":\"6\",\"%\":\"6\",\"&\":\"6\",\"'\":\"3\",\"(\":\"5\",\")\":\"5\",\"*\":\"5\",\"+\":\"6\",\",\":\"2\",\"-\":\"6\",\".\":\"2\",\"/\":\"6\",\"0\":\"6\",\"1\":\"6\",\"2\":\"6\",\"3\":\"6\",\"4\":\"6\",\"5\":\"6\",\"6\":\"6\",\"7\":\"6\",\"8\":\"6\",\"9\":\"6\",\":\":\"2\",\";\":\"2\",\"<\":\"5\",\"=\":\"6\",\">\":\"5\",\"?\":\"6\",\"@\":\"7\",\"A\":\"6\",\"B\":\"6\",\"C\":\"6\",\"D\":\"6\",\"E\":\"6\",\"F\":\"6\",\"G\":\"6\",\"H\":\"6\",\"I\":\"4\",\"J\":\"6\",\"K\":\"6\",\"L\":\"6\",\"M\":\"6\",\"N\":\"6\",\"O\":\"6\",\"P\":\"6\",\"Q\":\"6\",\"R\":\"6\",\"S\":\"6\",\"T\":\"6\",\"U\":\"6\",\"V\":\"6\",\"W\":\"6\",\"X\":\"6\",\"Y\":\"6\",\"Z\":\"6\",\"[\":\"4\",\"\\\\\":\"6\",\"]\":\"4\",\"^\":\"6\",\"_\":\"6\",\"'\":\"3\",\"a\":\"6\",\"b\":\"6\",\"c\":\"6\",\"d\":\"6\",\"e\":\"6\",\"f\":\"5\",\"g\":\"6\",\"h\":\"6\",\"i\":\"2\",\"j\":\"6\",\"k\":\"5\",\"l\":\"3\",\"m\":\"6\",\"n\":\"6\",\"o\":\"6\",\"p\":\"6\",\"q\":\"6\",\"r\":\"6\",\"s\":\"6\",\"t\":\"4\",\"u\":\"6\",\"v\":\"6\",\"w\":\"6\",\"x\":\"6\",\"y\":\"6\",\"z\":\"6\",\"{\":\"5\",\"|\":\"2\",\"}\":\"5\",\"~\":\"7\",\"⌂\":\"6\",\"Ç\":\"6\",\"ü\":\"6\",\"é\":\"6\",\"â\":\"6\",\"ä\":\"6\",\"à\":\"6\",\"å\":\"6\",\"ç\":\"6\",\"ê\":\"6\",\"ë\":\"6\",\"è\":\"6\",\"ï\":\"4\",\"î\":\"6\",\"ì\":\"3\",\"Ä\":\"6\",\"Å\":\"6\",\"É\":\"6\",\"æ\":\"6\",\"Æ\":\"6\",\"ô\":\"6\",\"ö\":\"6\",\"ò\":\"6\",\"û\":\"6\",\"ù\":\"6\",\"ÿ\":\"6\",\"Ö\":\"6\",\"Ü\":\"6\",\"ø\":\"6\",\"£\":\"6\",\"Ø\":\"6\",\"×\":\"4\",\"ƒ\":\"6\",\"á\":\"6\",\"í\":\"3\",\"ó\":\"6\",\"ú\":\"6\",\"ñ\":\"6\",\"Ñ\":\"6\",\"ª\":\"6\",\"º\":\"6\",\"¿\":\"6\",\"®\":\"7\",\"¬\":\"6\",\"½\":\"6\",\"¼\":\"6\",\"¡\":\"2\",\"«\":\"6\",\"»\":\"6\"}";
	static JsonObject xo = null;
	
	public static int getStrW(String s) {
		s = s.replace("ę","e");
		if(xo == null) {
			xo = gson.fromJson(x, JsonObject.class);
		}
		int wi = 0;
		for(char c : s.toCharArray()) {
			if(xo.get(c+"") == null) {
				wi += 4;
				continue;
			}
			wi += xo.get(c+"").getAsInt();
		}
		return wi;
	}
	
	public static String replace(String alphaName, String toReplace) {
		List<AlpChar> charset = alphabets.get(alphaName);
		
		StringBuilder sb = new StringBuilder();
		for(char c : toReplace.toCharArray()) {
			for(AlpChar ac : charset) {
				if(ac.orig != c) {
					continue;
				}
				sb.append(ac.mod);
			}
		}
		return sb.toString();
	}
	public static int calculateModdedStringWidth(String alphaName, String toReplace) throws Exception {
		if(alphabets == null || !alphabets.containsKey(alphaName)) {
			throw new Exception("ALPHABET NOT LOADED YET! ["+alphaName+"]");
		}
		List<AlpChar> charset = alphabets.get(alphaName);
		
		int width = 0;
		for(char c : toReplace.toCharArray()) {
			for(AlpChar ac : charset) {
				if(ac.orig != c) {
					continue;
				}
				width += ac.width/2;
			}
		}
		return width;
	}
}
