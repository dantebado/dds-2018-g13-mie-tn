package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

public class TemperatureSensor extends Sensor {
	
	public TemperatureSensor(float intervalInSeconds) {
		super(intervalInSeconds);
	}

	@Override
	protected Measure measureValue() { //TODO Determinación del valor
		return new Measure(new BigDecimal(15), "TEMPERATURE");
	}

}
