package me.przemovi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.meta.BookMeta.Generation;

import me.przemovi.utils.CustomBook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Interface {
	
	public static CustomBook getBook(int id) {
		return books.containsKey(id) ? books.get(id) : null;
	}
	private static Map<Integer,CustomBook> books = new HashMap<Integer,CustomBook>();
	private static final String cmd = "endiman";
	
	public static void build() {
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
			
			books.put(0,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			
			BaseComponent cb[] = new ComponentBuilder("Wygląda na to że masz w grze zablokowane serwerowe resourcepacki.\n\n")
					.append("Teraz aby włączyć resourcepack musisz na liście serwerów wejść w ten serwer, kliknąć \"edytuj\" i zezwolić na serwerowe resourcepacki.").underlined(false)
					.append("\n").underlined(false)
					.append(new ComponentBuilder("Do listy serwerów!\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" locked")).create())
					//.append(new ComponentBuilder("jednak nie chce go instalować, nie pytaj ponownie!\n").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/texturepack disable")).create())
					.create();
			pages.add(cb);
			
			books.put(1,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			
			BaseComponent cb[] = new ComponentBuilder("Pobieranie paczki się nie powiodło.\n\n")
					.append("Spróbuj ponownie a jak znowu się nie uda to skontaktuj się z administracją.").underlined(false)
					.append("\n\n\n\n\n").underlined(false)
					.append(new ComponentBuilder("Spróbuj ponownie\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" retry")).create())
					.append(new ComponentBuilder("Kontakt z administracją\n").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" book 4")).create())
					.create();
			pages.add(cb);
			
			books.put(2,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			
			BaseComponent cb[] = new ComponentBuilder("Instalacja paczki przebiegła pomyślnie.\n\n")
					.append("Jeżeli będziesz chciał zmienić paczkę na wersję bez/z SF to możesz to w każdej chwili zrobić za pomocą /paczka").underlined(false)
					.append("\n\n\n\n").underlined(false)
					.append(new ComponentBuilder("Koniec\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "")).create())
					.append(new ComponentBuilder("...zmieniam zdanie!").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/paczka")).create())
					.create();
			pages.add(cb);
			
			books.put(3,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			
			BaseComponent cb[] = new ComponentBuilder("Kontakt z administracją\n\n\n\n")
					.append("Discord: ").color(ChatColor.BLUE)
					.append("[klik!]\n").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8zwUaF6")).color(ChatColor.BLUE).underlined(true)
					.append("TeamSpeak: ").underlined(false).color(ChatColor.DARK_GRAY)
					.append("endimc.pl")
					.append("Lub pisz na chacie do:\n-PrzemoVi\n-Yasei_")
					.create();
			pages.add(cb);
			
			books.put(4,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
		{
			List<BaseComponent[]> pages = new ArrayList<>();
			
			BaseComponent cb[] = new ComponentBuilder("Niestety nie możesz wejść na serwer bez naszego resourcepacka.\n")
					.append("").underlined(false)
					.append("\n\n\n").underlined(false)
					.append(new ComponentBuilder("Autoinstalacja\n").underlined(true).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/paczka")).create())
					.append(new ComponentBuilder("Wychodzę\n").underlined(true).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+cmd+" quit")).create())
					.create();
			pages.add(cb);
			
			books.put(5,new CustomBook(Generation.TATTERED, "titletest", "Catzy44", pages));
		}
	}
}
