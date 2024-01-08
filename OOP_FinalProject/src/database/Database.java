package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class Database {
	
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "RestoManagement";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	public ResultSet rs;
	
	private Connection connection;
	private Statement st;
	private static Database database;
	
	
	private Database() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Database getInstance() {
		if(database == null) return new Database();
		return database;
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	public ResultSet executeQuery(String query) {
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
