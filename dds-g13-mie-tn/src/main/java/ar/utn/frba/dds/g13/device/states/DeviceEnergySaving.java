package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceEnergySaving implements DeviceState {

	public boolean isOn(Turnable device) {
		return false;
	}

	public boolean isOff(Turnable device) {
		return false;
	}

	public boolean isEnergySaving(Turnable device) {
		return true;
	}

	public boolean turnOn(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceOn());
		return true;
	}

	public boolean turnOff(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceOff());
		return true;
	}

	public boolean turnEnergySaving(SmartDevice device) { return false; }
	
	public String toString() {
		return "es";
	}
}
