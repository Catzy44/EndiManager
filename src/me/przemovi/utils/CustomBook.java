package me.przemovi.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

import me.przemovi.EndiManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
public class CustomBook {

	ItemStack book;
	//NBTTagList pages;
	
	private List<BaseComponent[]> pages;

	public CustomBook(Generation generation, String title, String author, List<BaseComponent[]> pages) {
		this.pages = pages;
		book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.setAuthor(author);
        bm.setTitle(title);
        for(BaseComponent[] s : pages) {
        	bm.spigot().addPage(s);
        }
        bm.setGeneration(generation);
        book.setItemMeta(bm);
	}
	
	public CustomBook(ComponentBuilder cb) {
		book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.spigot().addPage(cb.create());
        
        bm.setAuthor("Catzy44");
        bm.setTitle("");
        bm.setGeneration(Generation.TATTERED);
        book.setItemMeta(bm);
	}
	public CustomBook(List<BaseComponent[]> pages) {
		this.pages = pages;
		book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        for(BaseComponent[] s : pages) {
        	bm.spigot().addPage(s);
        }
        
        bm.setAuthor("Catzy44");
        bm.setTitle("");
        bm.setGeneration(Generation.TATTERED);
        book.setItemMeta(bm);
	}

	public void open(final Player p) {
		//final int slot = p.getInventory().getHeldItemSlot();
		//final ItemStack item = p.getInventory().getItem(slot);
		/*p.getInventory().setItem(slot, this.book);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RPHooks.plugin, new Runnable() {
			@Override
			public void run() {
				try {
				    //((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutOpenBook(EnumHand.a));
					//((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutOpenBook());
					p.getInventory().setItem(slot, item);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1L);*/
		Bukkit.getScheduler().scheduleSyncDelayedTask(EndiManager.plugin, new Runnable() {
			@Override
			public void run() {
				p.openBook(book);
			}
		});
	}

	public ItemStack toItemStack() {
		return this.book.clone();
	}

	public List<BaseComponent[]> getPages() {
		return pages;
	}
	
	

}
