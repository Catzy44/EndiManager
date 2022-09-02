package me.przemovi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

public class Komunikaty implements Listener {
	int delay = 200;
	
	private static Komunikaty instance;
	public static Komunikaty getInstance() {
		if(instance == null) {
			instance = new Komunikaty();
		}
		return instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("changedelay")) {
			if (p.hasPermission("komunikaty.changedelay")) {
				delay = Integer.parseInt(args[0]);
			}
		} else if (cmd.getName().equalsIgnoreCase("rangi")) {
			if (args.length == 0 || args[0] == null) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 RANGI &8«&6*&8»&8&m-----------&8«&6*&8»"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- Rangi dostępne na serwerze:"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &6VIP"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &6SuperVIP"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &5PVPMember"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &4You&fTuber"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- Informacje o danej randze: /rangi <nazwa>"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- Informacje o kitach: /kity <nazwa> lub /zestawy <nazwa>"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- zakupisz tutaj: &3endimc.pl"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 RANGI &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("vip")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- koszt rangi to &320 &7brutto."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- zakupisz tutaj: &3endimc.pl"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- ranga jest na &360 dni."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3/kit vip"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/hat"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3VIPRoom &7na serwerze."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- gracze z ta ranga posiadaja zloty przedrostek &e[&6VIP&e] &7na czacie.."));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Ranga chwilowo niedostępna"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("svip") || args[0].equalsIgnoreCase("supervip")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 Super VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- koszt rangi to &330 &7brutto."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- zakupisz tutaj: &3endimc.pl"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- ranga jest na &390 dni."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3/kit svip"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/hat"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/enderchest"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3VIPRoom &7na serwerze."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- gracze z ta ranga posiadaja zloty przedrostek &e[&6VIP&e] &7na czacie.."));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Ranga chwilowo niedostępna"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 Super VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("pvpmember") || args[0].equalsIgnoreCase("pvp")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 PVPMember &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- koszt rangi to &350 &7brutto."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- zakupisz tutaj: &3endimc.pl"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- ranga jest na &3150 dni."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3/kit pvp"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/hat"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/enderchest"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3VIPRoom &7na serwerze."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- gracze z ta ranga posiadaja przedrostek &d[&5PvpMember&d] &7na czacie.."));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Ranga chwilowo niedostępna"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 PVPMember &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("yt") || args[0].equalsIgnoreCase("youtuber")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8» &4You&fTuber &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- aby dostać rangę musisz mieć minimum 1000 subów"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- musisz też nagrać gameplay lub reklamę serwera."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- jeżeli sperłniasz te warunki to zpaytaj administracji o rangę."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3/kit yt"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/hat"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do komendy &3/enderchest"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- otrzymujesz dostep do &3VIPRoom &7na serwerze."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- gracze z ta ranga posiadaja przedrostek &e[&4You&fTuber&e] &7na czacie."));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Ranga chwilowo niedostępna"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8» &4You&fTuber &8«&6*&8»&8&m-----------&8«&6*&8»"));
			}

		} else if (cmd.getName().equalsIgnoreCase("kity") || cmd.getName().equalsIgnoreCase("zestawy")) {
			if(args.length == 0 || args[0] == null) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAWY &8«&6*&8»&8&m-----------&8«&6*&8»"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- Zestawy dostępne na serwerze:"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &fgracz"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &6vip"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &6svip"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &5pvp"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- &4y&ft"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- Informacje o danym zestawie: /zestawy <nazwa>"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7- zakupisz tutaj: &3endimc.pl"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAWY &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("gracz")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW GRACZ &8«&6*&8»&8&m-----------&8«&6*&8»"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Kamienny miecz"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Kamienna łopata"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Kamienny kilof"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Kamienna siekiera"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Skórzana zbroja"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &716 upieczonych steków"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw możesz wziąść raz na 24 godziny"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Aby otrzymać zestaw wpisz /kit gracz"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW GRACZ &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("vip")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy miecz"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy kilof"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowa zbroja"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 złote jabłka"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw możesz wziąść raz na tydzień"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Aby otrzymać zestaw wpisz /kit vip"));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw chwilowo niedostępny"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("svip")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW SUPER VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy miecz S4,F2"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy kilof E4,F3,U1"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowa zbroja U3,P2"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 złote jabłka"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 koxy"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw możesz wziąść raz na tydzień"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Aby otrzymać zestaw wpisz /kit svip"));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw chwilowo niedostępny"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW SUPER VIP &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("pvp")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW PVP MEMBER &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy miecz S5,F2"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy kilof E4,F3,U1"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowa zbroja U4,P3"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 złote jabłka"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 koxy"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw możesz wziąść raz na tydzień"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Aby otrzymać zestaw wpisz /kit pvp"));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw chwilowo niedostępny"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW PVP MEMBER &8«&6*&8»&8&m-----------&8«&6*&8»"));
			} else if (args[0].equalsIgnoreCase("yt") || args[0].equalsIgnoreCase("youtuber")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW YOUTUBER &8«&6*&8»&8&m-----------&8«&6*&8»"));
				/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy miecz S5,F2"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowy kilof E4,F3,U1"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Diamentowa zbroja U4,P3"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 złote jabłka"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &74 koxy"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw możesz wziąść raz na tydzień"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Aby otrzymać zestaw wpisz /kit yt"));*/
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Zestaw chwilowo niedostępny"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ZESTAW YOUTUBER &8«&6*&8»&8&m-----------&8«&6*&8»"));
			}
		} else if (cmd.getName().equalsIgnoreCase("komendy")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 POMOCNE KOMENDY &8«&6*&8»&8&m-----------&8«&6*&8»"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/spawn &7- teleport na spawn."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/tpa [nick] &7- prosba o teleportacje do danego gracza."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/rtp &7- teleport na losowe koordy."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/warp &7- lista dostepnych warpow."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/sethome &7- tworzenie domu &7/ &3/home &7- powrot do domu."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/m [nick] [wiad.] &7- prywatna wiadomosc do gracza."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/helpop [wiad.] &7- wiadomosc do administracji serwera."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/skin [nick skina] &7- ustawia skina."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/gildia &7- lista komend gildi."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/list &7- lista graczy online."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/kit gracz &7- startowy zestaw gracza."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/administracja &7- lista administracji serwera."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/afk &7- zmiana statusu na afk."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/zestawy &7- lista dostepnych kitow."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/www &7- adres strony serwera."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3/rangi &7- rangi dostępne na serwerze."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 POMOCNE KOMENDY &8«&6*&8»&8&m-----------&8«&6*&8»"));
		} else if (cmd.getName().equalsIgnoreCase("administracja")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ADMINISTRACJA &8«&6*&8»&8&m-----------&8«&6*&8»"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &4Wlasciciel&8: &7PrzemoVi &8& &7Yasei_WiLQ"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8» &3Technik&8:&slepysniper &8& &7PLPatPL"));
			//p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cAdmin&8: "));
			// p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8» &dPomocnik&8:
			// &7ProCosta"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 ADMINISTRACJA &8«&6*&8»&8&m-----------&8«&6*&8»"));
		} else if (cmd.getName().equalsIgnoreCase("www")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 STRONA SERWERA &8«&6*&8»&8&m-----------&8«&6*&8»"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &3endimc.pl"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6 STRONA SERWERA &8«&6*&8»&8&m-----------&8«&6*&8»"));
		}
		return true;
	}

	/*try {
	Thread.sleep(delay);
} catch (InterruptedException e) {
}
for (int i = 0; i < 20; i++) {
	p.sendMessage("     ");
}*/


//p.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("space.48")).append("Milej gry zyczy administracja serwera.").color(ChatColor.GOLD).create());

/*p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6&lendimc.pl«&6*&8»&8&m-----------&8«&6*&8»"));
p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Witaj &3" + p.getName() + " &7na serwerze &3EndiMc!"));
p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Pomocne komendy &3/komendy"));
p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Spodobal Ci sie serwer? Polec nas znajomym."));
p.spigot().sendMessage(new ComponentBuilder("» ").color(ChatColor.DARK_GRAY)
		.append("Wyraz swoja opinie: ").color(ChatColor.GRAY)
		.append("MPCForum").color(ChatColor.DARK_GRAY)
			.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mpcforum.pl/topic/1665259-1122mody-endimc/"))
			.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby przejść do naszego tematu na MPCForum")))
		.append(" ")
		.append("CraftPortal").color(ChatColor.DARK_GRAY).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://spigotmc.org"))
			.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://craftportal.pl/forum/index.php?/topic/105626-1122100snpmodygildie-endimc-techniczno-magicznie/"))
			.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby przejść do naszego tematu na CraftPortal")))
		.create());
p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &6Milej gry zyczy administracja serwera."));
p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8«&6*&8»&8&m-----------&8«&6*&8»&6&lendimc.pl«&6*&8»&8&m-----------&8«&6*&8»"));
p.sendMessage("");*/
}
