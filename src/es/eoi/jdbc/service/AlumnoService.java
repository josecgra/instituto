package es.eoi.jdbc.service;

import java.util.List;

import es.eoi.jdbc.entity.Alumno;
import es.eoi.jdbc.repository.AlumnoRepository;

public class AlumnoService {

	private AlumnoRepository repository;

	public AlumnoService() {
		super();
		this.repository = new AlumnoRepository();
	}
	
	
	public Alumno findByDni(String dni) {
		return this.repository.findByDni(dni);
	}
	
	public List<Alumno> findAll() {
		return this.repository.findAll();
	}
	
	public boolean create(Alumno alu) {
		return this.repository.create(alu);
	}
	
	public boolean delete(String dni) {
		return this.repository.delete(dni);
	}
	
	public boolean update(String dni, String nombre, String apellidos) {
		return this.repository.update(dni, nombre, apellidos);
	}
	
	
	
	
}
