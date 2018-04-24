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

	public Boolean getEncendido() {
		return encendido;
	}

	public void setEncendido(Boolean encendido) {
		this.encendido = encendido;
	}

	public float getConsumokWh() {
		return consumokWh;
	}

	public void setConsumokWh(float consumokWh) {
		this.consumokWh = consumokWh;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
