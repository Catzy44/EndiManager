package me.yasei.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database_LEGACY {
	private static Database_LEGACY instance;

	private String user = "minecraft";
	private String database = "player_profiles";
	private String password = "1xzdrOWB3nPQU_l0";
	private int port = 3306;
	private String hostname = "127.0.0.1";
	private final boolean useSSL = false;
	private final boolean verifyServerCertificate = false;
	private Connection connection;
	private final String encoding = "&useUnicode=true&characterEncoding=utf8";
	
	public void init() {
		/*
		 * hostname = Config.getDatabase().get("mysqlIp").getAsString(); port =
		 * Config.getDatabase().get("mysqlPort").getAsInt(); user =
		 * Config.getDatabase().get("mysqlLogin").getAsString(); password =
		 * Config.getDatabase().get("mysqlPass").getAsString(); database =
		 * Config.getDatabase().get("mysqlDb").getAsString();
		 */
		instance = this;

		this.firstConnection();
	}

	public Connection openConnection() {
		try {
			if (checkConnection()) {
				return connection;
			}
			// Class.forName("com.mysql.jdbc.Driver");
			String s = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
			if (useSSL) {
				s += "?useSSL=true";
			} else {
				s += "?useSSL=false";
			}
			if (verifyServerCertificate) {
				s += "&verifyServerCertificate=true";
			} else {
				s += "&verifyServerCertificate=false";
			}
			// s += "&serverTimezone=UTC";
			s += encoding;
			connection = DriverManager.getConnection(s, this.user, this.password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void firstConnection() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + this.hostname + ":" + this.port + "/?user=" + this.user + "&password=" + this.password;
			if (useSSL) {
				url += "&useSSL=true";
			} else {
				url += "&useSSL=false";
			}
			if (verifyServerCertificate) {
				url += "&verifyServerCertificate=true";
			} else {
				url += "&verifyServerCertificate=false";
			}
			url += encoding;
			Connection conn = DriverManager.getConnection(url);
			Statement s = conn.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.database);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection != null;
	}

	public Connection getConnection() {
		if (!checkConnection())
			openConnection();
		return connection;
	}

	public boolean closeConnection() {
		if (connection == null)
			return false;
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public ResultSet executeQuery(String query) {
		try {
			if (!checkConnection())
				openConnection();
			if (connection == null)
				return null;
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int executeUpdate(String query) {
		try {
			if (!checkConnection())
				openConnection();
			if (connection == null) {
				openConnection();
				if (connection == null)
					return 0;
			}
			Statement statement = connection.createStatement();
			if (statement == null)
				return 0;
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			if (e.getSQLState().equals("42S21"))
				return 4221;
			e.printStackTrace();
		}
		return 0;
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			if (!checkConnection())
				openConnection();
			if (connection == null)
				return null;
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return statement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Database_LEGACY getInstance() {
		if (instance == null) {
			// return new Database();
		}
		return instance;
	}

	public static int getRowCountFromResultSet(ResultSet rs) {
		try {
			int rowCount = 0;
			if (rs.last()) {
				rowCount = rs.getRow();
				rs.beforeFirst();
			}
			return rowCount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String escapeString(String x, boolean escapeDoubleQuotes) {
		StringBuilder sBuilder = new StringBuilder(x.length() * 11 / 10);

		int stringLength = x.length();

		for (int i = 0; i < stringLength; ++i) {
			char c = x.charAt(i);

			switch (c) {
			case 0: /* Must be escaped for 'mysql' */
				sBuilder.append('\\');
				sBuilder.append('0');

				break;

			case '\n': /* Must be escaped for logs */
				sBuilder.append('\\');
				sBuilder.append('n');

				break;

			case '\r':
				sBuilder.append('\\');
				sBuilder.append('r');

				break;

			case '\\':
				sBuilder.append('\\');
				sBuilder.append('\\');

				break;

			case '\'':
				sBuilder.append('\\');
				sBuilder.append('\'');

				break;

			case '"': /* Better safe than sorry */
				if (escapeDoubleQuotes) {
					sBuilder.append('\\');
				}

				sBuilder.append('"');

				break;

			case '\032': /* This gives problems on Win32 */
				sBuilder.append('\\');
				sBuilder.append('Z');

				break;

			case '\u00a5':
			case '\u20a9':
				// escape characters interpreted as backslash by mysql
				// fall through

			default:
				sBuilder.append(c);
			}
		}

		return sBuilder.toString();
	}
}
