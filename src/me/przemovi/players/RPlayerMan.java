package me.przemovi.players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import fr.xephi.authme.events.LoginEvent;
import me.przemovi.struct.CustCommand;

public class RPlayerMan implements Listener {
	private static RPlayerMan instance = null;
	public static RPlayerMan getInstance() {
		if(instance == null) {
			instance = new RPlayerMan();
			return instance;
		}
		return instance;
	}
	
	private List<RPlayer> players = new ArrayList<>();
	
	public void load() {
		if(players != null)
		players.clear();
		for(Player p : Bukkit.getOnlinePlayers()) {
			players.add(new RPlayer(p));
		}
		new CustCommand("endiman","install") {
			@Override
			public void execute(Player sender, String[] args) {
				//3,4 - unobf
				//1,2 - obf
				int id = Integer.parseInt(args[0]);
				if((id == 3 || id == 4) && !sender.hasPermission("endiman.unobf")) {
					sender.sendMessage("brak uprawnień");
					return;
				}
				getByPlayer(sender).installResourcepack(id);
			}
		};
	}
	
	public void shutdown() {
		if(players != null) {
			players.clear();
		}
		players = null;
	}
	
	public RPlayer getByNick(String nick) {
		for(RPlayer p : players) {
			if(p.getName().equals(nick)) {
				return p;
			}
		}
		return null;
	}
	
	public RPlayer getByPlayer(Player pl) {
		for(RPlayer rp : players) {
			//if(p.getName().equals(pl.getName())) {
			if(rp.getPlayer() == pl) {
				return rp;
			}
		}
		/*RPlayer rp = new RPlayer(pl);
		players.add(rp);*/
		return null;
	}
	
	public void reapplyResourcePacks() {
		for(RPlayer p : players) {
			p.reapplyResourcepack();
		}
	}
	
	public void reapplyResourcePacks(int packId) {
		for(RPlayer p : players) {
			if(p.getPackId() == packId) {
				p.reapplyResourcepack();
			}
		}
	}
	
	/*@EventHandler
    public void onMove(PlayerMoveEvent e) {
		//if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return;
		RPlayer rp = getByPlayer(e.getPlayer());
		if(rp == null) {
			return;
		}
		if(rp.getStatus() != Status.SUCCESSFULLY_LOADED && !e.getPlayer().isOp()) {
			e.setCancelled(true);
			rp.showResourcepackInterface();
		}
	}*/
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		RPlayer rp = getByNick(p.getName());
		
		if(rp == null) {
			rp = new RPlayer(p);
			players.add(rp);
		} else {
			rp.setPlayer(p);
		}
		
		rp.joinedEvent(event);
    }
	
	@EventHandler
	public void onAuthmeLogin(LoginEvent event) {
		Player p = event.getPlayer();
		RPlayer rp = getByNick(p.getName());
		
		if(rp == null) {
			rp = new RPlayer(p);
			players.add(rp);
		} else {
			rp.setPlayer(p);
		}
		
		rp.loggedInEvent(event);
	}
	
	/*@EventHandler
    public void onLogin(PlayerLoginEvent event) {}*/
	
	/*@EventHandler
    public void playerPreLoginCheck(AsyncPlayerPreLoginEvent event) {}*/
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		RPlayer rp = getByNick(p.getName());
		rp.quitEvent(event);
		System.out.println("PQUIT");
    }
	
	@EventHandler
	public void statusChanged(PlayerResourcePackStatusEvent e) {
		Player p = e.getPlayer();
		RPlayer rp = getByPlayer(p);
		rp.statusChanged(e.getStatus());
	}

	public List<RPlayer> getAllOnline() {
		return players.stream().filter(pl->pl.getPlayer() != null && pl.getPlayer().isOnline()).toList();
	}
	
	
}
