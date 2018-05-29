package ar.utn.frba.dds.g13.devices;

import java.math.BigDecimal;

public class StandardDevice extends Device {

	public StandardDevice(Boolean deviceOn, BigDecimal consumptionkWh, String name) {
		super(deviceOn, consumptionkWh, name);
	}

	private BigDecimal getConsumption() {
		return this.consumptionkWh;
	}
	
	public void setConsumption(BigDecimal consumption) {
		this.consumptionkWh = consumption;
	}
	
	public BigDecimal calculateConsumptionInHours(float hours) {
		return new BigDecimal(hours).multiply(this.getConsumption());
	}

}
