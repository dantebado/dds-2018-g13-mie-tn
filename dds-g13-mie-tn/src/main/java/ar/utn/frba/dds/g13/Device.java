package ar.utn.frba.dds.g13;

import com.google.gson.annotations.Expose;

import json.BeanToJson;

public class Device extends BeanToJson	 {
	
	@Expose private Boolean on;
	@Expose private float consumptionkWh;
	@Expose private String name;
	
	public Device(Boolean on, float consumptionkWh, String  name) {
		setOn(on);
		setConsumptionkWh(consumptionkWh);
		setName(name);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	public void setOn(Boolean on) {
		this.on = on;
	}

	public void setConsumptionkWh(float consumptionkWh) {
		this.consumptionkWh = consumptionkWh;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Device getObj() {
		return this;
	}

}
