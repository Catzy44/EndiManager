package me.yasei;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.przemovi.EndiManager;
import me.przemovi.utils.Utils;
import me.yasei.utils.Database;
import me.yasei.utils.DatabaseManager;
import net.dzikoysk.funnyguilds.guild.Guild;
import net.dzikoysk.funnyguilds.rank.DefaultTops;
import net.dzikoysk.funnyguilds.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.skinsrestorer.api.SkinsRestorer;

public class ChatProfile {
	private static Gson gson = new Gson();
	//private static Logger logger = PlayerProfiles.plugin.getLogger();
	
	public static BaseComponent[] genChatProfile(Player player, User user, Guild guild) throws Exception {
		ComponentBuilder container = new ComponentBuilder("");
		try {
			Database db = DatabaseManager.getDatabase();
			ResultSet rs = db.executeQuery("SELECT * FROM `mp_users` WHERE username='"+player.getName().toLowerCase()+"';");
			boolean succeed = rs.last();
			if(!succeed || rs.getRow() == 0) {
				container.append(buildBackground(player, false));
				container.append(buildFace(player, db, user, guild));
				container.append("\n");
				container.append(addBio(null));//FRESHMAN - NO BIO
			}else {
				rs.first();
				container.append(buildBackground(player, false));
				container.append(buildFace(player, db, user, guild));
				container.append("\n");
				container.append(addBio(rs.getString("bio")));
			}
			return container.create();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ComponentBuilder(buildBackground(player, false)).create();
	}
	
	public static BaseComponent buildBackground(Player player, boolean found) {
		String group = PlayerProfiles.perms.getPrimaryGroup(player);
		String bg = """
				󏿼{BG_LEFT}󏿿{BG_RIGHT}󏿼
				""";
		if(!found) {
			if(group.equals("owner")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else if(group.equals("vip")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else if (group.equals("svip")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else if (group.equals("admin")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else if (group.equals("mod")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else if (group.equals("tech")) {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			} else {
				bg = bg.replace("{BG_LEFT}", "").replace("{BG_RIGHT}", "");
			}
		}
		return new TextComponent(bg);
	}
	
	public static BaseComponent addFrame(Player player, boolean found) {
		String group = PlayerProfiles.perms.getPrimaryGroup(player);
		TextComponent frame = new TextComponent("");
		frame.addExtra(new TranslatableComponent("space.-44"));
		if(!found) {
			if(group.equals("owner")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_gold"));
			} else if(group.equals("vip")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_rose"));
			} else if (group.equals("svip")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_rose"));
			} else if (group.equals("admin")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_rose"));
			} else if (group.equals("mod")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_silver"));
			} else if (group.equals("tech")) {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_silver"));
			} else {
				frame.addExtra(new TranslatableComponent("endi.panels.mini_profiles.avatar_frames.frame_gray"));
			}
		}
		
		frame.addExtra(new TranslatableComponent("space.-2"));
		return frame;
	}
	
	private static JsonParser parser = new JsonParser();
	private static Map<String,String> premiumSkinCache = new HashMap<String,String>();
	//a cat was here
	public static String getPlayerSkinURL(Player player) {
		String url = getPlayerSkinURLFromSkinsRestorerPlugin(player);
		if(url != null) {
			return url;
		}
		return getPlayerSkinURLFromMojangPremiumAcc(player.getName());
	}
	public static String getPlayerSkinURLFromMojangPremiumAcc(String nick) {
		try {
			
			String apiResp = Utils.readEverythingFromURL("https://api.mojang.com/users/profiles/minecraft/"+nick);
			JsonObject apiRespObj = (JsonObject) new JsonParser().parse(apiResp);
			String name = apiRespObj.get("id").getAsString();
				
			apiResp = Utils.readEverythingFromURL("https://sessionserver.mojang.com/session/minecraft/profile/"+name);
			apiRespObj = (JsonObject) parser.parse(apiResp);
			
			JsonArray properties = apiRespObj.get("properties").getAsJsonArray();
			String texture = null;
			for(int i = 0; i < properties.size(); i++) {
				JsonObject obj = properties.get(i).getAsJsonObject();
				if(!obj.get("name").getAsString().equals("textures")) {
					continue;
				}
				texture = obj.get("value").getAsString();
			}
			if(texture == null) {
				return null;
			}
			
			byte[] decodedBytes = Base64.getDecoder().decode(texture);
			String decodedTex = new String(decodedBytes);
			JsonObject tex = parser.parse(decodedTex).getAsJsonObject();
			String skinUrl = tex.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
			
			return skinUrl;
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	public static String getPlayerSkinURLFromSkinsRestorerPlugin(Player player) {
		String playerUuid = player.getUniqueId().toString();
		try {
			Database db = DatabaseManager.getDatabase();
			
			var info = db.oneRowQuery("SELECT skin_type,skin_identifier FROM skins.sr_players WHERE uuid='"+playerUuid+"'");
			if(info == null) {
				return null;
			}
			
			String skinType = info.getString("skin_type");
			if(skinType == null) {
				return null;
			}
			String skinId = info.getString("skin_identifier");
			 
			String skinVal = null;
			if(skinType.equals("PLAYER")) {
				var skinInfo = db.oneRowQuery("SELECT value,signature FROM skins.sr_player_skins WHERE uuid='"+skinId+"'");
				skinVal = skinInfo.getString("value");
			} else if(skinType.equals("URL")) {
				var skinInfo = db.oneRowQuery("SELECT value,signature FROM skins.sr_url_skins WHERE url='"+skinId+"'");
				skinVal = skinInfo.getString("value");
			}
			if(skinVal == null) {
				return null;
			}
			
			byte[] decodedBytes = Base64.getDecoder().decode(skinVal);
			String decodedVal = new String(decodedBytes);
			JsonObject val = gson.fromJson(decodedVal, JsonObject.class);
			
			return val.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	// Using this one, reimplement if u wish or point to another method but plz dont change constructor
//	public static BufferedImage createCombinedHeadshot(Player player) {
//		try {
//			/*Database db = DatabaseManager.getDatabase();
//			ResultSet rs = db.executeQuery("SELECT sr_.Value FROM skins.Skins,skins.Players WHERE Players.Skin=Skins.Nick AND Players.Nick='" + player.getName().toLowerCase() + "';");
//			rs.last();
//			String dval = "";
//			if (rs.getRow() == 0) {
//				ResultSet rs2 = db.executeQuery("SELECT Skins.Value FROM skins.SkinsWHERE Skins.Nick='" + player.getName().toLowerCase() + "';");
//				rs2.first();
//				
//				String value = rs2.getString("Value");
//				byte[] decodedBytes = Base64.getDecoder().decode(value);
//				String decodedVal = new String(decodedBytes);
//				JsonObject val = gson.fromJson(decodedVal, JsonObject.class);
//				
//				dval = val.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
//			} else {
//				rs.first();
//				
//				String value = rs.getString("Value");
//				byte[] decodedBytes = Base64.getDecoder().decode(value);
//				String decodedVal = new String(decodedBytes);
//				JsonObject val = gson.fromJson(decodedVal, JsonObject.class);
//				
//				dval = val.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
//			}*/
//			
//			String dval = getPlayerSkinURL(player);
//			
//			URL texture = new URL(dval);
//			BufferedImage skin = ImageIO.read(texture);
//			BufferedImage combined = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
//			
//			Graphics2D g = combined.createGraphics();
//			g.drawImage(skin, 0, 0, 8, 8, 8, 8, 16, 16, null);
//			g.drawImage(skin, 0, 0, 8, 8, 40, 8, 48, 16, null);
//			g.dispose();
//			
//			return combined;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;//returns null on error
//		}
//	}
	
	static class SkinCache {
		static class CachedSkin {
			//public Player player;
			public BufferedImage skin;
			public long timestamp;
		}
		private static Map<String,CachedSkin> cache = new HashMap<String,CachedSkin>();
		
		public static BufferedImage getSkin(Player player) {
			try {
				String nick = player.getName();
				if(cache.containsKey(nick)) {
					CachedSkin cn = cache.get(nick);

					if(System.currentTimeMillis()-cn.timestamp < 1000*60*60*6) {//6h
						return cn.skin;
					}
				}
				String skinUrl = getPlayerSkinURL(player);
				if(skinUrl != null) {
					BufferedImage skin = ImageIO.read(new URL(skinUrl));
					
					CachedSkin cs = new CachedSkin();
					//cs.player = player;
					cs.timestamp = System.currentTimeMillis();
					cs.skin = skin;
					
					cache.put(nick, cs);
					
					return cs.skin;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				return ImageIO.read(EndiManager.class.getResourceAsStream("/files/steve.png"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}

	public static BaseComponent buildFace(Player player, Database db, User user, Guild guild) throws Exception {
			BufferedImage skin = SkinCache.getSkin(player);
		
			TextComponent container = new TextComponent("");
			
			BufferedImage combined = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g = combined.createGraphics();
	    	g.drawImage(skin, 0, 0, 8, 8, 8, 8, 16, 16, null);
	    	g.drawImage(skin, 0, 0, 8, 8, 40, 8, 48, 16, null);
	    	g.dispose();
			for(int y = 0; y < 8; y++) {
			    for(int x = 0; x < 8; x++) {
			    	Color result = new Color(combined.getRGB(x, y), true);
			        TranslatableComponent pixel = y % 2 == 0 ? new TranslatableComponent("endi.chat.panels.mini_profiles.pixel_top") : new TranslatableComponent("endi.chat.panels.mini_profiles.pixel_bottom");
			        if(x == 0 && y % 2 == 0) {
			        	pixel.setColor(ChatColor.of(result));
			        	container.addExtra(new TranslatableComponent("space.11"));
			        	container.addExtra(new TranslatableComponent("space.1/2"));
			        	container.addExtra(pixel);
			        }else if(x == 7 && y % 2 == 0) {
			        	pixel.setColor(ChatColor.of(result));
			        	container.addExtra(new TranslatableComponent("space.-1"));
			        	container.addExtra(pixel);
			        	container.addExtra(new TranslatableComponent("space.-41"));
			        } else if(x == 7) {
			        	pixel.setColor(ChatColor.of(result));
			        	container.addExtra(new TranslatableComponent("space.-1"));
			        	container.addExtra(pixel);
			        	if(y == 7) container.addExtra(addFrame(player, false));
			        	container.addExtra(addStats(y, player, user, guild));
			        	if(y != 7) container.addExtra("\n");
			        } else {
			        	pixel.setColor(ChatColor.of(result));
			        	if(x != 0) container.addExtra(new TranslatableComponent("space.-1"));
			        	container.addExtra(pixel);
			        }
			    }
			}
			return container;
			
		/*} catch (SQLException e) {
			e.printStackTrace();
		}
		return new TextComponent("failed miserably");*/
	}
	
	public static BaseComponent addStats(int line, Player player, User user, Guild g) {
		TextComponent stats = new TextComponent(new TranslatableComponent("space.5"));
		if (line == 1) {
			stats.addExtra("Nick: " + player.getDisplayName());
		} else if (line == 3) {
			if (g != null) {
				stats.addExtra("Gildia: " + g.getName() + " [" + g.getTag() + "]");
			} else {
				stats.addExtra("Gildia: brak");
			}
		} else if (line == 5) {
			if (user != null) {
				stats.addExtra("Ranking: #" + (user.getRank().getPosition(DefaultTops.USER_POINTS_TOP)+1)+ " (" + user.getRank().getPoints() + ")");
			} else {
				stats.addExtra("Ranking: --");
			}
		} else if (line == 7) {
			if (user != null) {
				stats.addExtra("K/D: " + user.getRank().getKDR());
			} else {
				stats.addExtra("K/D: --");
			}
		}
		return stats;
	}
	
	public static BaseComponent addBio(String text) {
		TextComponent bio = new TextComponent("");
		if(text != null && !text.equals("")) {
			bio.addExtra("Eyy\nThere's\na\nbio");
		} else {
			bio.addExtra("\n");
			bio.addExtra(new TranslatableComponent("space.9"));
        	bio.addExtra(new TranslatableComponent("space.1/2"));
			bio.addExtra("Ten gracz");
			bio.addExtra("\n");
			bio.addExtra(new TranslatableComponent("space.9"));
        	bio.addExtra(new TranslatableComponent("space.1/2"));
        	bio.addExtra("nie posiada opisu");
			bio.addExtra("\n");
			bio.setColor(ChatColor.of("#dbdbdb"));
			bio.setItalic(true);
		}
		return bio;
	}

}