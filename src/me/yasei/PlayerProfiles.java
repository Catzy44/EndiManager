package me.yasei;

import org.bukkit.plugin.RegisteredServiceProvider;

import me.przemovi.EndiManager;
import me.yasei.utils.DatabaseManager;
import net.milkbowl.vault.permission.Permission;

public class PlayerProfiles/* extends JavaPlugin implements Listener*/ {
	public static EndiManager plugin;
	public static Permission perms = null;
	
	public void initialize() {
		RegisteredServiceProvider<Permission> rsp = EndiManager.plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        plugin = EndiManager.plugin;
		
		DatabaseManager.connect();
		
		/*getServer().getPluginManager().registerEvents(this, this);*/
	}
	
	public void deinitialize() {
		DatabaseManager.disconnect();
	}
	
	private static PlayerProfiles instance;
	
	public static PlayerProfiles getInstance() {
		if(instance == null) {
			instance = new PlayerProfiles();
		}
		return instance;
	}
	
	/*@Override
	public void onEnable() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        plugin = this;
		
		DatabaseManager.connect();
		
		getServer().getPluginManager().registerEvents(this, this);
		
	}
	@Override
	public void onDisable() {
		DatabaseManager.disconnect();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				DatabaseManager.getDatabase().executeUpdate("INSERT IGNORE INTO `mp_users` SET username='"+e.getPlayer().getName().toLowerCase()+"';");
			}
		}, 0);
		
	}*/
}
