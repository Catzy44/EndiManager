package me.przemovi.npcs.inter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.przemovi.EndiManager;

public class InvInterface implements Listener {
	private Inventory i;
	private String inventoryName;
	
	public InvInterface(String name) {
		reg();
		inventoryName = "§r§f\ue930\ue931\uF80Fendiman-inv-"+name+"";
		i = Bukkit.createInventory(null, 54, inventoryName);
		
		ItemStack empty = nbtPane(30,true);
		for(int n = 0; n < 54; n++) {
			i.setItem(n, empty);
		}
		selfs.add(this);
	}
	
	private HashMap<Integer, InvClick> slots = new HashMap<Integer,InvClick>();
	public void setSlotAction(int slot, InvClick r) {
		slots.put(slot, r);
	}
	public void setSlotItemAction(int slot, ItemStack item, InvClick r) {
		i.setItem(slot, item);
		slots.put(slot, r);
	}
	
	public static ItemStack nbtPane(int cmd, boolean nolore) {
		ItemStack item = new ItemStack(Material.CYAN_STAINED_GLASS_PANE);
	    ItemMeta im = item.getItemMeta();
		if (nolore) {
			List<String> loreList = new ArrayList<String>();
			for (int i = 0; i < 159; i++) {
				loreList.add("");
			}
			loreList.add("                                                                                                                                                                                                                                                                                                                                                                                                                           ");
			im.setLore(loreList);
		}
	    im.setCustomModelData(cmd);
	    item.setItemMeta(im);
	    return item;
	}
	
	public Inventory getInv() {
		return i;
	}

	public HashMap<Integer, InvClick> getSlots() {
		return slots;
	}

	public String getName() {
		return inventoryName;
	}

	private static List<InvInterface> selfs = new ArrayList<InvInterface>();
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
		if (!event.getView().getTitle().contains("endiman-inv")) {
			return;
		}
		event.setCancelled(true);
		
		for(InvInterface inv : selfs) {
			if(!inv.getName().equals(event.getView().getTitle())) {
				continue;
			}
			int sl = event.getSlot();
			if(!inv.getSlots().containsKey(sl)) {
				continue;
			}
			try {inv.getSlots().get(sl).triggered((Player) event.getWhoClicked());} catch (Exception e) {e.printStackTrace();}
			return;
		}
	}
}
