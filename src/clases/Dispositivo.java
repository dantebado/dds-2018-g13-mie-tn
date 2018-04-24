package clases;

public class Dispositivo {
	
	private Boolean encendido;
	private float consumokWh;
	private String nombre;
	
	public Dispositivo(Boolean encendido, float consumokWh, String nombre) {
		setEncendido(encendido);
		setConsumokWh(consumokWh);
		setNombre(nombre);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	public void setEncendido(Boolean encendido) {
		this.encendido = encendido;
	}

	public void setConsumokWh(float consumokWh) {
		this.consumokWh = consumokWh;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
