package dam2.add.p5;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBaseDatos {
	static String url;
	static String username;
	static String password;
	static String driver;

	static String fichero = "./properties/bbdd.properties";
	static Connection conexion;

	public static Connection getConexion() {
		if (conexion == null) {
			crearConexion();
		}
		return conexion;
	}

	public static boolean crearConexion() {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(fichero));

			url = properties.getProperty("db.url");
			username = properties.getProperty("db.user");
			password = properties.getProperty("db.password");
			driver = properties.getProperty("db.driver");

			Class.forName(driver);
			conexion = DriverManager.getConnection(url, username, password);
			conexion.setAutoCommit(false);

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void desconectar() {
		try {
			conexion.close();
			conexion = null;

		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
		}
	}
}
