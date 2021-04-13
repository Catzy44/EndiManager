package me.catzy44.tasks;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.przemovi.EndiManager;
import me.przemovi.players.RPlayer;
import me.przemovi.players.RPlayerMan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.milkbowl.vault.economy.Economy;

public class ActionBarSenderTask extends BukkitRunnable {
	private static ActionBarSenderTask instance;

	public static ActionBarSenderTask getInstance() {
		if (instance == null) {
			instance = new ActionBarSenderTask();
		}
		return instance;
	}
	private ProtectedRegion spawn = null;
	private Economy econ;
	
	public void start() {
		RegisteredServiceProvider<Economy> rsp = EndiManager.plugin.getServer().getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		World world = Bukkit.getWorld("world");
		RegionManager regions = container.get(BukkitAdapter.adapt(world));
		spawn = regions.getRegion("spawn");
		
		this.runTaskTimerAsynchronously(EndiManager.plugin, 0, 1 * 20);
	}
	public void stop() {
		econ = null;
		try {
			if (this == null || this.isCancelled()) {
				return;
			}
			this.cancel();
		} catch (Exception e) {
		}
	}
	final int charWidth = 6;
	@Override
	public void run() {
		try {
			for (RPlayer rp : RPlayerMan.getInstance().getAllOnline()) {
				if(!rp.isLoggedAndHasResourcepackLoaded()) {
					continue;
				}
				Location l = rp.getPlayer().getLocation();
				
				if(!spawn.contains(BukkitAdapter.asBlockVector(l))) {
					continue;
				}
				
				if(rp.getPlayer().getGameMode() != GameMode.SURVIVAL) {
					continue;
				}
				
				
				int playereco = (int) econ.getBalance(rp.getPlayer());
				
				int digitsCount = (int) (Math.log10(playereco)+1);
				int spaceTakenByDigits = digitsCount * charWidth;

				TextComponent mess = new TextComponent();
				mess.addExtra(new TranslatableComponent("space." + (74-spaceTakenByDigits)));

				TextComponent tc = new TextComponent("");
				tc.setFont("minecraft:eco_counter");
				tc.setColor(ChatColor.of(new Color(254, 253, 4)));

				{
					TextComponent x = new TextComponent("");
					x.addExtra(playereco+"");

					TextComponent space = new TextComponent(" ");
					space.setFont("minecraft:default");
					x.addExtra(space);

					tc.addExtra(x);
				}
				tc.addExtra("ꢤ");// rubin icon
				mess.addExtra(tc);
				{
					TextComponent space = new TextComponent("");
					space.setFont("minecraft:default");
					mess.addExtra(space);
				}

				rp.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, mess);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
}
}
