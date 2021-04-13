package me.przemovi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonElement;

import me.catzy44.resourcepack.translation.Translator;
import me.catzy44.tasks.ChunkyStartStopTask;
import me.catzy44.tasks.CustomBlocksManager;
import me.catzy44.tasks.TasksManager;
import me.przemovi.database.Config;
import me.przemovi.players.RPlayerMan;
import me.przemovi.resourcepack.ResourcePackManager;
import me.przemovi.struct.ChatMan;
import me.przemovi.struct.CustCommand;
import me.przemovi.struct.GuildTerrainShotMan;
import me.przemovi.utils.Alphabet;
import me.przemovi.utils.ClickEventRun;
import me.yasei.PlayerProfiles;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.milkbowl.vault.permission.Permission;

public class EndiManager extends JavaPlugin implements Listener {

	public static EndiManager plugin;
	public static Permission perms = null;
	
	public static void debug(String s) {
		log(s);
	}

	public void onDisable() {
		log(ChatColor.DARK_RED + "disabling...");

		ClickEventRun.stop();
		CustomBlocksManager.getInstance().stop();
		TasksManager.stopTasks();
		RPlayerMan.getInstance().shutdown();
		GuildTerrainShotMan.getInstance().stop();
		//NPCsManager.getInstance().shutdown();
		PlayerProfiles.getInstance().initialize();
		Config.save();
		Translator.dump();

		log(ChatColor.DARK_RED + "disabled.");
	}

	public void onEnable() {
		plugin = this;

		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();

		debug("Loading submodules...");
		Config.load();
		Alphabet.load();
		Translator.load();
		ResourcePackManager.start();
		RPlayerMan.getInstance().load();
		TasksManager.startTasks();
		AutoMessages.getInstance().start();
		ChatMan.getInstance().enable();
		ClickEventRun.init();
		
		JsonElement x = Config.getDatabase().get("servername");
		if(x != null && x.getAsString().equals("surv")) {
			ChunkyStartStopTask.getInstance().start();
			RandomTP.getInstance().start();
			Interface.build();
			//NPCsManager.getInstance().init();
			PlayerProfiles.getInstance().initialize();
			GuildTerrainShotMan.getInstance().start();
			//TODO CBM temp disabled /TEAM
			//CustomBlocksManager.getInstance().start();debug("[ok] CustomBlocksManager");
		}
		
		debug("Loading events...");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(ChatMan.getInstance(), this);
		pm.registerEvents(RPlayerMan.getInstance(), this);
		pm.registerEvents(Komunikaty.getInstance(), this);
		debug("[ok] Events");
		
		log(ChatColor.GREEN+"started.");
		
		new CustCommand("endiman") {
			@Override
			public void execute(Player sender, String[] args) {
				for(CustCommand em : all) {
					String cmd = em.name[0] +" "+(em.name.length == 1 ? "" : em.name[1]);
					BaseComponent[] c = new ComponentBuilder().append(" ")
						.append(cmd)
							.color(ChatColor.GRAY)
							.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd))
						.create();
					sender.spigot().sendMessage(c);
				}
			}
		};
		
		new CustCommand("discord") {
			@Override
			public void execute(Player sender, String[] args) {
				BaseComponent[] c = new ComponentBuilder().append("  ")
						.append("Discord: ")
						.color(ChatColor.GRAY)
					.append(Translator.translate("endi.2d.chat.other.link"))
						.reset()
						.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8zwUaF6"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby otworzyć Discorda")))
					.create();
					sender.spigot().sendMessage(c);
			}
		};
		
		new CustCommand("endiman","stopy") {
			@Override
			public void execute(Player sender, String[] args) {
				ItemStack is = new ItemStack(Material.LEATHER_HORSE_ARMOR);
				ItemMeta meta = is.getItemMeta();
				meta.setCustomModelData(10051);
				is.setItemMeta(meta);
				sender.getInventory().setBoots(is);
			}
		};
		new CustCommand("endiman","paczka") {
			@Override
			public void execute(Player sender, String[] args) {
				Interface.getBook(0).open(sender);
			}
		};
		new CustCommand("endiman","quit") {
			@Override
			public void execute(Player sender, String[] args) {
				sender.kickPlayer("");
			}
		};
		new CustCommand("endiman","book") {
			@Override
			public void execute(Player sender, String[] args) {
				int id = Integer.parseInt(args[1]);
				Interface.getBook(id).open(sender);
			}
		};
		new CustCommand("endiman","retry") {
			@Override
			public void execute(Player sender, String[] args) {
				Interface.getBook(0).open(sender);
			}
		};
		new CustCommand("endiman","locked") {
			@Override
			public void execute(Player sender, String[] args) {
				sender.kickPlayer("");
			}
		};
		new CustCommand("endiman","reload") {
			@Override
			public void execute(Player p, String[] args) {
				p.sendMessage("Reloading config and hashes...");
				Config.load();
				ResourcePackManager.forceAsyncConfigReload(new Runnable() {
					@Override
					public void run() {
						if(args == null || args.length == 1) {
							p.sendMessage("Reapplying all resourcepacks for "+Bukkit.getOnlinePlayers().size()+" players...");
							RPlayerMan.getInstance().reapplyResourcePacks();
						} else {
							p.sendMessage("Reapplying resourcepack #"+args[1]+" for "+Bukkit.getOnlinePlayers().size()+" players...");
							RPlayerMan.getInstance().reapplyResourcePacks(Integer.parseInt(args[1]));
						}
						p.sendMessage("Done!");
					}
				}, new Runnable() {
					@Override
					public void run() {
						p.sendMessage("FAILED!");
					}
				});
				
			}
		};
		new CustCommand("endiman","regentershotsnow") {
			@Override
			public void execute(Player sender, String[] args) {
				GuildTerrainShotMan.getInstance().regenerateAllNow();
			}
		};
		new CustCommand("endiman","testautomessages") {
			@Override
			public void execute(Player sender, String[] args) {
				for(BaseComponent[] s : AutoMessages.getInstance().getMessages()) {
					sender.spigot().sendMessage(s);
				}
			}
		};
		new CustCommand("endiman","playtest") {
			@Override
			public void execute(Player sender, String[] args) {
				World w = Bukkit.getWorld("world");
				w.playSound(w.getSpawnLocation(), "custom.ambient.outdoors.quiet", SoundCategory.AMBIENT, Float.valueOf(args[1]), 1.0f);
			}
		};
		new CustCommand("endiman","setspawn") {
			@Override
			public void execute(Player sender, String[] args) {
				World w = Bukkit.getWorld("world");
				w.setSpawnLocation(sender.getLocation());
			}
		};
		
	}
	
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"[EndiManager] "+ChatColor.RESET+message);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String[] allargs = new String[args.length+1];
		allargs[0] = cmd.getName();
		for(int i = 0; i < args.length; i++) {
			allargs[i+1] = args[i];
		}
		CustCommand.execByCmd((Player)sender, allargs);
		return false;
	}

}