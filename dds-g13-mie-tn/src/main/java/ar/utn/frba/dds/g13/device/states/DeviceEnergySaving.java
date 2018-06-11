package ar.utn.frba.dds.g13.device.states;

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

	public void turnOn(Turnable device) {
		device.setState(new DeviceOn());
	}

	public void turnOff(Turnable device) {
		device.setState(new DeviceOff());
	}

	public void turnEnergySaving(Turnable device) { }
}
