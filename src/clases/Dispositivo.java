package clases;

public class Dispositivo {
	
	Boolean encendido;
	int consumokWh;
	String nombre;
	
	public Dispositivo(Boolean encendido, int consumokWh, String nombre) {
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

	public int getConsumokWh() {
		return consumokWh;
	}

	public void setConsumokWh(int consumokWh) {
		this.consumokWh = consumokWh;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
