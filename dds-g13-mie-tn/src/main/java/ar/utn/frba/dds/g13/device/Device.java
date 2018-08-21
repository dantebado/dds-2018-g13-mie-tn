package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

public abstract class Device {
	
	String name;
	BigDecimal hourlyConsumption;
	boolean ModoAhorroEnergiaON;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getHourlyConsumption() {
		return hourlyConsumption;
	}

	public void setHourlyConsumption(BigDecimal hourlyConsumption) {
		this.hourlyConsumption = hourlyConsumption;
	}
	

	public boolean isModoAhorroEnergiaON() {
		return ModoAhorroEnergiaON;
	}

	public void setModoAhorroEnergiaON(boolean modoAhorroEnergiaON) {
		ModoAhorroEnergiaON = modoAhorroEnergiaON;
	}

	public Device(String name, BigDecimal hourlyConsumption) {
		this.name = name;
		this.hourlyConsumption = hourlyConsumption;
	}
	
	public abstract boolean isSmart();

}
