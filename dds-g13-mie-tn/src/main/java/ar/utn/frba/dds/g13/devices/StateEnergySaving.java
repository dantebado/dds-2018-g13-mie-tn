package ar.utn.frba.dds.g13.devices;

import java.math.BigDecimal;

public class StateEnergySaving implements StateDevice {

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
		return null; //TODO: Calcular consumo
	}

	@Override
	public void setDeviceOn(SmartDevice device) {
		device.setState(new StateOn());
	}

	@Override
	public void setDeviceOff(SmartDevice device) {
		device.setState(new StateOff());
	}

	@Override
	public void setDeviceEnergySaving(SmartDevice device) {	}

}
