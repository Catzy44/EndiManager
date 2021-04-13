package me.przemovi.struct;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.przemovi.EndiManager;
import me.przemovi.utils.Alphabet;
import me.przemovi.utils.Utils;
import me.yasei.ChatProfile;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.guild.Guild;
import net.dzikoysk.funnyguilds.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import panda.std.Option;

public class ChatMan__ implements Listener {
	private static ChatMan__ instance = null;
	public static ChatMan__ getInstance() {
		if(instance == null) {
			instance = new ChatMan__();
			return instance;
		}
		return instance;
	}
	@SuppressWarnings("deprecation")
	public BaseComponent buildGuildTag(Guild g) throws Exception {
		
		BaseComponent tagComp = new TextComponent();

		String tag = g.getTag().toLowerCase();
		String alphaType = "normal";
		// silver rose normal gold dark_rose

		tagComp.addExtra("");
		tagComp.addExtra(new TranslatableComponent("space.-1"));

		int w = Alphabet.calculateModdedStringWidth(alphaType, tag) - 11;// counts all chars width's
		for (int i = 0; i < w; i++) {
			tagComp.addExtra("");
			tagComp.addExtra(new TranslatableComponent("space.-1"));
		}

		tagComp.addExtra("");// ending
		tagComp.addExtra(new TranslatableComponent("space.-" + (18 + w)));// neg space

		tagComp.addExtra(Alphabet.replace(alphaType, tag));// ending
		tagComp.addExtra(new TranslatableComponent("space.3"));// neg space

		String xx = """
				Gildia: {NAME} [{TAG}]
				Lider: {OWNER}
				Członkowie: {MEMBERS} ({ONLINEMEMBERS} online)
				Ranking: #{RANKING}
				pkt: {PKT}
				KDR: {LEVEL}

				LPM dla więcej info...""";

		xx = xx.replace("{NAME}", g.getName()).replace("{TAG}", g.getTag()).replace("{OWNER}", g.getOwner().getName()).replace("{RANKING}", g.getRank().getPosition() + "").replace("{PKT}", g.getRank().getPoints() + "")
				.replace("{LEVEL}", g.getRank().getKDR() + "").replace("{MEMBERS}", g.getMembers().size() + "").replace("{ONLINEMEMBERS}", g.getOnlineMembers().size() + "");

		tagComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(xx)));
		tagComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/funnyguilds:info "+g.getTag()));

		return tagComp;
	}

	private BaseComponent buildRankTag(Player player) {
		String group = EndiManager.perms.getPrimaryGroup(player);
		String cha = "";
		if (group.equals("gracz")) {
			cha = "";
		} else if (group.equals("wlasciciel")) {
			cha = "";
		} else if (group.equals("vip")) {
			cha = "";
		} else if (group.equals("svip")) {
			cha = "";
		} else if (group.equals("admin")) {
			cha = "";
		} else if (group.equals("moderator")) {
			cha = "";
		} else if (group.equals("technik")) {
			cha = "";
		}
		TextComponent groupComp = new TextComponent(cha);
		
		return groupComp;
	}

	private BaseComponent buildNickAndMessage(Player player, String messageStr, User user, Guild guild) throws Exception {
		//default -> for "gracz" rank and others
		ChatColor displaynameColor = ChatColor.of("#cbcbcb");
		ChatColor chatMessageColor = ChatColor.of("#cbcbcb");
		String cha = "";
		
		String group = EndiManager.perms.getPrimaryGroup(player);
		if (group.equals("wlasciciel")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		} else if (group.equals("vip")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		} else if (group.equals("svip")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		} else if (group.equals("admin")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		} else if (group.equals("moderator")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		} else if (group.equals("technik")) {
			cha = "";
			displaynameColor = ChatColor.WHITE;
			chatMessageColor = ChatColor.WHITE;
		}
		
		TextComponent container = new TextComponent("");
		
		TextComponent nick = new TextComponent(player.getDisplayName() + " ");
		nick.setColor(displaynameColor);
		if(Utils.isPluginInstalled("PlayerProfiles")) {
			nick.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatProfile.genChatProfile(player, user, guild))));
		}
		container.addExtra(nick);

		TextComponent arrow = new TextComponent(cha+" ");
		arrow.setColor(ChatColor.WHITE);
		container.addExtra(arrow);

		TextComponent message = new TextComponent(messageStr);
		message.setColor(chatMessageColor);
		container.addExtra(message);

		return container;
	}
	
	public static void broadcast(BaseComponent[] comps) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(comps);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerChat(AsyncPlayerChatEvent e) throws Exception {
		if (e.isCancelled()) {
			return;
		}
		Player player = e.getPlayer();
		ComponentBuilder message = new ComponentBuilder();

		Option<User> us = FunnyGuilds.getInstance().getUserManager().findByPlayer(player);
		User user = us != null && us.isPresent() ? us.get() : null;
		Guild g = user.hasGuild() ? user.getGuild().get() : null;
		if (g != null) {
			message.append(buildGuildTag(g));
		}

		message.append(buildRankTag(player));
		message.append(" ");
		message.append(buildNickAndMessage(player, e.getMessage(), user, g));

		broadcast(message.create());
		
		e.setCancelled(true);
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		ComponentBuilder message = new ComponentBuilder();
		message.append(new TextComponent(" "));
		message.append(new TextComponent(e.getPlayer().getName())).color(ChatColor.of("#D9D9D9"));
		
		e.setJoinMessage(null);
		broadcast(message.create());
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		ComponentBuilder message = new ComponentBuilder();
		message.append(new TextComponent(" "));
		message.append(new TextComponent(e.getPlayer().getName())).color(ChatColor.of("#D9D9D9"));
		
		e.setQuitMessage(null);
		broadcast(message.create());
	}
}
