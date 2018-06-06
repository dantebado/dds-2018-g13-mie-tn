package ar.utn.frba.dds.g13.device.states;

public class DeviceEnergySaving implements DeviceState {

	public boolean isOn(Turnable device) {
		return false;
	}

	public void turnOn(Turnable device) {
		device.setState(new DeviceOn());
	}

	public void turnOff(Turnable device) {
		device.setState(new DeviceOff());
	}

	public void turnEnergySaving(Turnable device) { }
}
