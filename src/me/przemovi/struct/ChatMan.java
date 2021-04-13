package me.przemovi.struct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.przemovi.EndiManager;
import me.przemovi.players.RPlayer;
import me.przemovi.players.RPlayerMan;
import me.przemovi.utils.Alphabet;
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

public class ChatMan implements Listener {
	private static ChatMan instance = null;
	private static List<String> quietPlayers = new ArrayList<String>();
	
	public void enable() {
		new CustCommand("endiman","quiet") {
			@Override
			public void execute(Player sender, String[] args) {
				String a = args[1];
				if(a.equals("add")) {
					quietPlayers.add(a);
				}
			}
		};new CustCommand("endiman","tag") {
			@Override
			public void execute(Player sender, String[] args) {
				/*GuildRank g =FunnyGuilds.getInstance().getGuildManager().findByTag(args[0]).get().getRank();
				
				sender.sendMessage(g.getPoints()+" "+g.getPosition(DefaultTops.GUILD_POINTS_TOP)+1);*/
				Guild g =FunnyGuilds.getInstance().getGuildManager().findByTag(args[0]).get();
				List<Guild> gs = FunnyGuilds.getInstance().getGuildManager().getGuilds().stream().sorted(new Comparator<Guild>() {
					@Override
					public int compare(Guild o1, Guild o2) {
						return -Integer.compare(o1.getRank().getPoints(), o2.getRank().getPoints());
					}
				}).collect(Collectors.toList());
				int in = gs.indexOf(g)+1;
				sender.sendMessage(""+in);
			}
		};
	}
	
	public static ChatMan getInstance() {
		if (instance == null) {
			instance = new ChatMan();
			return instance;
		}
		return instance;
	}

	public BaseComponent buildGuildTag(Guild g) throws Exception {

		BaseComponent tagComp = new TextComponent();

		String tag = g.getTag().toLowerCase();
		String alphaType;
		// silver rose normal gold dark_rose
		
		// int p = g.getRank().getPosition(DefaultTops.GUILD_POINTS_TOP)+1;
		int p = FunnyGuilds.getInstance().getGuildManager().getGuilds().stream().sorted(new Comparator<Guild>() {
			@Override
			public int compare(Guild o1, Guild o2) {
				return -Integer.compare(o1.getRank().getPoints(), o2.getRank().getPoints());
			}
		}).limit(3).collect(Collectors.toList()).indexOf(g)+1;
		
		String frameColor;
		switch (p) {
		case 1:
			alphaType = "gold";
			frameColor = "gold_purple";
			break;
		case 2:
			alphaType = "silver";
			frameColor = "silver_blue";
			break;
		case 3:
			alphaType = "dark_rose";
			frameColor = "dark_rose_gray";
			break;
		default:
			alphaType = "normal";
			frameColor = "normal";
		}
		
		String tags = p > 3 ? "norm" : "top"+p;

		tagComp.addExtra(new TranslatableComponent("endi.2d.chat.guilds."+tags+"_left"));
		tagComp.addExtra(new TranslatableComponent("space.-1"));

		int w = Alphabet.calculateModdedStringWidth(alphaType, tag) - 11;// counts all chars width's
		for (int i = 0; i < w; i++) {
			tagComp.addExtra(new TranslatableComponent("endi.2d.chat.guilds."+tags+"_fill"));
			tagComp.addExtra(new TranslatableComponent("space.-1"));
		}

		tagComp.addExtra(new TranslatableComponent("endi.2d.chat.guilds."+tags+"_right"));// ending
		tagComp.addExtra(new TranslatableComponent("space.-" + (18 + w)));// neg space

		tagComp.addExtra(Alphabet.replace(alphaType, tag));// ending
		tagComp.addExtra(new TranslatableComponent("space.3"));// neg space

		{
			TextComponent c = new TextComponent();

			c.addExtra(new TranslatableComponent("space.-4"));
			c.addExtra(new TranslatableComponent("endi.chat.panels.guilds."+frameColor+"_left"));
			c.addExtra(new TranslatableComponent("space.-1"));
			c.addExtra(new TranslatableComponent("endi.chat.panels.guilds."+frameColor+"_right"));
			c.addExtra(new TranslatableComponent("space.-179"));
			
			int width = 176;
			
			{
				String li = "Gildia: {NAME} [{TAG}]".replace("{NAME}", g.getName()).replace("{TAG}", g.getTag());
				int textWidth = Alphabet.getStrW(li);
				
				c.addExtra(new TranslatableComponent("space."+(width/2-textWidth/2)));
				c.addExtra(li+"\n");
			}
			
			String xx = """
					󐀁Lider: {OWNER}
					󐀁Członkowie: {MEMBERS} ({ONLINEMEMBERS} online)
					󐀁Ranking: #{RANKING}
					󐀁pkt: {PKT}
					󐀁KDR: {LEVEL}
					
					""";

			// @noformat
			xx = xx
				.replace("{OWNER}", g.getOwner().getName())
				.replace("{RANKING}", p + "")
				.replace("{PKT}", g.getRank().getPoints() + "")
				.replace("{LEVEL}", g.getRank().getKDR() + "")
				.replace("{MEMBERS}", g.getMembers().size() + "")
				.replace("{ONLINEMEMBERS}", g.getOnlineMembers().size() + "");
			// @endnoformat

			c.addExtra(new TextComponent(xx));
			
			{
				String li = "LPM dla wiecej info...";
				int textWidth = Alphabet.getStrW(li);
				
				c.addExtra(new TranslatableComponent("space."+(width-textWidth-4)));
				c.addExtra(li);
			}

			tagComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(c).create())));
		}
		tagComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/funnyguilds:info " + g.getTag()));

		return tagComp;
	}

	private BaseComponent buildRankTag(Player player, String group) {
		TranslatableComponent s = new TranslatableComponent("endi.2d.chat.ranks." + group);
		s.setHoverEvent(null);
		return s;
	}

	private BaseComponent buildNick(Player player, User user, Guild guild) throws Exception {
		String group = EndiManager.perms.getPrimaryGroup(player);

		String arr = "endi.2d.chat.other.arr_silver";
		if (group.equals("vip") || group.equals("admin"))
			arr = "endi.2d.chat.other.arr_rose";
		if (group.equals("owner") || group.equals("svip"))
			arr = "endi.2d.chat.other.arr_gold";

		TextComponent container = new TextComponent("");

		// nick
		TextComponent nick = new TextComponent(player.getDisplayName() + " ");
		nick.setColor(ChatColor.WHITE);
		//if (Utils.isPluginInstalled("PlayerProfiles")) {
			nick.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatProfile.genChatProfile(player, user, guild))));
		//}
		nick.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName()+" "));
		container.addExtra(nick);

		// arrow
		TranslatableComponent arrow = new TranslatableComponent(arr);
		arrow.setColor(ChatColor.WHITE);
		container.addExtra(arrow);

		return container;
	}
	
	private BaseComponent buildMessage(String messageStr) throws Exception {
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', messageStr));
		message.setColor(ChatColor.WHITE);
		
		return message;
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void playerChat(AsyncPlayerChatEvent e) throws Exception {
		if (e.isCancelled()) {
			return;
		}
		e.setCancelled(true);
		
		Player player = e.getPlayer();
		
		if(!AuthMeApi.getInstance().isAuthenticated(player)) {
			player.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("endi.2d.chat.other.warn")).append(new TextComponent(" Musisz być zalogowany!")).create());
			return;
		}
		
		ComponentBuilder message = new ComponentBuilder();
		
		Option<User> us = FunnyGuilds.getInstance().getUserManager().findByPlayer(player);
		User user = us != null && us.isPresent() ? us.get() : null;
		Guild g = user.hasGuild() ? user.getGuild().get() : null;
		if (g != null) {
			message.append(buildGuildTag(g));
		}
		
		String group = EndiManager.perms.getPrimaryGroup(player);
		
		message.append(buildRankTag(player,group)).reset();
		message.append(" ");
		message.append(buildNick(player, user, g)).reset();
		message.append(" ");
		message.append(buildMessage(e.getMessage()));

		BaseComponent bc[] = message.create();
		for (Player p : Bukkit.getOnlinePlayers()) {
			RPlayer rp = RPlayerMan.getInstance().getByPlayer(p);
			if(!rp.isLoggedAndHasResourcepackLoaded()) {
				continue;
			}
			rp.sendMessage(bc);
		}
		
		//log message
		EndiManager.log("[CHAT] ["+group+"] "+player.getName()+" > "+e.getMessage());
	}
}
