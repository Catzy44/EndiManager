package me.przemovi.npcs;

public class UniversalTrait /*extends Trait*/{
	
//	private ClickEvent book(String name) {
//		ClickEventRun e = new ClickEventRun() {
//			@Override
//			public void triggered(Player sender) {
//				books.get(name).open(sender);
//			}
//		};
//		return e.getEvent();
//	}
//	static final String name = "Ogrodnik";
//	
//	private Map<String,CustomBook> books = new HashMap<String,CustomBook>();
//	public UniversalTrait() {
//		super(name);
//		
//		books.put("player-or-admin",new CustomBook(new ComponentBuilder("")
//				.append(new TextComponent("Interakcja jako:\n\n"))
//				.append(new ComponentBuilder("Gracz\n").underlined(true).color(ChatColor.GREEN).event(book("player-main")).create())
//				.append(new ComponentBuilder("Administrator").underlined(true).color(ChatColor.RED).event(book("admin-main")).create())));
//		
//		books.put("admin-main",new CustomBook(new ComponentBuilder("")
//				.append(new TextComponent("TRYB ADMINISTRATORA\n\n")).color(ChatColor.RED)
//				.append(new ComponentBuilder("Zmień oferty").underlined(true).color(ChatColor.GREEN).event(book("offers-change")).create())));
//		
//		books.put("offers-change",new CustomBook(new ComponentBuilder("")
//				.append(new TextComponent("TRYfghSTRATORA\n\n")).color(ChatColor.RED)
//				.append(new ComponentBuilder("Zmień oferty").underlined(true).color(ChatColor.GREEN).event(book("offers-change")).create())));
//		
//		var shoopers = Config.getDatabase().get("shoopers").getAsJsonObject();
//		var me = shoopers.get(name).getAsJsonObject();
//		var deals = me.get("deals").getAsJsonArray();
//		
//		/*var pages = new ArrayList<BaseComponent[]>();
//		ComponentBuilder curpage = null;
//		for(int i = 0; i < deals.size(); i++) {
//			JsonObject obj = deals.get(i).getAsJsonObject();
//			
//			String title = obj.get("title").getAsString();
//			int buy = obj.get("buy").getAsInt();
//			int sell = obj.get("sell").getAsInt();
//			
//			int x = (i % 5) + 1;
//			if(curpage == null || x == 6) {
//				if(curpage != null) {
//					pages.add(curpage.create());//add last page to list
//				}
//				curpage = new ComponentBuilder();//build beginning of the new page
//				curpage.append("Tytuł bla bla\n\n");
//			}
//			
//			//append item to page
//			curpage.append(title);
//		}
//		pages.add(curpage.create());
//		
//		books.put("offers-list", new CustomBook(pages));*/
//		
//		/*String inventoryName = "§r§f\ue930\ue931\uF80Fendiman1";
//		Inventory inventory = Bukkit.createInventory(null, 54, inventoryName);
//		
//		ItemStack empty = nbtPane(30,true);
//		for(int i = 0; i < 54; i++) {
//			inventory.setItem(i, empty);
//		}
//		inventory.setItem(5*9+6, new ClickableItem(nbtPane(33,true)) {
//			@Override
//			public void triggered(Player sender) {
//				sender.sendMessage("a");
//			}
//		}.getItemStack());//undo
//		inventory.setItem(5*9+7, nbtPane(31,true));//back
//		inventory.setItem(5*9+8, nbtPane(32,true));//forw*/
//		
//		
//		
//		InvInterface in = new InvInterface("czarodziej");
//		Inventory inv = in.getInv();
//		
//		int adms = 5*9+4;
//		in.setSlotItemAction(adms, InvInterface.nbtPane(34,true), new InvClick() {
//			@Override
//			public void triggered(Player p) {//undo
//				if(inv.getItem(adms).getItemMeta().getCustomModelData() == 34) {
//					//to player
//					inv.setItem(adms, InvInterface.nbtPane(35, true));
//				} else {
//					inv.setItem(adms, InvInterface.nbtPane(34, true));
//				}
//			}
//		});
//		in.setSlotItemAction(5*9+6, InvInterface.nbtPane(33,true), new InvClick() {
//			@Override
//			public void triggered(Player p) {//undo
//				
//			}
//		});
//		in.setSlotItemAction(5*9+7, InvInterface.nbtPane(31,true), new InvClick() {
//			@Override
//			public void triggered(Player p) {//back
//				
//			}
//		});
//		in.setSlotItemAction(5*9+8, InvInterface.nbtPane(32,true), new InvClick() {
//			@Override
//			public void triggered(Player p) {//forward
//				
//			}
//		});
//		
//		books.put("player-main",new CustomBook(new ComponentBuilder("")
//				.append(new TextComponent("Witaj graczu:\n\n"))
//				.append(new ComponentBuilder("Gracz\n").underlined(true).color(ChatColor.GREEN).event(book("player-main")).create())
//				.append(new ComponentBuilder("Pokaż mi swoje towary").underlined(true).color(ChatColor.RED).event(new ClickEventRun() {
//					@Override
//					public void triggered(Player sender) {
//						new BukkitRunnable() {
//							@Override
//							public void run() {
//								sender.openInventory(inv);
//							}
//						}.runTask(EndiManager.plugin);
//					}
//				}.getEvent()).create())));
//		
//	}
//	
//	
//
//	@EventHandler
//	public void click(net.citizensnpcs.api.event.NPCRightClickEvent e) {
//		if(e.getNPC() != this.getNPC()) {
//			return;
//		}
//		
//		books.get("player-main").open(e.getClicker());
//	}
//	
//	//for nerds :)
//			/*books.put("player-main",new CustomBook(new ComponentBuilder("")
//					.append(new TextComponent("Interakcja jako gracz:\n\n"))
//					.append(new ComponentBuilder("Gracz\n").underlined(true).color(ChatColor.GREEN).event(new ClickEventRun() {
//						@Override
//						public void triggered(Player sender) {
//							books.get("player-main").open(sender);
//						}}.getEvent()).create())
//					.append(new ComponentBuilder("Administrator").underlined(true).color(ChatColor.RED).event(new ClickEventRun() {
//						@Override
//						public void triggered(Player sender) {
//							books.get("admin-main").open(sender);
//						}
//					}.getEvent()).create())));*/
}
