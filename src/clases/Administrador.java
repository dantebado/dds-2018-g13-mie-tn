package clases;

import java.util.Date;

public class Administrador extends Persona {
	
	int nIdentificacion;

	public Administrador(int nIdentificacion, String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario,
			String contraseņa) {
		super(nombreApellido, domicilio, fechaAlta, nombreUsuario, contraseņa);
		setnIdentificacion(nIdentificacion);
	}

	public int getnIdentificacion() {
		return nIdentificacion;
	}

	public void setnIdentificacion(int nIdentificacion) {
		this.nIdentificacion = nIdentificacion;
	}

}
