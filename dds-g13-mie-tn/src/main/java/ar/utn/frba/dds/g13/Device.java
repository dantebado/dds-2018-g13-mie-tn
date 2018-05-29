package ar.utn.frba.dds.g13;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

import json.BeanToJson;

public class Device extends BeanToJson	 {
	
	@Expose private Boolean deviceOn;
	@Expose private BigDecimal consumptionkWh;
	@Expose private String name;
	
	public Device(Boolean deviceOn, BigDecimal consumptionkWh, String name) {
		this.deviceOn = deviceOn;
		this.consumptionkWh = consumptionkWh;
		this.name = name;
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	@Override
	public Device getObj() {
		return this;
	}

}
