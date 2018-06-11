package ar.utn.frba.dds.g13.device.states;

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

	public void turnOn(Turnable device) { }

	public void turnOff(Turnable device) {
		device.setState(new DeviceOff());
		System.out.println("Se apagó el device");
	}

	public void turnEnergySaving(Turnable device) {
		device.setState(new DeviceEnergySaving());
	}

}
