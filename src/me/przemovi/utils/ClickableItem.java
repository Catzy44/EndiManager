package me.przemovi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.przemovi.EndiManager;

public abstract class ClickableItem implements Listener {
	
	private static boolean registered = false;
	private void reg() {
		if(registered) {
			return;
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() == null) {
			return;
		}
		if (event.getRawSlot() >= event.getView().getTopInventory().getSize()) {
			return;// clicked their own inv
		}
		if (!event.getView().getTitle().contains("endiman1")) {
			return;
		}
		for(ClickableItem item : selfs) {
			if(item.getItemStack() != event.getCurrentItem()) {
				System.out.println(event.getCurrentItem().getType().name());
				continue;
			}
			try {item.triggered((Player) event.getWhoClicked());} catch (Exception e) {e.printStackTrace();}
			event.setCancelled(true);
		}
		event.setCancelled(true);
	}

	private static int lastIdentifier = 0;
	private static List<ClickableItem> selfs = new ArrayList<ClickableItem>();

	public static int getNextI() {
		int x = lastIdentifier;
		lastIdentifier++;
		return x;
	}

	public abstract void triggered(Player sender);
	private ItemStack is;
	
	public ClickableItem(ItemStack is) {
		reg();
		this.is = is;
		this.id = getNextI();
		ClickableItem.selfs.add(this);
	}

	private int id;

	public int getId() {
		return id;
	}

	public ItemStack getItemStack() {
		return is;
	}

}
