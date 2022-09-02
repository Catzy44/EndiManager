package me.przemovi.utils;

import java.awt.image.BufferedImage;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_21_R4.util.CraftMagicNumbers;
import org.bukkit.map.MapPalette;

import me.przemovi.EndiManager;
import net.minecraft.world.level.material.MaterialMapColor;

public class PreviewGenerator implements Runnable{
	public int minX;
	public int maxX;
	public int minZ;
	public int maxZ;
	public BufferedImage image;
	private Locker l = new Locker();
	private World w;
	private boolean stopOnPlayerJoin = false;
	
	public BufferedImage begin(int x1, int x2, int z1, int z2, World w, boolean stopOnPlayerJoin) throws Exception {
		this.stopOnPlayerJoin = stopOnPlayerJoin;
		return begin(x1,x2,z1,z2,w);
	}
	
	public BufferedImage begin(int x1, int x2, int z1, int z2, World w) throws Exception {
		
		minX = Math.min(x1, x2);
		maxX = Math.max(x1, x2);
		
		minZ = Math.min(z1, z2);
		maxZ = Math.max(z1, z2);
		this.w = w;
		
		/*x = minX;
		z = minZ;*/
		width = maxX-minX;
		height = maxZ-minZ;
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(EndiManager.plugin, this);
		l.waitForUnlock();
		if(ex != null) {
			throw ex;
		}
		return image;
	}
	
	int width = 0;
	int height = 0;
	
	int x = 0;
	int z = 0;
	
	int skips = 0;
	int maxTimeMilis = 50;
	
	private Exception ex;
	
	@Override
	public void run() {
		try {
			if (stopOnPlayerJoin && Bukkit.getOnlinePlayers().size() > 0) {
				throw new Exception("players online");
			}
			long timeTaken = System.currentTimeMillis();
			boolean skipped = false;
			main: 
			for (; x < width; x++) {
				for (; z < height; z++) {
					if (System.currentTimeMillis() - timeTaken > maxTimeMilis) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(EndiManager.plugin, this, 1);
						skips++;
						skipped = true;
						break main;
					}
					Block b = w.getHighestBlockAt(minX+x, minZ+z);
					image.setRGB(x, z, blockToRGB(b));
				}
				z = 0;
			}
			if (!skipped) {
				l.unlock();
			}
		} catch (Exception e) {
			l.unlock();
			ex = e;
		}
	}

	public void _run() {
		try {
			long timeTaken = System.currentTimeMillis();
			boolean skipped = false;
			main: for (; x < maxX; x++) {
				for (; z < maxZ; z++) {
					if (System.currentTimeMillis() - timeTaken > 20) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(EndiManager.plugin, this, 1);
						skipped = true;
						break main;
					}
					Block b = w.getHighestBlockAt(x, z);
					image.setRGB(x, z, blockToRGB(b));
				}
				z = minZ;
			}
			if (!skipped) {
				l.unlock();
			}
		} catch (Exception e) {
			l.unlock();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int blockToRGB(Block block) {
		net.minecraft.world.level.block.Block nmsblock = CraftMagicNumbers.getBlock(block.getType());
		MaterialMapColor nms = nmsblock.w();
		return MapPalette.getColor((byte) (nms.al * 4)).getRGB();
	}
}
