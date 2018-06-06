package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceEnergySaving implements DeviceState {

	public boolean isOn(SmartDevice device) {
		return false;
	}

	public void turnOn(SmartDevice device) {
		device.setState(new DeviceOn());
	}

	public void turnOff(SmartDevice device) {
		device.setState(new DeviceOff());
	}

	public void turnEnergySaving(SmartDevice device) { }
}
