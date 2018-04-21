package clases;

import java.util.ArrayList;
import java.util.Date;

public class Usuario extends Persona {
	
	int telefono;
	String tipoDoc;
	int numeroDoc;
	String categoria;
	int kWConsumidos;
	
	ArrayList<Dispositivo> dispositivos;

	public Usuario(String nombreApellido, String domicilio, Date fechaAlta, String nombreUsuario,
			String contraseņa, int telefono, String tipoDoc, int numeroDoc, String categoria, int kWConsumidos) {
		super(nombreApellido, domicilio, fechaAlta, nombreUsuario, contraseņa);
		setTelefono(telefono);
		setTipoDoc(tipoDoc);
		setNumeroDoc(numeroDoc);
		setCategoria(categoria);
		setkWConsumidos(kWConsumidos);
		dispositivos = new ArrayList<Dispositivo>();
	}
	
	public void agregarDispositivo(Dispositivo dispositivo) {
		dispositivos.add(dispositivo);
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

	public ArrayList<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(ArrayList<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

}
