package me.yasei.utils;

public class DatabaseManager {
	static Database db;
	
	public static void connect() {
		db = new Database("127.0.0.1",3306,"minecraft","1xzdrOWB3nPQU_l0","player_profiles",false,false);
		db.start();
	}
	public static void disconnect() {
		db.stop();
	}
	
	public static Database getDatabase() {
		return db;
	}
	
	//test
	/*public static void main(String args[]) {
		try {
			Database db = new Database("192.168.2.10", 3306, "minecraft", "1xzdrOWB3nPQU_l0", "player_profiles", false, false);
			db.start();

			ResultSet rs = db.executeQuery("SELECT * FROM mp_users");
			rs.next();
			System.out.println(rs.getString("username"));
			
			Thread.sleep(6000);//connection shld die
			
			rs = db.executeQuery("SELECT * FROM mp_users");//conn should be reopened
			rs.next();
			System.out.println(rs.getString("username"));
			
			Thread.sleep(7000);//conn shld die again
			
			rs = db.executeQuery("SELECT * FROM mp_users");//conn should be reopened
			rs.next();
			System.out.println(rs.getString("username"));
			
			db.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
