package me.yasei.struct;

import java.awt.image.BufferedImage;
import java.util.Map;

public class SkinsMap {
	private static Map<String, BufferedImage> skinsMap;

	public static BufferedImage getFace(String txt) {
		BufferedImage result = skinsMap.containsKey(txt) == true ? skinsMap.get(txt) : null;
		return  result;
	}

	public static void setFace(String txt, BufferedImage skin) {
		if(!skinsMap.containsKey(txt)) {
			skinsMap.put(txt, skin);
		} else if(skinsMap.containsKey(txt) && skinsMap.get(txt) != skin) {
			skinsMap.replace(txt, skin);
		}
	}
	public static boolean keyExists(String txt) {
		return skinsMap.containsKey(txt);
	}
 }
