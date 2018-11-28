package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class DeviceOn implements DeviceState {

	public boolean isOn(Turnable device) {
		return true;
	}

	public boolean isOff(Turnable device) {
		return false;
	}

	public boolean isEnergySaving(Turnable device) {
		return false;
	}

	public boolean turnOn(SmartDevice device) { return false; }

	public boolean turnOff(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceOff());
		System.out.println("Se apagó el device");
		return true;
	}

	public boolean turnEnergySaving(SmartDevice device) {
		device.saveCurrentStatusInterval();
		device.setState(new DeviceEnergySaving());
		return true;
	}
	
	public String toString() {
		return "on";
	}

}
