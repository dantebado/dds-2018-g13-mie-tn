package ar.utn.frba.dds.g13;

import java.math.BigDecimal;

import json.BeanToJson;

public class Category extends BeanToJson {
	
	private String name;
	private BigDecimal basePrice;
	private BigDecimal variablePrice;
	private BigDecimal monthlyConsumption;
	
	public Category(String name, BigDecimal basePrice,
			BigDecimal variablePrice, BigDecimal monthlyConsumption) {
		this.name = name;
		this.basePrice = basePrice;
		this.variablePrice = variablePrice;
		this.monthlyConsumption = monthlyConsumption;
	}
	
	/*COMPORTAMENTO*/
	
	public BigDecimal estimateBill(BigDecimal consumption) {
		return null;
	}
	
	/*GETTERS - SETTERS*/

	@Override
	public Category getObj() {
		return this;
	}

}
