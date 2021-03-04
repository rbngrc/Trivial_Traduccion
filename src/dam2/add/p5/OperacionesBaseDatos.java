package dam2.add.p5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OperacionesBaseDatos {
	private static Connection conexion;
	private static Statement st;

	ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();
	ArrayList<Jugador> recordJugador = new ArrayList<Jugador>();

	public ArrayList<Pregunta> leerPreguntas() {

		conexion = ConexionBaseDatos.getConexion();

		try {
			st = conexion.createStatement();

			String sql = "select * from preguntas";
			ResultSet rss = st.executeQuery(sql);

			while (rss.next()) {
				String texto = rss.getString("Pregunta");
				String respuesta1 = rss.getString("RespuestaA");
				String respuesta2 = rss.getString("RespuestaB");
				String respuesta3 = rss.getString("RespuestaC");
				String correcta = rss.getString("Correcta");

				Pregunta preg = new Pregunta(texto, respuesta1, respuesta2, respuesta3, correcta);
				listaPreguntas.add(preg);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return listaPreguntas;
	}

	public void insertarPreguntas(ArrayList<Pregunta> lista_preguntas) {

		conexion = ConexionBaseDatos.getConexion();

		try {
			st = conexion.createStatement();

			for (int i = 0; i < lista_preguntas.size(); i++) {
				Pregunta preg = lista_preguntas.get(i);
				String texto = preg.getPregunta();
				String respuesta1 = preg.getRespuesta1();
				String respuesta2 = preg.getRespuesta2();
				String respuesta3 = preg.getRespuesta3();
				String correcta = preg.getCorrecta();

				String query = "INSERT INTO preguntas (Pregunta, RespuestaA, RespuestaB, RespuestaC, Correcta) "
						+ "VALUES ('" + texto + "', '" + respuesta1 + "',  '" + respuesta2 + "', '" + respuesta3
						+ "', '" + correcta + "');";

				int resultado = st.executeUpdate(query);

				if (resultado == 0) {
					System.out.println("No se ha podido insertar");
				}
				conexion.commit();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<Jugador> leerRecord() {

		conexion = ConexionBaseDatos.getConexion();

		try {
			st = conexion.createStatement();

			String sql = "select * from records order by puntuacion DESC";
			ResultSet rss = st.executeQuery(sql);

			while (rss.next()) {
				String nombre = rss.getString("nombre");
				String act = rss.getString("aciertos");
				String fll = rss.getString("fallos");
				String pnt = rss.getString("puntuacion");

				Jugador jugador = new Jugador(nombre, act, fll, pnt);
				recordJugador.add(jugador);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return recordJugador;

	}

	public void insertarRecord(ArrayList<Jugador> recordJugador) {

		conexion = ConexionBaseDatos.getConexion();

		try {
			st = conexion.createStatement();

			for (int i = 0; i < recordJugador.size(); i++) {
				Jugador jug = recordJugador.get(i);
				String nombre = jug.getNombre();
				String aciertos = jug.getAciertos();
				String fallos = jug.getFallos();
				String puntuacion = jug.getPuntuacion();

				String query = "INSERT INTO records (nombre, aciertos, fallos, puntuacion) " + "VALUES ('" + nombre
						+ "', '" + aciertos + "',  '" + fallos + "', '" + puntuacion + "');";

				int resultado = st.executeUpdate(query);

				if (resultado == 0) {
					System.out.println("No se ha podido insertar");
				}
				conexion.commit();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean comprobarRecord(String nombre) {
		OperacionesBaseDatos ops = new OperacionesBaseDatos();
		ArrayList<Jugador> recordsGuardados = ops.leerRecord();

		boolean resultado = false;

		for (int i = 0; i < recordsGuardados.size(); i++) {
			Jugador rec = recordsGuardados.get(i);
			String nombreJugador = rec.getNombre();

			if (nombreJugador.equals(nombre)) {
				resultado = true;
			}
		}

		return resultado;

	}

	public boolean comprobarPuntuacion(String nombre, double puntuacion) {
		OperacionesBaseDatos ops = new OperacionesBaseDatos();
		ArrayList<Jugador> recordsGuardados = ops.leerRecord();

		boolean resultado = false;

		for (int i = 0; i < recordsGuardados.size(); i++) {
			Jugador rec = recordsGuardados.get(i);
			String nombreJugador = rec.getNombre();
			String puntuacionJugador = rec.getPuntuacion();

			Double d = new Double("6.35");
			double punt = d.parseDouble(puntuacionJugador);
			if (nombreJugador.equals(nombre)) {
				if (punt < puntuacion) {
					resultado = true;
				} else if (punt >= puntuacion) {
					resultado = false;
				}
			}
		}
		return resultado;
	}

	public void actualizarRecord(ArrayList<Jugador> recordJugador) {
		conexion = ConexionBaseDatos.getConexion();

		try {
			st = conexion.createStatement();

			for (int i = 0; i < recordJugador.size(); i++) {
				Jugador jug = recordJugador.get(i);
				String nombre = jug.getNombre();
				String aciertos = jug.getAciertos();
				String fallos = jug.getFallos();
				String puntuacion = jug.getPuntuacion();

				String query = "update records set aciertos = '" + aciertos + "', fallos = '" + fallos
						+ "', puntuacion = '" + puntuacion + "' where nombre = '" + nombre + "';";

				int resultado = st.executeUpdate(query);

				if (resultado == 0) {
					System.out.println("No se ha podido insertar");
				}
				conexion.commit();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
