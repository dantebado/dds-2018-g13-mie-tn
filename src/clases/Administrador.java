package clases;

import java.util.Date;

public class Administrador extends Persona {
	
	private int nIdentificacion;

	public Administrador(int nIdentificacion, String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario,
			String password) {
		super(nombreApellido, domicilio, fechaAlta, nombreUsuario, password);
		setnIdentificacion(nIdentificacion);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	public void setnIdentificacion(int nIdentificacion) {
		this.nIdentificacion = nIdentificacion;
	}

}
