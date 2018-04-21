package clases;

import java.util.Date;

public abstract class Persona {
	
	String nombreApellido;
	String domicilio;
	Date fechaAlta;
	String nombreUsuario;
	String contrasena;
	
	public Persona(String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario, String contrasena) {
		setNombreApellido(nombreApellido);
		setDomicilio(domicilio);
		setFechaAlta(fechaAlta);
		setNombreUsuario(nombreUsuario);
		setContrasena(contrasena);
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}
