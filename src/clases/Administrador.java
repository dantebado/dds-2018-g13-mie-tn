package clases;

import java.util.Date;

public class Administrador extends Persona {
	
	int nIdentificacion;

	public Administrador(int nIdentificacion, String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario,
			String contraseña) {
		super(nombreApellido, domicilio, fechaAlta, nombreUsuario, contraseña);
		setnIdentificacion(nIdentificacion);
	}

	public int getnIdentificacion() {
		return nIdentificacion;
	}

	public void setnIdentificacion(int nIdentificacion) {
		this.nIdentificacion = nIdentificacion;
	}

}
