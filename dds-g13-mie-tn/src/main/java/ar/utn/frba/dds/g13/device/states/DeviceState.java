package ar.utn.frba.dds.g13.device.states;

public interface DeviceState {
	
	public boolean isOn(Turnable device);
	public boolean isOff(Turnable device);
	public boolean isEnergySaving(Turnable device);
	
	public void turnOn(Turnable device);
	public void turnOff(Turnable device);
	public void turnEnergySaving(Turnable device);

}
