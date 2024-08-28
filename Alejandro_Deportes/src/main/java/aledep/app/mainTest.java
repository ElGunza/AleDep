package aledep.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mainTest {

	public static void main(String[] args) {

		testearConexion();

	}

	public static void testearConexion() {
//		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String jdbcUrl = "jdbc:sqlserver://GONZALO\\SQLEXPRESS;databaseName=AlejandroDeportes";
		String username = "sa";
		String password = "12345678";

		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Conexión exitosa a SQL Server");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al conectar a la base de datos");
		}
	}

}
