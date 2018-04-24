package clases;

import java.util.Date;

public abstract class Persona {
	
	protected String nombreApellido;
	protected String domicilio;
	protected Date fechaAlta;
	protected String nombreUsuario;
	protected String password;
	
	public Persona(String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario, String password) {
		setNombreApellido(nombreApellido);
		setDomicilio(domicilio);
		setFechaAlta(fechaAlta);
		setNombreUsuario(nombreUsuario);
		setPassword(password);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
