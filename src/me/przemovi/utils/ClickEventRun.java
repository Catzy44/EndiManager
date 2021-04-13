package me.przemovi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.przemovi.struct.CustCommand;
import net.md_5.bungee.api.chat.ClickEvent;

public abstract class ClickEventRun {
	public static void init() {
		new CustCommand("endiman","runevent") {
			@Override
			public void execute(Player sender, String[] args) {
				int ev = Integer.parseInt(args[0]);
				for(ClickEventRun e : selfs) {
					if(e.getId() != ev) {
						continue;
					}
					e.triggered(sender);
					break;
				}
			}
		};
	}
	public static void stop() {
		
	}
	
	private static int lastIdentifier = 0;
	private static List<ClickEventRun> selfs = new ArrayList<ClickEventRun>();
	
	public static int getNextI() {
		int x = lastIdentifier;
		lastIdentifier++;
		return x;
	}
	
	public abstract void triggered(Player sender);
	
	
	public ClickEventRun() {
		this.id= getNextI();
		this.e = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/endiman runevent "+id);
		ClickEventRun.selfs.add(this);
	}
	
	private ClickEvent e;
	private int id;
	
	public int getId() {
		return id;
	}
	public ClickEvent getEvent() {
		return e;
	}
	
	
}
