package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public interface DeviceState {
	
	public String toString();
	
	public boolean isOn(Turnable device);
	public boolean isOff(Turnable device);
	public boolean isEnergySaving(Turnable device);
	
	public boolean turnOn(SmartDevice device);
	public boolean turnOff(SmartDevice device);
	public boolean turnEnergySaving(SmartDevice device);

}
