package dam2.add.p5;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	private static Connection conexion;
	private static Statement st;

	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		Operaciones operacion = new Operaciones();

		boolean salir = false;
		int opcion;

		conexion = ConexionBaseDatos.getConexion();
		st = conexion.createStatement();
		
		while (!salir) {

			System.out.println("MENU");
			System.out.println("1 - Jugar");
			System.out.println("2 - Anadir Pregunta");
			System.out.println("3 - Importar preguntas");
			System.out.println("4 - Ver records");
			System.out.println("5 - Instrucciones");
			System.out.println("6 - Salir");

			try {
				System.out.println("Introduzca una opcion: ");
				opcion = sc.nextInt();

				switch (opcion) {
				case 1:
					operacion.jugar();
					st.close();
					ConexionBaseDatos.desconectar();
					break;
				case 2:
					operacion.anadirPreguntas();
					st.close();
					ConexionBaseDatos.desconectar();
					break;
				case 3:
					operacion.importarPreguntas();
					st.close();
					ConexionBaseDatos.desconectar();
					break;
				case 4:
					operacion.verRecords();
					st.close();
					ConexionBaseDatos.desconectar();
					break;
				case 5:
					operacion.instrucciones();
					break;
				case 6:
					salir = true;
					break;
				default:
					System.out.println("\nElije del 1 al 6\n");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nDebe insertar un numero\n");
			}
		}
	}
}
