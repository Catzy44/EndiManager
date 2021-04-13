package me.przemovi.resourcepack;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.przemovi.EndiManager;
import me.przemovi.database.Config;

public class ResourcePackManager {
	private static Map<Integer, ResourcePack> resourcePacks = new HashMap<Integer, ResourcePack>();
	
	public static void start() {
		new BukkitRunnable() {
			@Override
			public void run() {
				loadFromDb();
			}
		}.runTaskAsynchronously(EndiManager.plugin);
	}
	public static void forceAsyncConfigReload(Runnable success,Runnable failure) {
		forceDelayedAsyncConfigReload(0, success, failure);
	}
	public static void forceDelayedAsyncConfigReload(int delay, Runnable success,Runnable failure) {
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(delay);
					loadFromDb();
				} catch (Exception e) {
					e.printStackTrace();
					failure.run();
				}
				success.run();
			}
		}.runTaskAsynchronously(EndiManager.plugin);
	}

	public static void loadFromDb() {
		try {
			EndiManager.log("[RP] syncing with CDP...");
			JsonArray arr = Config.getDatabase().get("packs").getAsJsonArray();
			for (int i = 0; i < arr.size(); i++) {
				JsonObject pack = arr.get(i).getAsJsonObject();

				ResourcePack rp = new ResourcePack(pack.get("url").getAsString(), pack.get("hashUrl").getAsString());
				rp.refreshHash();//REFRESH HASHES, USES DROPBOX!
				resourcePacks.put(pack.get("id").getAsInt(), rp);
			}
			EndiManager.log("[RP] successfully reloaded resource packs!");
		} catch (Exception e) {
			EndiManager.log("[RP] loading resourcepacks from db FAILED!");
			EndiManager.log("[RP] retrying in 30 seconds! THIS IS SERIOUS!");
			e.printStackTrace();
		}
	}
	
	public static ResourcePack get(int id) {
		return resourcePacks.get(id);
	}
	
	/*public static void XrefreshAllResourcePacks(boolean async) {
		for(Integer i : resourcePacks.keySet()) {
			ResourcePack rp = resourcePacks.get(i);
			rp.refreshHash(async);
		}
	}*/
}
