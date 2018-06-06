package ar.utn.frba.dds.g13.category;

import java.math.BigDecimal;

public class Category {
	
	String name;
	float minConsumption;
	float maxConsumption;
	BigDecimal fixedCharge;
	BigDecimal variableCharge;
	
	public Category(String name,
			float minConsumption, float maxConsumption,
			BigDecimal fixedCharge, BigDecimal variableCharge) {
		this.name = name;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
		this.fixedCharge = fixedCharge;
		this.variableCharge = variableCharge;
	}
	
	public Boolean belongsToCategory(BigDecimal monthlyConsumption) {
		float consumption = (float) monthlyConsumption.doubleValue();
		return minConsumption < consumption && consumption <= maxConsumption; 
	}
	
	public BigDecimal estimateBill(BigDecimal monthlyConsumption) {
		return fixedCharge.add(variableCharge.multiply(monthlyConsumption)); 
	}

}
