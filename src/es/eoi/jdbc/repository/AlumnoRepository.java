package es.eoi.jdbc.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.eoi.jdbc.entity.Alumno;

public class AlumnoRepository {

	private Connection openConnection() {

		String url = "jdbc:mysql://localhost:3306/instituto?useSSL=false&serverTimezone=UTC";
		String usuario = "root";
		String pass = "1234";
		Connection con = null;

		try {
			con = DriverManager.getConnection(url, usuario, pass);
		} catch (SQLException e) {
			System.err.println("ERROR al conectar a la BBDD " + e.getMessage());
		}

		return con;
	}

	public Alumno findByDni(String dni) {

		Alumno alu = null;
		Connection con = openConnection();

		String sql = "SELECT * FROM alumno WHERE dni = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, dni);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				alu = new Alumno(dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
			}

		} catch (SQLException e) {
			System.err.println("ERROR al recuperar el Alumno con el DNI " + e.getMessage());
		}

		return alu;
	}

	public List<Alumno> findAll() {

		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		Connection con = openConnection();
		Alumno alu = null;
		String sql = "SELECT * FROM alumno";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				alu = new Alumno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getInt("edad"));
				listaAlumnos.add(alu);
			}

		} catch (SQLException e) {
			System.out.println("ERROR al recuperar todos los alumnos " + e.getMessage());
		}

		return listaAlumnos;
	}
	
	public List<Alumno> findAll18() {

		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		Connection con = openConnection();
		Alumno alu = null;
		String sp = "call SP_ALUMNOS18()";

		try {
			CallableStatement cst = con.prepareCall(sp);
			ResultSet rs = cst.executeQuery();

			while (rs.next()) {
				alu = new Alumno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getInt("edad"));
				listaAlumnos.add(alu);
			}

		} catch (SQLException e) {
			System.out.println("ERROR al recuperar todos los alumnos " + e.getMessage());
		}

		return listaAlumnos;
	}

	
	public boolean create(Alumno alu) {

		Connection con = openConnection();
		boolean creado = false;
		String sql = "INSERT INTO alumno VALUES (?, ?, ?, ?)";

		if (alu != null) {

			try {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, alu.getDni());
				pst.setString(2, alu.getNombre());
				pst.setString(3, alu.getApellidos());
				pst.setInt(4, alu.getEdad());

				int i = pst.executeUpdate();

				if (i > 0) {
					creado = true;
				}

			} catch (SQLException e) {
				System.err.println("ERROR al crear el Alumno en la BBDD " + e.getMessage());
			}

		}

		return creado;
	}

	public boolean delete(String dni) {

		boolean borrado = false;
		Connection con = openConnection();

		String sql = "DELETE FROM instituto.alumno WHERE dni = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, dni);

			int i = pst.executeUpdate();

			if (i > 0) {
				borrado = true;
			}

		} catch (SQLException e) {
			System.err.println("ERROR borrando un alumno " + e.getMessage());
		}

		return borrado;
	}

	public boolean update(String dni, String nombre, String apellidos) {

		boolean actualizado = false;
		Connection con = openConnection();

		String sql = "UPDATE alumno SET nombre = ?, apellidos = ? WHERE dni = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, nombre);
			pst.setString(2, apellidos);
			pst.setString(3, dni);

			int i = pst.executeUpdate();

			if (i > 0) {
				actualizado = true;
			}

		} catch (SQLException e) {
			System.err.println("ERROR actualizando los datos del alumno " + e.getMessage());
		}

		return actualizado;
	}
	
	public boolean insertsTran() {
		
		boolean insertadosTran = false;
		Connection con = openConnection();
		
		String sql1 = "INSERT INTO alumno VALUES ('55555555Q', 'Yeni', 'Gomez', 10)";
		String sql2 = "INSERT INTO alumno VALUES ('55555555N', 'Luisa', 'Gomez', 26)";
		String sql3 = "INSERT INTO alumno VALUES ('55555555T', 'Teresa', 'Gomez', 11)";
		
		try {
			//desactivo el autocommit
			con.setAutoCommit(false);
			
			Statement st = con.createStatement();
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			st.executeUpdate(sql3);
			
			// inserta todo lo pendiente en la BBDD
			con.commit();
			insertadosTran = true;
			
			
		} catch (SQLException e) {
			System.out.println("hubo un problema con un insert " + e.getMessage());
			try {
				System.out.println("Se deshacen todos los INSERT");
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
		return insertadosTran;
	}

}
