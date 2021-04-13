package me.przemovi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.meta.BookMeta.Generation;

import me.przemovi.utils.CustomBook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Interface2 {
	
	public static CustomBook SUCCESS = null;
	public static CustomBook ASK_FOR_RP_TYPE = null;
	private static final String cmd = "endiman";
	
	public static void load() {
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			BaseComponent cb[] = new ComponentBuilder("Wygląta na to że wszystko gra!\n\n")
					.append("Jeżeli masz słaby komputer możesz spróbować przełaczyć si").underlined(false)
					.append("\n\n\n\n").underlined(false)
					.append(new ComponentBuilder("Koniec\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "")).create())
					.append(new ComponentBuilder("...zmieniam zdanie!").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/paczka")).create())
					.create();
			pages.add(cb);
			SUCCESS = new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages);
		}
		
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			BaseComponent cb[] = new ComponentBuilder("")
					.append(new ComponentBuilder("Do grania na EndiMc potrzebujesz naszego resourcepacka.\n\n"+
					"Zostanie on zainstalowany automatycznie, nic nie musisz robić!\n"+
					"\n\n").create())
				
					.append(new ComponentBuilder("EndiMc+Slimefun [25MB]\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" install 2")).create())
					.append(new ComponentBuilder("Tylko EndiMc [3MB]\n").underlined(true).color(ChatColor.BLUE).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" install 1")).create())
					.append(new ComponentBuilder("Nie, wychodzę.\n").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" quit")).create())
					/*.append(new ComponentBuilder("usuwanie i więcej informacji\n").underlined(true).color(ChatColor.BLUE).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/")).create())*/
					//.append(new ComponentBuilder("nie tym razem\n").underlined(true).color(ChatColor.BLUE).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "")).create())
					.create();
			pages.add(cb);
			ASK_FOR_RP_TYPE = new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages);
		}
	}
	
	/*public static final int SELECT;

	SUCCESSFULLY_LOADED;
	
	public void show(Player p) {
		if(this == SUCCESSFULLY_LOADED) {
			if(successfullyLoaded == null) {
				List<BaseComponent[]> pages = new ArrayList<>();
				BaseComponent cb[] = new ComponentBuilder("Wygląta na to że wszystko gra!\n\n")
						.append("Jeżeli masz słaby komputer możesz spróbować przełaczyć si").underlined(false)
						.append("\n\n\n\n").underlined(false)
						.append(new ComponentBuilder("Koniec\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "")).create())
						.append(new ComponentBuilder("...zmieniam zdanie!").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/paczka")).create())
						.create();
				pages.add(cb);
				successfullyLoaded = new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages);
			}
			successfullyLoaded.open(p);
			return;
		}
	}
	
	private static CustomBook successfullyLoaded;*/
	
}
