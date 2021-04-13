package me.catzy44.resourcepack.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import me.przemovi.EndiManager;
import me.przemovi.utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class Translator {
	private static List<Entry> arr = new ArrayList<Entry>();

	public static void load() {
		try {
			String read = Utils.readEverythingFromURL("https://www.dropbox.com/s/cyxrr3k3y828sj5/map.txt?dl=1",true);
			String spl[] = read.split("\n");
			for (String line : spl) {
				String subspl[] = line.split(" ");
				if (subspl.length < 3) {
					continue;
				}
				Entry rt = new Entry(subspl);
				arr.add(rt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		EndiManager.log("resourcepack mappings successfully installed");
	}
	public static void dump() {
		arr.clear();
		arr = null;
	}
	public static BaseComponent translate(String x) {
		try {
			Entry e = findTranslation(x);
			if(e != null) {
				return new TextComponent(e.ch+"");
			}
		} catch (Exception e) {
			EndiManager.log("translation unsuccessfull: "+x);
		}
		return new TranslatableComponent(x);
	}
	
	private static Entry findTranslation(String fff) throws NoSuchElementException {
		return arr.stream()
		.filter(e -> e.getAbsolutePath().equals(fff))
		.findAny()
		.orElseThrow();
		//java8 streams, awesome! no more boring loops!
	}
}
