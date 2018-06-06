package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

public abstract class Device {
	
	String name;
	BigDecimal hourlyConsumption;
	
	public Device(String name, BigDecimal hourlyConsumption) {
		this.name = name;
		this.hourlyConsumption = hourlyConsumption;
	}
	
	public abstract boolean isSmart();

}
