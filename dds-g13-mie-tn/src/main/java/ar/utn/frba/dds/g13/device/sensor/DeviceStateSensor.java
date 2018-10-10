package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("STATESENSOR")
public class DeviceStateSensor extends Sensor {
	
	public DeviceStateSensor(float intervalInSeconds, SmartDevice device) {
		super(intervalInSeconds, device);
	}
	
	protected DeviceStateSensor() {
	}

	@Override
	protected Measure measureValue() { //TODO Determinación del valor
		if(device.isOn()) {
			return new Measure(new BigDecimal(1), "DEVICE_STATE");
		} else if(device.isOff()) {
			return new Measure(new BigDecimal(-1), "DEVICE_STATE");
		} else if(device.isEnergySaving()) {
			return new Measure(new BigDecimal(0), "DEVICE_STATE");
		}
		return null;
	}
	
}
