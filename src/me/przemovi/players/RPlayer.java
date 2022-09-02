package me.przemovi.players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LoginEvent;
import me.catzy44.resourcepack.translation.Translator;
import me.przemovi.EndiManager;
import me.przemovi.Interface;
import me.przemovi.database.Config;
import me.przemovi.resourcepack.ResourcePack;
import me.przemovi.resourcepack.ResourcePackManager;
import me.przemovi.utils.CustomBook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class RPlayer {
	private String name;
	private Player player;
	private int packId = 0;
	
	public long lastRTPUsage = -1;
	
	private boolean fresh = true;

	long joinedTime = 0;
	public RPlayer(Player player) {
		this.player = player;
		name = player.getName();
		joinedTime = System.currentTimeMillis();
		if (Config.getDatabase().get("players").getAsJsonObject().get(name) != null) {
			packId = Config.getDatabase().get("players").getAsJsonObject().get(name).getAsInt();
		}
		
		if(packId == 0) {
			packId = 4;
		}
	}
	
	public boolean isResourcepackLoaded() {
		//STATUS IS NULL IF PLUGIN WAS RELOADED!
		if (status != null && status == Status.SUCCESSFULLY_LOADED) {
			return true;
			// not asking for login players with not yet loaded resourcepack
		}
		return false;
	}
	public boolean isLoggedIn() {
		return AuthMeApi.getInstance().isAuthenticated(player);
	}
	public boolean isLoggedAndHasResourcepackLoaded() {
		return isLoggedIn() && isResourcepackLoaded();
	}
	
	
	public void greetPlayer() {
		//this function kicks in in two scenarios, but ONLY ONCE! :
		//JOINED->LOGGED_IN->TXT_LOADED->GREET_PLAYER
		//JOINED->TXT_LOADED->LOGGED_IN->GREET_PLAYER
		
		player.spigot().sendMessage(Translator.translate("endi.2d.ui.dialogs.mascot"));
		player.spigot().sendMessage(new ComponentBuilder()
				.append(Translator.translate("space.48"))
				.append("Witaj ").color(ChatColor.GRAY)
				.append(player.getName()).color(ChatColor.DARK_AQUA)
				.append(" na serwerze ").color(ChatColor.GRAY)
				.append("EndiMC").color(ChatColor.DARK_AQUA)
				.append("!").color(ChatColor.GRAY)
				.create());
		player.spigot().sendMessage(new ComponentBuilder().append(Translator.translate("space.48"))
				.append("Pomocne komendy ").color(ChatColor.GRAY)
				.append("/komendy").color(ChatColor.DARK_AQUA)
				.create());
		player.spigot().sendMessage(new ComponentBuilder()
				.append(Translator.translate("space.48"))
				.append("Spodobał Ci się serwer? Poleć nas znajomym.").color(ChatColor.GRAY)
				.create());
		player.spigot().sendMessage(new ComponentBuilder("")
				.append(Translator.translate("space.48"))
				.append("Wyraz swoją opinię: ").color(ChatColor.GRAY)
				.append("MPCForum").color(ChatColor.DARK_GRAY)
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mpcforum.pl/topic/1665259-1122mody-endimc/"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby przejść do naszego tematu na MPCForum")))
				.append(" ")
				.append("CraftPortal").color(ChatColor.DARK_GRAY).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://spigotmc.org"))
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://craftportal.pl/forum/index.php?/topic/105626-1122100snpmodygildie-endimc-techniczno-magicznie/"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby przejść do naszego tematu na CraftPortal")))
				.create());
		player.sendMessage("");
	}
	
	public void installResourcepack(int id) {
		packId = id;
		Config.getDatabase().get("players").getAsJsonObject().addProperty(name, id);
		reapplyResourcepack();
	}
	
	//AUTHME!!!
	public void loggedInEvent(LoginEvent event) {
		player.spigot().sendMessage(
				new ComponentBuilder()
				.append(Translator.translate("endi.2d.chat.other.success"))
				.append(new TextComponent(" Pomyślnie zalogowano"))
				.create());
		
		if(isResourcepackLoaded()) {
			greetPlayer();
		}
	}
	//VANILLA (BEFORE AUTHME LOGIN)
	public void joinedEvent(PlayerJoinEvent event) {
		fresh = true;
		
		//if(!player.isOp()) {//player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 50, false, false));//}
		reapplyResourcepack();
		
		{
			ComponentBuilder message = new ComponentBuilder();
			message.append(new TextComponent(" "));
			message.append(new TextComponent(name)).color(ChatColor.of("#D9D9D9"));

			event.setJoinMessage(null);
			
			BaseComponent[] s = message.create();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if(!isLoggedAndHasResourcepackLoaded()) {
					System.out.println("MESSAMESS <----------------");
					continue;
				}
				if(p == player) {
					continue;
				}
				sendMessage(s);
			}
		}
	}
	public void quitEvent(PlayerQuitEvent event) {
		ComponentBuilder message = new ComponentBuilder();
		message.append(new TextComponent(" "));
		message.append(new TextComponent(name)).color(ChatColor.of("#D9D9D9"));

		event.setQuitMessage(null);
		
		BaseComponent bc[] = message.create();
		for (Player p : Bukkit.getOnlinePlayers()) {
			RPlayer rp = RPlayerMan.getInstance().getByPlayer(p);
			if(!rp.isLoggedAndHasResourcepackLoaded()) {
				continue;
			}
			rp.sendMessage(bc);
		}
		
		player = null;
		status = null;
	}
	
	public void reapplyResourcepack() {
		applyResourcepack(packId);
	}
	
	private void applyResourcepack(int id) {
		ResourcePack x = ResourcePackManager.get(id);
		/*BaseComponent[] c = new ComponentBuilder("&8&m---------------------\n").color(ChatColor.DARK_RED).create();
		String m = ComponentSerializer.toString(c);*/
		String m = ChatColor.translateAlternateColorCodes('&', """
				
				
				
				&8&m----------------------------------&r
				
				&6Witaj przybyszu!
				
				&fAby zacząć swoją przygodę
				na serwerze &5EndiMC &fmusisz już tylko
				zaakceptować naszą paczkę zasobów!
				&7Jej rozmiar to około 10MB.
				
				&3Jesteś gotowy?
				&fKliknij przycisk po lewej stronie ;)
				
				&8&m----------------------------------&r
				
				&cUwaga! Ten komunikat możemy wyświetlić tylko raz.
				Jeżeli nie zaakceptujesz teraz naszej paczki zasobów
				bez której gra na naszym serwerze jest problematyczna
				żeby zainstalować ją w przyszłości będziesz musiał
				zrobić to ręcznie poprzez menu gry:
				lista serwerów->ten serwer->edytuj->paczki zasobów
				
				""");
		player.setResourcePack(x.getUrl()+"#"+x.getHash(), x.getHashBytes(), m, !player.isOp());
	}
	
	private Status status = null;
	public void statusChanged(Status s) {
		this.status = s;
		if(status == Status.SUCCESSFULLY_LOADED) {
			if(fresh) {
				fresh = false;
				
				if(isLoggedIn()) {
					greetPlayer();
				}
			}
		} else if(status == Status.FAILED_DOWNLOAD) {
			player.spigot().sendMessage(new ComponentBuilder().append(Translator.translate("endi.2d.chat.other.warn")).append(new TextComponent(" Wczytywanie paczki nie powiodło się! Spróbuj wyjść i wejść a jeżeli problem nie ustąpi to skontaktuj się z administracją!")).create());
		}
		EndiManager.log("Resporcepack status changed " + player.getName() + " -> " + s.name());
		
	}

	public void showBook(int i) {
		if (player == null || !player.isOnline()) {
			return;
		}
		CustomBook book = Interface.getBook(i);
		book.open(player);
	}

	/* G&S */

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPackId() {
		return packId;
	}
	
	public void sendMessage(BaseComponent... comp) {
		sendMessage(player,comp);
	}
	
	public static void sendMessage(Player p, BaseComponent... comp) {
		/*for(int i = 0; i < comp.length; i++) {
			BaseComponent c = comp[i];
			if(!(c instanceof TranslatableComponent)) {
				continue;
			}
			TranslatableComponent tc = (TranslatableComponent) c;
			String transname = tc.getInsertion();
			System.out.println(transname);
		}*/
		p.spigot().sendMessage(comp);
	}

}
