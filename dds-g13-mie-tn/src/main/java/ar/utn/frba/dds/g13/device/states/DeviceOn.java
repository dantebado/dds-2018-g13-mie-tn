package ar.utn.frba.dds.g13.device.states;

public class DeviceOn implements DeviceState {

	public boolean isOn(Turnable device) {
		return true;
	}

	public void turnOn(Turnable device) { }

	public void turnOff(Turnable device) {
		device.setState(new DeviceOff());
	}

	public void turnEnergySaving(Turnable device) {
		device.setState(new DeviceEnergySaving());
	}

}
