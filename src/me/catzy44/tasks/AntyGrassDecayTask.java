package me.catzy44.tasks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

import me.przemovi.EndiManager;
import me.przemovi.RandomTP;

public class AntyGrassDecayTask implements Listener {
	private static AntyGrassDecayTask instance;
	public static AntyGrassDecayTask getInstance() {
		if(instance == null) {
			instance = new AntyGrassDecayTask();
		}
		return instance;
	}
	
	public void init() {
		EndiManager.plugin.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
	}
	
	@EventHandler
	public void onChange(BlockFadeEvent ev) {
		if(ev.getBlock().getType() != Material.GRASS_BLOCK) {
			return;
		}
		if(!RandomTP.getInstance().isInSpawnRegion(ev.getBlock().getLocation())) {
			return;
		}
		ev.setCancelled(true);
	}
}
