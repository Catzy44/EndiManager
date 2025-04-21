package me.przemovi;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonObject;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.przemovi.database.Config;
import me.przemovi.players.RPlayer;
import me.przemovi.players.RPlayerMan;
import me.przemovi.struct.CustCommand;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class RandomTP implements Listener{
	private static RandomTP self;
	
	public static RandomTP getInstance() {
		if(self == null) {
			self = new RandomTP();
		}
		return self;
	}
	
	private boolean guildsEnabled() {
		return EndiManager.plugin.getServer().getPluginManager().isPluginEnabled("FunnyGuilds");
	}
	
	private final int range = 5000;
	private Random r = new Random();
	private ProtectedRegion spawn = null;
	private net.dzikoysk.funnyguilds.guild.RegionManager rm = null;
	private Location spawnLoc;
	
	public boolean isInSpawnRegion(Location l) {
		return spawn.contains(BukkitAdapter.asBlockVector(l));
	}
	
	public void start() {
		EndiManager.plugin.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
		
		{
			JsonObject config = Config.getDatabase();
			JsonObject rtp = config.get("rtp").getAsJsonObject();
			JsonObject spawn = rtp.get("spawn").getAsJsonObject();
			spawnLoc = new Location(
					Bukkit.getWorld(spawn.get("world").getAsString()),
					spawn.get("x").getAsFloat(),
					spawn.get("y").getAsFloat(),
					spawn.get("z").getAsFloat());
		}
		
		{
			RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
			RegionManager regions = container.get(BukkitAdapter.adapt(spawnLoc.getWorld()));
			spawn = regions.getRegion("obokspawn");
			if(guildsEnabled()) {
				rm = FunnyGuilds.getInstance().getRegionManager();
			} else {
				EndiManager.log("ERR FunnyGuilds not found");
			}
		}
		
		new CustCommand("rtp") {
			@Override
			public void execute(Player sender, String[] args) {
				RPlayer rp = RPlayerMan.getInstance().getByPlayer(sender);
				
				int t = 1000*60*60*3;//every 3 hours
				if(rp.lastRTPUsage != -1 && System.currentTimeMillis()-rp.lastRTPUsage < t && !sender.hasPermission("endiman.superrtp")) {
					int secs = (int) ((t-(System.currentTimeMillis()-rp.lastRTPUsage))/1000);
					
					int m = secs/60;
					int s = secs%60;
					
					String time = m+" minut i "+s+" sekund";
					sender.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("endi.2d.chat.other.warn"))
							.append(new TextComponent(" Musisz odczekać jeszcze "+time+"!")).create());
					return;
				}
				rp.lastRTPUsage = System.currentTimeMillis();
				
				sender.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("endi.2d.chat.other.success"))
						.append(new TextComponent(" Szukam bezpiecznej lokalizacji...")).create());
				
				
				Location l = randomizeLocation();
				if(l == null) {
					sender.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("endi.2d.chat.other.warn"))
							.append(new TextComponent(" Nie udało się znaleźć bezpiecznej lokazji. Spróbuj ponownie później.")).create());
					return;
				}
				
				sender.spigot().sendMessage(new ComponentBuilder().append(new TranslatableComponent("endi.2d.chat.other.success"))
						.append(new TextComponent(" Teleportacja..")).create());
				
				new BukkitRunnable() {
					@Override
					public void run() {
						sender.teleport(l);
					}
				}.runTaskLater(EndiManager.plugin, 2);
				
			}
		};
	}
	
	public Location randomizeLocation() {
		return randomizeLocation(0);
	}

	public Location randomizeLocation(int attemp) {
		if(attemp >= 15) {
			return null;
		}
		
		World w = spawnLoc.getWorld();
		
		int spawnX = spawnLoc.getBlockX();
		int spawnY = spawnLoc.getBlockY();
		
		int Xmax = spawnX+range;
		int Xmin = spawnX-range;
		int x = r.nextInt(Xmax - Xmin + 1) + Xmin;
		
		int Ymax = spawnY+range;
		int Ymin = spawnY-range;
		int z = r.nextInt(Ymax - Ymin + 1) + Ymin;
		
		Location found = new Location(w,x,50,z);
		
		if(rm != null && rm.isNearRegion(found)) {
			return randomizeLocation(attemp+1);
		}
		
		if(isInSpawnRegion(found)) {
			return randomizeLocation(attemp+1);
		}
		
		found.setY(w.getHighestBlockAt(found).getY());
		
		if(w.getBlockAt(found).isLiquid()) {
			return randomizeLocation(attemp+1);
		}
		
		if(w.getBlockAt(found.add(0, 1, 0)).getType() != Material.AIR ||
				w.getBlockAt(found.add(0,2,0)).getType() != Material.AIR) {
			return randomizeLocation(attemp+1);
		}
		
		return found.add(0, 1, 0);
	}
}
