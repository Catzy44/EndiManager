package me.przemovi.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.przemovi.EndiManager;

public abstract class CustCommand {
	public static List<CustCommand> all = new ArrayList<CustCommand>() {
		private static final long serialVersionUID = 1L;
		public boolean add(CustCommand mt) {
			super.add(mt);
			Collections.sort(all, new Comparator<CustCommand>() {
				@Override
				public int compare(CustCommand o1, CustCommand o2) {
					if (o1.name == null || o2.name == null) {
						return 0;
					}
					return -Integer.compare(o1.name.length, o2.name.length);
				}
			});
			return true;
		}
	};
	
	public static void clear() {
		all.clear();
		all = null;
	}
	
	public static List<CustCommand> getAll() {
		return all;
	}
	
	public abstract void execute(Player sender, String args[]);
	
	public String name[];
	
	public String hoomanName() {
		StringBuilder sb = new StringBuilder();
		for(String s : name) {
			sb.append(s+"/");
		}
		String g = sb.toString();
		return g.toString().substring(0,g.length()-1);
	}
	
	public static void execByCmd(Player sender, String[] args) {
		int argumentsLength = args.length;
		for(CustCommand a: all) {
			int commandArgumentsLength = a.name.length;
			
			if(commandArgumentsLength > argumentsLength) {
				continue;
			}
			boolean pass = true;//wszystkie argumenty komendy muszą zostać spełnione
			for(int i = 0; i < commandArgumentsLength; i++) {
				if(!a.name[i].equals(args[i])) {
					pass = false;
					break;
				}
			}
			if(pass) {
				new BukkitRunnable() {
					@Override
					public void run() {
						a.execute(sender, argumentsLength == commandArgumentsLength ? null : Arrays.copyOfRange(args, commandArgumentsLength, argumentsLength));
					}
				}.runTaskAsynchronously(EndiManager.plugin);
				return;
			}
		}
	}

	public CustCommand(String... name) {
		all.add(this);
		this.name = name;
	}
}
