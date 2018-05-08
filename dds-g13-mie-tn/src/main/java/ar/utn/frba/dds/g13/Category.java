package ar.utn.frba.dds.g13;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

import json.BeanToJson;

public class Category extends BeanToJson {
	
	@Expose private String name;
	@Expose private BigDecimal basePrice;
	@Expose private BigDecimal variablePrice;
	@Expose private BigDecimal monthlyConsumption;
	
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
