package me.catzy44.tasks;

import org.bukkit.event.Listener;

public class CustomBlocksManager implements Listener {
	private static CustomBlocksManager instance;

	public static CustomBlocksManager getInstance() {
		if (instance == null) {
			instance = new CustomBlocksManager();
		}
		return instance;
	}
	
	public void start() {
//		EndiManager.plugin.getServer().getPluginManager().registerEvents(this, EndiManager.plugin);
//
//		new CustCommand("endiman","block") {
//			@Override
//			public void execute(Player sender, String[] args) {
//				
//			}
//		};
	}
	
	public void stop() {
		
	}
	
//	@EventHandler
//	public void onInteract(PlayerInteractEvent e) {
//		Action action = e.getAction();
//        //Player player = e.getPlayer();
//        Block block = e.getClickedBlock();
//
//        if (!action.equals(Action.RIGHT_CLICK_BLOCK)) {
//        	return;
//        }
//        
//        if(e.getHand() != EquipmentSlot.HAND) {
//        	return;
//        }
//        
//        if(block.getType() != Material.AZALEA_LEAVES) {
//        	return;
//        }
//        
//        new BukkitRunnable() {
//			@Override
//			public void run() {
//				Map<Location,BlockData> bs = new HashMap<Location,BlockData>();
//				
//				Location l = block.getLocation();
//				Leaves c = (Leaves) Bukkit.createBlockData(Material.AZALEA_LEAVES);
//				c.setDistance(1);
//				c.setPersistent(true);
//				bs.put(l, c);
//				bs.put(l.clone().add(0, 1, 0), c);
//				
//				
//				fakeBlocks(bs);
//			}
//        }.runTaskLaterAsynchronously(EndiManager.plugin, 10);
//	}
//	
//	//all custom blocks on entire server
//	//private List<CustomBlock> customBlocks = new ArrayList<CustomBlock>();
//	
//	public void fakeBlocks(Map<Location, BlockData> blocks) {
//		
//		if(blocks.size() == 1) {
//			Location l = (Location) blocks.keySet().toArray()[0];
//			BlockData d = blocks.get(l);
//			
//			IBlockData ib = ((CraftBlockData) d).getState();
//			// int stateId = net.minecraft.world.level.block.Block.i(ib);
//
//			net.minecraft.core.BlockPosition bp = new net.minecraft.core.BlockPosition(l.getBlockX(), l.getBlockY(), l.getBlockZ());
//			PacketPlayOutBlockChange pack = new PacketPlayOutBlockChange(bp, ib);
//
//			
//			for(Player p : Bukkit.getOnlinePlayers()) {
//				((CraftPlayer) p).getHandle().c.a(pack);
//			}
//			return;
//		}
//		
//		
//		Map<YChunk, HashMap<Location, BlockData>> chunksToDo = new HashMap<YChunk, HashMap<Location, BlockData>>();
//
//		for (Location l : blocks.keySet()) {
//			BlockData data = blocks.get(l);
//			YChunk procChunk = new YChunk(l);
//
//			YChunk found = null;
//			for(YChunk y : chunksToDo.keySet()) {
//				if(y.equals(procChunk)) {
//					found = y;
//					break;
//				}
//			}
//			if(found == null) {
//				chunksToDo.put(procChunk, new HashMap<Location, BlockData>());
//				found = procChunk;
//			}
//			chunksToDo.get(found).put(l, data);
//		}
//
//		for (YChunk c : chunksToDo.keySet()) {
//			Map<Location, BlockData> map = chunksToDo.get(c);
//			EndiManager.debug("[CB] Chunk blocks: "+map.size());
//			
//			SectionPosition sectionposition = SectionPosition.a(c.getX(), c.getY(), c.getZ());
//
//			ShortSet ss = new ShortOpenHashSet();
//			IBlockData[] d = new IBlockData[map.size()];
//
//			int i = 0;
//			for (Location l : map.keySet()) {
//				BlockData data = map.get(l);
//				ss.add(SectionPosition.b(new BlockPosition(l.getBlockX(), l.getBlockY(), l.getBlockZ())));
//				d[i] = ((CraftBlockData) data).getState();
//				i++;
//			}
//
//			PacketPlayOutMultiBlockChange p = new PacketPlayOutMultiBlockChange(sectionposition, ss, d);
//
//			for (Player pl : Bukkit.getOnlinePlayers()) {
//				((CraftPlayer) pl).getHandle().c.a(p);
//			}
//		}
//	}
//
//	@EventHandler
//	public void onBlockPlace(BlockPlaceEvent e) {
//		Block b = e.getBlock();
//		if (b.getType() == Material.AZALEA_LEAVES) {
//			boolean isOakNear = false;
//			List<BlockFace> faces = List.of(BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH);
//			for (BlockFace f : faces) {
//				if (b.getRelative(f).getType() == Material.OAK_LOG) {
//					isOakNear = true;
//					break;
//				}
//			}
//			if (isOakNear) {
//				Leaves c = (Leaves) b.getBlockData();
//				c.setPersistent(false);
//				b.setBlockData(c);
//			}
//		}
//	}
}
