package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceOff implements DeviceState {

	public boolean isOn(Turnable device) {
		return false;
	}

	public boolean isOff(Turnable device) {
		return true;
	}

	public boolean isEnergySaving(Turnable device) {
		return false;
	}

	public boolean turnOn(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceOn());
		System.out.println("Se prendió el device");
		return true;
	}

	public boolean turnOff(SmartDevice device) { return false; }

	public boolean turnEnergySaving(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceEnergySaving());
		return true;
	}
	
	public String toString() {
		return "off";
	}
}
