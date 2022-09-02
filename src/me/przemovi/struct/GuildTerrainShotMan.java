package me.przemovi.struct;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.google.gson.JsonObject;

import me.kotsu.microHTTPServer.HttpServer;
import me.kotsu.microHTTPServer.HttpServerAction;
import me.przemovi.EndiManager;
import me.przemovi.utils.PreviewGenerator;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.guild.Guild;
import net.md_5.bungee.api.ChatColor;

public class GuildTerrainShotMan {
	private List<GuildTerrainShot> shots = new ArrayList<GuildTerrainShot>();
	private static GuildTerrainShotMan instance;
	
	public static GuildTerrainShotMan getInstance() {
		if(instance == null) {
			instance = new GuildTerrainShotMan();
		}
		return instance;
	}
	
	public GuildTerrainShot findByGuild(Guild g) {
		for(GuildTerrainShot s : shots) {
			if(s.getGuild() == g) {
				return s;
			}
		}
		return null;
	}
	
	public void stop() {
		if (scheduler != null) {
			scheduler.shutdownNow();
		}
		taskRunning = false;
		refresher = null;
		if(manuel != null && manuel.isAlive()) {
			manuel.interrupt();
		}
		if(server != null) {
			server.shutdown();
		}
		server = null;
	}
	
	private Thread manuel;
	public void regenerateAllNow() {
		manuel = new Thread(refresher);
		manuel.start();
	}
	
	private ScheduledExecutorService scheduler = null;
	private Runnable refresher = null;
	private HttpServer server;
	private boolean taskRunning = true;
	
	public void start() {
		server = new HttpServer(22587);
		server.start();
		taskRunning = true;
		refresher = new Runnable() {
			@Override
			public void run() {
				try {
					EndiManager.log(ChatColor.GREEN+"Regeneration of all guild terrain previews just began.");
					long started = System.currentTimeMillis();
					while (System.currentTimeMillis() - started < 20 * 60 * 1000 && !Thread.interrupted() && taskRunning) {
						if (Bukkit.getOnlinePlayers().size() > 0) {
							EndiManager.log(ChatColor.RED+"Regenerating guild previews interrupted (players on server), retry in 30min.");
							Thread.sleep((3600 / 2) * 1000);// half hour
							continue;
						}
						for (int i = 0; i < shots.size(); i++) {
							GuildTerrainShot s = shots.get(i);
							try {
								s.regenerate();
							} catch (Exception e) {
								if(e.getMessage().equals("players online")) {
									EndiManager.log(ChatColor.RED+"Regenerating '"+s.getGuild().getTag()+"' interrupted (player join), retry in 15min.");
									Thread.sleep((3600 / 4) * 1000);// 15 minutes
									i--;
									continue;
								}
								throw e;
							}
						}
					}
				} catch (InterruptedException e) {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		scheduler = Executors.newScheduledThreadPool(1);
		Long initialDelay = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);
		scheduler.scheduleAtFixedRate(refresher, initialDelay, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);
		
		server.addAction(new HttpServerAction("/test") {
			@Override
			public Object run(String method, String ip, BufferedReader in) {
				try {
					JsonObject json = HttpServer.readJSONFromBRHTTP(in);
					
					if(json == null) {
						return "err";
					}
					
					JsonObject o1 = json.get("l1").getAsJsonObject();
					World w = Bukkit.getWorld(o1.get("world").getAsString());
					JsonObject o2 = json.get("l2").getAsJsonObject();
					
					
					PreviewGenerator s = new PreviewGenerator();
					BufferedImage image = null;
					try {
						image = s.begin(o1.get("x").getAsInt(), o2.get("x").getAsInt(), o1.get("z").getAsInt(), o2.get("z").getAsInt(), w);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return image;
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		server.addAction(new HttpServerAction("/guild/terrain") {
			@SuppressWarnings("deprecation")
			@Override
			public Object run(String method, String ip, BufferedReader in) throws Exception {
				Map<String, String> args = HttpServer.readFORMFromBRHTTP(in);
				if (args == null) {
					throw new Exception("no post args");
				}

				Guild g = FunnyGuilds.getInstance().getGuildManager().findByName(args.get("name")).getOrNull();
				if (g == null) {
					throw new Exception("guild not found (use name): " + args.get("name"));
				}

				GuildTerrainShot shot = findByGuild(g);
				if (shot == null) {
					shot = new GuildTerrainShot(g);
					shots.add(shot);
				}

				return shot.getImage();
			}
		});
	}
}
