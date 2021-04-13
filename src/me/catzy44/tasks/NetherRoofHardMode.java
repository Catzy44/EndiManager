package me.catzy44.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.przemovi.EndiManager;

public class NetherRoofHardMode implements Listener{
	private static NetherRoofHardMode self;
	
	private Map<Player,Long> teleporting;
	private List<Location> bakedPortalBlocks;
	
	public void start() {
		teleporting = new HashMap<Player,Long>();
		bakedPortalBlocks = new ArrayList<Location>();
		
		EndiManager.plugin.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
		
		mobs.put(EntityType.PHANTOM, 2);
		mobs.put(EntityType.PIGLIN_BRUTE, 1);
		mobs.put(EntityType.PIGLIN, 1);
		mobs.put(EntityType.CREEPER, 1);
		
		ru = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Location l = p.getLocation();

					if (l.getWorld().getEnvironment() != World.Environment.NETHER) {
						continue;
					}

					if (l.getBlockY() < 190) {
						continue;
					}
					
					World w = l.getWorld();

					new BukkitRunnable() {
						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							for(EntityType type : mobs.keySet()) {
								int target = mobs.get(type);
								
								int count = w.getEntities().stream().filter(e->{
									if(e.getType() == EntityType.PIGLIN_BRUTE) {
										//System.out.println(e.get);
									}
									return e.getLocation().distance(l) < 30 && e.getType() == type;
								}).collect(Collectors.toList()).size();
								
								if(count >= target) {
									continue;
								}
								
								for (int i = 0; i < target-count; i++) {
									
									{
										int max = 5;
										int min = 0;
										int x = r.nextInt(max - min + 1) + min;

										if (x != 1) {// 1 out of 5 change to spawn mob
											continue;
										}
									}
									
									int max = 20;
									int min = 0;
									int x = r.nextInt(max - min + 1) + min;

									Location s = l.add(x, type == EntityType.PHANTOM ? 10 : 1, x);

									w.playEffect(s, Effect.POTION_BREAK, 1, 1);
									w.spawnParticle(Particle.PORTAL, s, 10);
	 								LivingEntity e = (LivingEntity) w.spawnEntity(s, type);
	 								e.setHealth(e.getMaxHealth());
	 								e.removePotionEffect(PotionEffectType.POISON);
	 								if(e instanceof Piglin) {
	 									Piglin cp = (Piglin) e;
	 									cp.setAware(true);
	 									cp.setAdult();
	 									e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100000, 2));
	 									cp.setTarget(p);
	 								} else if(e instanceof PiglinBrute) {
	 									PiglinBrute cp = (PiglinBrute) e;
	 									cp.setAware(true);
	 									cp.setAdult();
	 									cp.setTarget(p);
	 								} else if(e instanceof Creeper) {
	 									Creeper cp = (Creeper) e;
	 									cp.setPowered(true);
	 									cp.setAware(true);
	 									cp.setTarget(p);
	 									//e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100000, 1));
	 								}
	 								
								}
							}
							
						}
					}.runTaskLater(EndiManager.plugin, 1);
				}
			}
		};
		ru.runTaskTimerAsynchronously(EndiManager.plugin, 0, 4 * 20);
	}
	
	@EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        /*if(e.getChangedType() != Material.NETHER_PORTAL) {
        	return;
        }
        if(!bakedPortalBlocks.contains(e.getBlock().getLocation())) {
        	return;
        }
        e.
        e.setCancelled(true);*/
    }
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
		if(e.getCause() != TeleportCause.NETHER_PORTAL) {
			return;
		}
		teleporting.put(e.getPlayer(), System.currentTimeMillis());
	}

	
	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		//System.out.println(e.getReason().name()+" "+e.getBlocks().get(0).getWorld().getEnvironment().name());
		//fire nether, netherpair normal
		World w = e.getWorld();
		if(e.getReason() == CreateReason.FIRE) {//nether
			
			if (w.getEnvironment() != World.Environment.NETHER) {
				return;
			}
			
			List<BlockState> s = e.getBlocks();
			if(s.get(0).getY() < 190) {
				return;
			}
			
			new BukkitRunnable() {
				@Override
				public void run() {
					List<Location> toset = new ArrayList<Location>();
					for(BlockState b : s) {
						if(b.getType() != Material.NETHER_PORTAL) {
							continue;
						}
						bakedPortalBlocks.add(b.getLocation());
					}
					for(BlockState b : s) {
						if(b.getType() != Material.OBSIDIAN) {
							continue;
						}
						//w.setType(b.getLocation(), Material.BEDROCK);
						w.getBlockAt(b.getLocation()).setType(Material.BEDROCK, false);
					}
					//fill portal corners
					for(BlockState b : s) {
						Block bl = w.getBlockAt(b.getLocation());
						if(bl.getRelative(BlockFace.UP).getType() == Material.BEDROCK && 
								bl.getRelative(BlockFace.DOWN).getType() == Material.BEDROCK) {
							toset.add(b.getLocation().add(0, -2, 0));
							toset.add(b.getLocation().add(0, 2, 0));
						}
					}
					for(Location l : toset) {
						//w.setType(l, Material.BEDROCK);
						w.getBlockAt(l).setType(Material.BEDROCK, false);
					}
					
				}
			}.runTaskLater(EndiManager.plugin, 2);
			
			return;
		} else if(e.getReason() == CreateReason.NETHER_PAIR) {//normal
			if(!(e.getEntity() instanceof LivingEntity)) {
				return;
			}
			LivingEntity en = (LivingEntity) e.getEntity();
			if(!(en instanceof Player)) {
				return;
			}
			Player p = (Player) en;
			if(!teleporting.containsKey(p)) {
				return;
			}
			if((System.currentTimeMillis()-teleporting.get(p))/1000 > 10) {
				return;
			}
			
			for(BlockState b : e.getBlocks()) {
				if(b.getType() != Material.OBSIDIAN) {
					continue;
				}
				b.setType(Material.BEDROCK);
				w.setBlockData(b.getLocation(), b.getBlockData());
			}
			teleporting.remove(p);
			return;
		}
	}
	
	public static NetherRoofHardMode getInstance() {
		if(self == null) {
			self = new NetherRoofHardMode();
		}
		return self;
	}
	
	private Map<EntityType,Integer> mobs = new HashMap<EntityType,Integer>();
	private BukkitRunnable ru;
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		Location l = e.getEntity().getLocation();

		if (l.getWorld().getEnvironment() != World.Environment.NETHER) {
			return;
		}

		if (l.getBlockY() < 190) {
			return;
		}
		
		if(!mobs.containsKey(e.getEntityType())) {
			return;
		}
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Location l = e.getEntity().getLocation();

		if (l.getWorld().getEnvironment() != World.Environment.NETHER) {
			return;
		}

		if (l.getBlockY() < 190) {
			return;
		}
		
		if(e.getEntity().getType() == EntityType.PLAYER) {
			e.setDamage(e.getDamage()*3);
		} else if(e.getEntity().getType() == EntityType.PHANTOM && e.getDamager().getType() == EntityType.CREEPER) {
			e.setCancelled(true);
		}
	}
	
	public void stop() {
		if(teleporting != null) {
			teleporting.clear();
		}
		teleporting = null;
		try {
			if (ru != null && !ru.isCancelled())
				ru.cancel();
		} catch (Exception e) {

		}
		ru = null;
	}
	
	@EventHandler
	public void onEntityCollision(VehicleEntityCollisionEvent e) {
		if (!e.getEntity().getType().name().startsWith("MINECART")) {
			return;
		}
		if (e.getEntity().getTicksLived() < 20 * 60) {
			return;
		}
		e.setCancelled(true);
	}

	Random r = new Random();

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event) {
		Block b = event.getBlock();
		Location l = b.getLocation();

		if (l.getWorld().getEnvironment() != World.Environment.NETHER) {
			return;
		}

		if (l.getBlockY() < 190) {
			return;
		}

		if (event.getDirection() == BlockFace.DOWN || event.getDirection() == BlockFace.UP) {
			return;
		}

		int max = 100;
		int min = 0;
		int x = r.nextInt(max - min + 1) + min;

		if (x == 1) {
			event.setCancelled(true);
		}
	}
}
