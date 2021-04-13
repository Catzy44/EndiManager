package me.catzy44.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.przemovi.EndiManager;
import me.przemovi.players.RPlayer;
import me.przemovi.players.RPlayerMan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class LoginMessageSenderTask extends BukkitRunnable {
	private static LoginMessageSenderTask instance;

	public static LoginMessageSenderTask getInstance() {
		if (instance == null) {
			instance = new LoginMessageSenderTask();
		}
		return instance;
	}
	public void start() {
		this.runTaskTimerAsynchronously(EndiManager.plugin, 0, 3 * 20);
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

		for (RPlayer rp : RPlayerMan.getInstance().getAllOnline()) {
			try {
				if (!rp.isResourcepackLoaded()) {
					continue;
				}

				if (AuthMeApi.getInstance().isAuthenticated(rp.getPlayer())) {
					continue;
				}
				
				if(AuthMeApi.getInstance().isRegistered(rp.getName())) {
					rp.sendMessage(
							new ComponentBuilder()
							.append(new TranslatableComponent("endi.2d.chat.other.arr_gray"))
							.append(new TextComponent(" Zaloguj się wpisując ")).color(ChatColor.GRAY)
							.append(new TextComponent("/login <twoje hasło>")).color(ChatColor.DARK_AQUA)
							.create());
				} else {
					rp.sendMessage(
							new ComponentBuilder()
							.append(new TranslatableComponent("endi.2d.chat.other.arr_gray"))
							.append(new TextComponent(" Zarejestruj się wpisując: ")).color(ChatColor.GRAY)
							.append(new TextComponent("/register <hasło> <powtórz_hasło>")).color(ChatColor.DARK_AQUA)
							.create());
				}
			} catch (Exception e) {
				// thread interrupted
			}
		}

	}
}
