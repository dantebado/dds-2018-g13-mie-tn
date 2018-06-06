package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceOn implements DeviceState {

	public boolean isOn(SmartDevice device) {
		return true;
	}

	public void turnOn(SmartDevice device) { }

	public void turnOff(SmartDevice device) {
		device.setState(new DeviceOff());
	}

	public void turnEnergySaving(SmartDevice device) {
		device.setState(new DeviceEnergySaving());
	}

}
