package me.przemovi.npcs;

import org.bukkit.event.Listener;

public class NPCsManager implements Listener{
//	
//	private static NPCsManager self = null;
//	
//	public static NPCsManager getInstance() {
//		if(self == null) {
//			self = new NPCsManager();
//		}
//		return self;
//	}
//	
//	private boolean citizensEnabled() {
//		return EndiManager.plugin.getServer().getPluginManager().isPluginEnabled("Citizens");
//	}
//	
//	private TraitInfo czarownik = null;
//	private TraitFactory s;
//	public NPCsManager() {
//		if(!citizensEnabled()) {
//			EndiManager.log("ERR Citizens not found!");
//			return;
//		}
//		czarownik = net.citizensnpcs.api.trait.TraitInfo.create(UniversalTrait.class).withName(UniversalTrait.name);
//		
//		s = CitizensAPI.getTraitFactory();
//	}
//	
//	public boolean disabled = true;
//
//	public void init() {
//		disabled = false;
//		
//		if(!citizensEnabled()) {
//			return;
//		}
//		s.registerTrait(czarownik);
//		
//		CitizensAPI.getNPCRegistry().forEach((n) -> {
//			if(!n.getName().equals("Czarownik")) {
//				return;
//			}
//			n.addTrait(UniversalTrait.class);
//		});
//		
//		Bukkit.getServer().getPluginManager().registerEvents(this,EndiManager.plugin);
//	}
//	
//	public void shutdown() {
//		disabled = true;
//		
//		if(!citizensEnabled()) {
//			return;
//		}
//		s.deregisterTrait(czarownik);
//		
//		CitizensAPI.getNPCRegistry().forEach((n) -> {
//			if(!n.hasTrait(UniversalTrait.class)) {
//				return;
//			}
//			n.removeTrait(UniversalTrait.class);
//		});
//	}
//	
//	@EventHandler
//    public void onCitizensEnable(CitizensEnableEvent ev) {
//       /* NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Dinnerbone");
//        npc.spawn(yourlocationhere);*/
//    }
//	
//	@EventHandler
//	public void onInventoryClick(InventoryClickEvent event) {
//		if(event.getInventory() == null) {
//			return;
//		}
//	    if (event.getRawSlot() >= event.getView().getTopInventory().getSize()) {
//	        return;//clicked their own inv
//	    }
//		if(!event.getView().getTitle().contains("endiman1")) {
//			return;
//		}
//		event.setCancelled(true);
//	}
}
