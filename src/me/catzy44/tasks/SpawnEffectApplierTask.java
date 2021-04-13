package me.catzy44.tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.przemovi.EndiManager;
import me.przemovi.players.RPlayer;
import me.przemovi.players.RPlayerMan;

public class SpawnEffectApplierTask extends BukkitRunnable{
	private static SpawnEffectApplierTask instance;

	public static SpawnEffectApplierTask getInstance() {
		if (instance == null) {
			instance = new SpawnEffectApplierTask();
		}
		return instance;
	}
	
	private ProtectedRegion spawn = null;
	/*private final float vol = 0.05f;
	private Map<RPlayer,Long> playing = new HashMap<RPlayer,Long>();*/
	
	public void start() {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		World world = Bukkit.getWorld("world");
		RegionManager regions = container.get(BukkitAdapter.adapt(world));
		spawn = regions.getRegion("spawn");
		
		
		
		this.runTaskTimerAsynchronously(EndiManager.plugin, 1, 4 * 20);
	}
	public void stop() {
		try {
			if (this == null || this.isCancelled()) {
				return;
			}
			this.cancel();
		} catch (Exception e) {
		}
	}
	@Override
	public void run() {
		var list = new ArrayList<RPlayer>();
		for(RPlayer rp : RPlayerMan.getInstance().getAllOnline()) {
			Location l = rp.getPlayer().getLocation();
			
			if(!spawn.contains(BukkitAdapter.asBlockVector(l))) {
				continue;
			}
			list.add(rp);
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(RPlayer rp : list) {
					rp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE,10*20, 0, true, false));
				}
			}
		}.runTaskLater(EndiManager.plugin, 1);
		//w.playSound(w.getSpawnLocation(), "custom.ambient.outdoors.quiet", 20f, 1.0f);
		/*World w = Bukkit.getWorld("world");
		 * for(RPlayer rp : RPlayerMan.getInstance().getAll()) {
			Location l = rp.getPlayer().getLocation();
			
			if(spawn.contains(BukkitAdapter.asBlockVector(l))) {
				if(!rp.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
					new BukkitRunnable() {
						@Override
						public void run() {
							rp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,10*20, 0, true, false));
						}
					}.runTaskLater(EndiManager.plugin, 1);
				}
				
				//in spawn
				if(!playing.containsKey(rp)) {
					//just entered spawn
					//rp.getPlayer().playSound(w.getSpawnLocation(), "custom.ambient.outdoors", vol, 1.0f);
					if(!rp.isLoggedAndReady()) {
						continue;
					}
					playing.put(rp, System.currentTimeMillis());
					continue;
				}
				if(System.currentTimeMillis() - playing.get(rp) > 41000) {
					//keep playback!
					//rp.getPlayer().playSound(w.getSpawnLocation(), "custom.ambient.outdoors", vol, 1.0f);
					playing.put(rp, System.currentTimeMillis());
					continue;
				}
			} else {
				if(rp.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING) &&
						rp.getPlayer().getPotionEffect(PotionEffectType.SLOW_DIGGING) != null && 
						rp.getPlayer().getPotionEffect(PotionEffectType.SLOW_DIGGING).getDuration() >= 1000000) {
					new BukkitRunnable() {
						@Override
						public void run() {
							rp.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
						}
					}.runTaskLater(EndiManager.plugin, 1);
				}
				
				//not in spawn
				if(playing.containsKey(rp)) {
					//just leaved spawn
					rp.getPlayer().stopSound("custom.ambient.outdoors");
					rp.getPlayer().playSound(w.getSpawnLocation(), "custom.ambient.outdoors.fadeout", vol, 1.0f);
					playing.remove(rp);
					continue;
				}
			}
			
		}*/
	}
}
