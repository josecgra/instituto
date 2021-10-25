package es.eoi.jdbc.app;

import java.util.Scanner;

import es.eoi.jdbc.entity.Alumno;
import es.eoi.jdbc.service.AlumnoService;

public class GestionInstituto {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int i = 0;
		AlumnoService servicio = new AlumnoService();
		
		do {
			System.out.println("GESTION INSTITUTO V1");
			System.out.println("====================");
			System.out.println("1 –Listado Alumnos");
			System.out.println("2 –Busca Alumno (DNI)");
			System.out.println("3 –Crear Alumno");
			System.out.println("4 –Modificar Alumno");
			System.out.println("5 –Eliminar Alumno");
			System.out.println("6 –Listado Alumnos +18");
			System.out.println("0 –SALIR");
			i = sc.nextInt();
			sc.nextLine();
			
			switch(i) {
				case 1:
					for (Alumno alu : servicio.findAll()) {
						System.out.println(alu.toString());
					}
					break;
				case 2:
					System.out.println("Introduce el DNI: ");
					String dni = sc.nextLine();
					Alumno alu = servicio.findByDni(dni);
					System.out.println(alu.toString());
					break;
				case 3:
					System.out.print("Introduce el DNI: ");
					String d = sc.nextLine();
					System.out.print("Introduce el NOMBRE: ");
					String nom = sc.nextLine();
					System.out.print("Introduce el APELLIDOS: ");
					String ape = sc.nextLine();
					System.out.print("Introduce el EDAD: ");
					int ed = sc.nextInt();
					Alumno a = new Alumno(d, nom, ape, ed);
					
					if (servicio.create(a))
						System.out.println("El Alumno se ha creado correctamente");
					else
						System.out.println("Ha habido algun fallo creado el Alumno");
					
					break;
				case 4:
					System.out.println("Introduce el DNI: ");
					String dd = sc.nextLine();
					System.out.println("Introduce el NOMBRE: ");
					String nn = sc.nextLine();
					System.out.println("Introduce el APELLIDOS: ");
					String aa = sc.nextLine();
					
					if (servicio.update(dd, nn, aa))
						System.out.println("El Alumno se ha modificado correctamente");
					else
						System.out.println("Ha habido algun fallo modificando el Alumno");
					
					break;
				case 5:
					
					System.out.println("Introduce el DNI: ");
					String ddd = sc.nextLine();
					
					if (servicio.delete(ddd))
						System.out.println("El Alumno se ha borrado correctamente");
					else
						System.out.println("Ha habido algun fallo borrando el Alumno");
					
					break;
				case 6:
					for (Alumno alu1 : servicio.findAll18()) {
						System.out.println(alu1.toString());
					}
					break;
			}
			
			
			
		} while (i != 0);
		
		System.out.println("LA APLICACION HA TERMINADO");
		
		
	}

}
