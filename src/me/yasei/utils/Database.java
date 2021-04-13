package me.yasei.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private String user;
	private String database;
	private String password;
	private int port = 3306;
	private String hostname = "127.0.0.1";
	private boolean useSSL = false;
	private boolean verifyServerCertificate = false;
	private Connection connection;
	private final String encoding = "&useUnicode=true&characterEncoding=utf8";
	
	
	public Database(String ip, int port, String user, String password, String database, boolean useSSL, boolean verifySSLCert) {
		this.hostname = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
		this.useSSL = useSSL;
		this.verifyServerCertificate = verifySSLCert;
	}
	
	public void start() {
		openConnection();
		initKiller();
	}
	
	public void stop() {
		closeConnection();
		stopKiller();
	}
	
	private final int MYSQL_CONNECTION_TIMEOUT = 5000;
	private long lastDBUseTimestamp = 0;
	private Thread killerThread;
	public void initKiller() {
		killerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (!Thread.interrupted()) {
						if(lastDBUseTimestamp == 0) {
							Thread.sleep(500);
							continue;
						}// lastDBUseTimestamp==0 -> connection has been closed or never opened
						//lastDBUseTimestamp is set to currenttimemilis on executeUpdate, executeQuery and openConnection
						while (System.currentTimeMillis() - lastDBUseTimestamp < MYSQL_CONNECTION_TIMEOUT) {
							Thread.sleep(500);//check every 500ms if 5000ms has passed from last req
						}
						if (checkConnection()) {
							closeConnection();
						}
						lastDBUseTimestamp = 0;
					}
				} catch (InterruptedException e) {
					//thread got interrupted by user
					//it's intended so ignore the stacktrace
				} catch (Exception e) {
					//thats weird..? (any other exception)
					e.printStackTrace();
				}
				stopKiller();//cleanup
			}
		});
		killerThread.start();
	}
	
	public void stopKiller() {
		lastDBUseTimestamp = 0;
		if(killerThread != null) {
			killerThread.interrupt();
		}
		killerThread = null;
	}

	public Connection openConnection() {
		try {
			if (checkConnection()) {
				//System.out.println("[DEBUG] Tried to open new db connection but old connection is still alive.");
				return connection;
			}
			//System.out.println("[DEBUG] Opened new db connection.");
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
			lastDBUseTimestamp = System.currentTimeMillis();
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		lastDBUseTimestamp = System.currentTimeMillis();
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
			lastDBUseTimestamp = System.currentTimeMillis();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet oneRowQuery(String query) {
		try {
			ResultSet rs = executeQuery(query);
			rs.last();
			if (rs.getRow() > 0) {
				rs.first();
				return rs;
			}
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
			lastDBUseTimestamp = System.currentTimeMillis();
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
			lastDBUseTimestamp = System.currentTimeMillis();
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return statement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
