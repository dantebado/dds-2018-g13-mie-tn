package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

public class StandardDevice extends Device {
	
	BigDecimal dailyUseEstimation;

	public StandardDevice(String name,
			BigDecimal hourlyConsumption,
			BigDecimal dailyUseEstimation) {
		super(name, hourlyConsumption);
		this.dailyUseEstimation = dailyUseEstimation;
	}
	
	
	public BigDecimal getDailyUseEstimation() {
		return dailyUseEstimation;
	}


	@Override
	public boolean isSmart() {
		return false;
	}


	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
