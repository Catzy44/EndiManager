package me.przemovi.struct;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.przemovi.utils.PreviewGenerator;
import net.dzikoysk.funnyguilds.guild.Guild;
import net.dzikoysk.funnyguilds.guild.Region;

public class GuildTerrainShot {
	private long generated;
	private BufferedImage image;
	private Guild guild;
	private static final int maxAgeMs = 1000*3600*24;//24h
	private Lock l = new ReentrantLock();
	
	public GuildTerrainShot(Guild g) {
		this.guild = g;
	}
	
	public Guild getGuild() {
		return guild;
	}

	public BufferedImage getImage() throws Exception {
		l.lock();
		if(image == null || System.currentTimeMillis()-generated > maxAgeMs) {
			regenerate();
		}
		l.unlock();
		return image;
	}
	
	public void regenerate() throws Exception {
		regenerate(false);
	}
	
	@SuppressWarnings("deprecation")
	public void regenerate(boolean stopOnPlayerJoin) throws Exception {
		Region r = guild.getRegion().getOrNull();
		
		PreviewGenerator s = new PreviewGenerator();
		image = s.begin(r.getLowerX(), r.getUpperX(), r.getLowerZ(), r.getUpperZ(), r.getWorld(), stopOnPlayerJoin);
		generated = System.currentTimeMillis();
	}
}
