package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ClinicAccounts";
    private static final String USER = "root"; 
    private static final String PASS = "CalvinMySQL"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

	public static ClinicAccountSystem getSystem() {
		// TODO Auto-generated method stub
		return null;
	}
}