package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

public class StandardDevice extends Device {
	
	BigDecimal dailyUseEstimation;

	public StandardDevice(String name,
			BigDecimal hourlyConsumption,
			BigDecimal dailUseEstimation) {
		super(name, hourlyConsumption);
		this.dailyUseEstimation = dailUseEstimation;
	}

	@Override
	public boolean isSmart() {
		return false;
	}

}
