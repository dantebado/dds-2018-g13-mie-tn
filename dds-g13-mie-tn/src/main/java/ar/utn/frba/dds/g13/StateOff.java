package ar.utn.frba.dds.g13;

import java.math.BigDecimal;

public class StateOff implements StateDevice {

	@Override
	public boolean isOn(SmartDevice device) {
		return false;
	}

	@Override
	public boolean isOff(SmartDevice device) {
		return true;
	}

	@Override
	public BigDecimal energyConsumptionHours(SmartDevice device, float hours) {
		return new BigDecimal(0);
	}

	@Override
	public void setDeviceOn(SmartDevice device) {
		device.setState(new StateOn());
	}

	@Override
	public void setDeviceOff(SmartDevice device) { }

	@Override
	public void setDeviceEnergySaving(SmartDevice device) {
		device.setState(new StateEnergySaving());
	}

}
