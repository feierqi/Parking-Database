package database;

import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionSingleton {

	private static DBConnectionSingleton dbConnectionSingleton = null;

	private static Connection conn = null;

	static final String URL = "jdbc:oracle:thin:@oracle.wpi.edu:1521:WPI11grxx";

	private DBConnectionSingleton(String userName, String password) throws ClassNotFoundException, SQLException, IOException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, I cannot find the Oracle JDBC driver.");
		}

		System.out.println("Success! Oracle JDBC Driver connected.");

		try {
			System.out.println("Trying to connnect to the server...");
			conn = DriverManager.getConnection(URL, userName, password);
			System.out.println("Evaluating connection...");
			if (conn == null || conn.isClosed()) {
				System.out.println("Connection is not established or closed!");
			} else {
				System.out.println("Success!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
		}
	}

	public static synchronized Connection getConnect(String userName, String password) throws ClassNotFoundException, SQLException, IOException {
		if (dbConnectionSingleton==null || conn==null || conn.isClosed()) {
			dbConnectionSingleton = new DBConnectionSingleton(userName, password);
		}
		return conn;
	}

	public static void main(String[] args) {
		try {
			Connection conn1 = DBConnectionSingleton.getConnect("pren", "pren@wpi.edu");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}