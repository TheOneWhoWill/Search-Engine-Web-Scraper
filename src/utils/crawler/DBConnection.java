package utils.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
	private static final String DB_USER = "your_username";
	private static final String DB_PASSWORD = "your_password";

	private static Connection conn;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = new Properties();

				props.setProperty("user", DB_USER);
				props.setProperty("password", DB_PASSWORD);
				props.setProperty("ssl", "true");

				conn = DriverManager.getConnection(DB_URL, props);
			} catch (SQLException e) {
				System.out.println(e.getMessage());

				try {
					if (conn != null) {
						conn.close();
						System.out.println("Database connection closed");
					}
				} catch (SQLException closing_exception) {
					System.out.println(closing_exception.getMessage());
				}

				return null;
			}
		}

		return conn;
	}

}
