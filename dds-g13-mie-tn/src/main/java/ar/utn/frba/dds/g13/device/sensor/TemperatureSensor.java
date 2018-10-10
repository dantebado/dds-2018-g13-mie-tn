package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TEMPERATURESENSOR")
public class TemperatureSensor extends Sensor {
	
	public TemperatureSensor(float intervalInSeconds, SmartDevice device) {
		super(intervalInSeconds, device);
	}
	
	protected TemperatureSensor() {
		
	}

	@Override
	protected Measure measureValue() { //TODO Determinación del valor
		return new Measure(new BigDecimal(15), "TEMPERATURE");
	}

}
