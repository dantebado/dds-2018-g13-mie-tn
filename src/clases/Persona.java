package clases;

import java.util.Date;

public abstract class Persona {
	
	String nombreApellido;
	String domicilio;
	Date fechaAlta;
	String nombreUsuario;
	String contraseña;
	
	public Persona(String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario, String contraseña) {
		setNombreApellido(nombreApellido);
		setDomicilio(domicilio);
		setFechaAlta(fechaAlta);
		setNombreUsuario(nombreUsuario);
		setContraseña(contraseña);
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
}
