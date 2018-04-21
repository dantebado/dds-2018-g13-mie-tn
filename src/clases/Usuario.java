package clases;

import java.util.Date;

public class Usuario extends Persona {
	
	int telefono;
	String tipoDoc;
	int numeroDoc;
	String categoria;
	int kWConsumidos;

	public Usuario(String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario,
			String contraseña, int telefono, String tipoDoc, int numeroDoc, String categoria, int kWConsumidos) {
		super(nombreApellido, domicilio, fechaAlta, nombreUsuario, contraseña);
		setTelefono(telefono);
		setTipoDoc(tipoDoc);
		setNumeroDoc(numeroDoc);
		setCategoria(categoria);
		setkWConsumidos(kWConsumidos);
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public int getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(int numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getkWConsumidos() {
		return kWConsumidos;
	}

	public void setkWConsumidos(int kWConsumidos) {
		this.kWConsumidos = kWConsumidos;
	}	

}
