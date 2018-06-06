package ar.utn.frba.dds.g13.device.states;

public class DeviceOff implements DeviceState {

	public boolean isOn(Turnable device) {
		return false;
	}

	public void turnOn(Turnable device) {
		device.setState(new DeviceOn());
	}

	public void turnOff(Turnable device) { }

	public void turnEnergySaving(Turnable device) {
		device.setState(new DeviceEnergySaving());
	}
}
