package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceOff implements DeviceState {

	public boolean isOn(SmartDevice device) {
		return false;
	}

	public void turnOn(SmartDevice device) {
		device.setState(new DeviceOn());
	}

	public void turnOff(SmartDevice device) { }

	public void turnEnergySaving(SmartDevice device) {
		device.setState(new DeviceEnergySaving());
	}
}
