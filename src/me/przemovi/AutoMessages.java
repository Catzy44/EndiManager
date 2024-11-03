package me.przemovi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.catzy44.resourcepack.translation.Translator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class AutoMessages {

	public BukkitTask ChatTask;
	int ChatMessagesTimer = 60*30;
	int CurChatMes = 0;
	
	private static AutoMessages instance;
	public static AutoMessages getInstance() {
		if(instance == null) {
			instance = new AutoMessages();
		}
		return instance;
	}
	
	/*private TextComponent cl(String u) {
		TextComponent tc=  new TextComponent();
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,u));
		return tc;
	}*/
	
	private List<BaseComponent[]> messages;
	
	public List<BaseComponent[]> getMessages() {
		return messages;
	}

	public void start() {
		messages = new ArrayList<BaseComponent[]>();//.append(c("endi.2d.chat.other.link"))
		
		messages.add(new ComponentBuilder().append("  ")
				.append("Kupując rangę ")
					.color(ChatColor.GRAY)
				.append(c("endi.2d.chat.ranks.vip"))
				.reset()
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://endimc.pl"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby zobaczyć więcej")))
				.append(" wspierasz serwer!")
					.reset()
					.color(ChatColor.GRAY)
				.create());
		
		messages.add(new ComponentBuilder().append("  ")
				.append("TeamSpeak3: ")
					.color(ChatColor.GRAY)
				.append(c("endi.2d.chat.other.link"))
					.reset()
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://endimc.pl"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby zobaczyć więcej")))
				.create());
		
		messages.add(new ComponentBuilder().append("  ")
				.append("Discord: ")
					.color(ChatColor.GRAY)
				.append(c("endi.2d.chat.other.link"))
					.reset()
					.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8zwUaF6"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby otworzyć Discorda")))
				.create());
		//messages.add(new ComponentBuilder().append("  ").color(ChatColor.GRAY).append("Swoje statystyki możesz zobaczyć na naszej stronie ").append(cl("https://endimc.pl/stats")).create());
		messages.add(new ComponentBuilder().append("  ").append("Wszelkie błędy prosimy zgłaszac do administracji!").color(ChatColor.GRAY).create());
		messages.add(new ComponentBuilder().append("  ").append("Nie ").color(ChatColor.GRAY).color(ChatColor.DARK_RED).append("cheatuj ").color(ChatColor.GRAY).append("i tak Cie ").color(ChatColor.DARK_RED).append("złapiemy").color(ChatColor.GRAY).append("!").create());
		
		messages.add(new ComponentBuilder().append("  ")
				.append("Informacje o rangach premium: ")
					.color(ChatColor.GRAY)
				.append(c("endi.2d.chat.other.click"))
					.reset()
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rangi"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby zobaczyć więcej")))
				.create());
		messages.add(new ComponentBuilder().append("  ")
				.append("Informacje o randze YouTubera: ")
					.color(ChatColor.GRAY)
				.append(c("endi.2d.chat.other.click"))
					.reset()
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rangi yt"))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby zobaczyć więcej")))
				.create());
		/*messages.add(new ComponentBuilder().append("  ")
				.append("Wszelkie pomocne komendy &3/komendy").create());*/
		//messages.add(new ComponentBuilder().append("  ").color(ChatColor.GRAY).append("Chciałbyś ustawić sobie swojego własnego skina z pliku? Użyj panelu gracza na naszej stronie https://endimc.pl!").create());
		//messages.add(new ComponentBuilder().append("  ").color(ChatColor.AQUA).append("chcesz mieć info z serwera na swoim pulpicie? Sprawdź nasze gadżety pulpitu: http://endimc.pl?p=gadzety").create());
		
		ChatMessagesTask();
	}
	
	public BaseComponent c(String s) {
		return Translator.translate(s);
	}

	public void ChatMessagesTask() {
		this.ChatTask = new BukkitRunnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers().size() >= 1) {
					int messageSize = messages.size();
					if (CurChatMes < messageSize) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.spigot().sendMessage(messages.get(CurChatMes));
						}
						CurChatMes++;
						return;
					} else {
						CurChatMes = 0;
					}
				}

			}
		}.runTaskTimer(EndiManager.plugin, 20 * this.ChatMessagesTimer, 20 * this.ChatMessagesTimer);
	}
}