package me.catzy44.tasks;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.popcraft.chunky.ChunkyProvider;

import me.przemovi.EndiManager;
import net.md_5.bungee.api.chat.BaseComponent;

public class ChunkyStartStopTask implements Listener {
	private static ChunkyStartStopTask me;
	public static ChunkyStartStopTask getInstance() {
		if(me == null) {
			me = new ChunkyStartStopTask();
		}
		return me;
	}
	BukkitRunnable checker = null;
	BaseComponent[] c;
	public void start() {
		EndiManager.plugin.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
		/*new CustCommand("endiman","pause") {
			@Override
			public void execute(Player sender, String[] args) {
				Map<String, GenerationTask> tasks = ChunkyProvider.get().getGenerationTasks();
				for(String s : tasks.keySet()) {
					GenerationTask gt = tasks.get(s);
					gt.stop(false);
					task = gt;
				}
			}
		};*/
		/*new CustCommand("endiman","gen","pause") {
			@Override
			public void execute(Player sender, String[] args) {
				paused = true;
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "chunky pause");
			}
		};
		c  = new ComponentBuilder()
				.append(new TranslatableComponent("endi.2d.chat.other.warn"))
				.append(new TextComponent(" Pregenerowanie świata, jeżeli to widzisz to koniecznie ")).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/endiman gen pause")).create();
		checker = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.spigot().sendMessage(c);
				}
			}
		};
		checker.runTaskTimerAsynchronously(EndiManager.plugin, 0, 20*30);*/
	}
	
	private boolean paused = false;
	private void rethink() {
		//count only admins, i <3 java8 streams
		int c = Bukkit.getOnlinePlayers().stream().filter(u->{
			return !u.hasPermission("endiman.admin");
		}).collect(Collectors.toList()).size();
		
		paused = ChunkyProvider.get().getGenerationTasks().size() == 0;
		
		if(c == 0) {
			if(!paused) {
				return;
			}
			paused = false;
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "chunky continue");
		} else {
			if(paused) {
				return;
			}
			paused = true;
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "chunky pause");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				rethink();
			}
		}.runTaskLater(EndiManager.plugin, 2);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				rethink();
			}
		}.runTaskLater(EndiManager.plugin, 2);
	}
}
