package ar.utn.frba.dds.g13;

import java.math.BigDecimal;

public class StateOn implements StateDevice {

	@Override
	public boolean isOn(SmartDevice device) {
		return true;
	}

	@Override
	public boolean isOff(SmartDevice device) {
		return false;
	}

	@Override
	public BigDecimal energyConsumptionHours(SmartDevice device, float hours) {
		return null; //TODO: Cálculo del conusmo
	}

	@Override
	public void setDeviceOn(SmartDevice device) { }

	@Override
	public void setDeviceOff(SmartDevice device) {
		device.setState(new StateOff());
	}

	@Override
	public void setDeviceEnergySaving(SmartDevice device) {
		device.setState(new StateEnergySaving());
	}

}
