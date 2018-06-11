package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceStateSensor extends Sensor {
	
	SmartDevice device;
	
	public DeviceStateSensor(float intervalInSeconds, SmartDevice device) {
		super(intervalInSeconds);
		this.device = device;
	}

	@Override
	protected Measure measureValue() { //TODO Determinación del valor
		if(device.isOn()) {
			return new Measure(new BigDecimal(1), MeasureMagnitude.DEVICE_STATE);
		} else if(device.isOff()) {
			return new Measure(new BigDecimal(-1), MeasureMagnitude.DEVICE_STATE);
		} else if(device.isEnergySaving()) {
			return new Measure(new BigDecimal(0), MeasureMagnitude.DEVICE_STATE);
		}
		return null;
	}
	
}
