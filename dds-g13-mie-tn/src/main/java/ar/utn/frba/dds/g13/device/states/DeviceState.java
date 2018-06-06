package ar.utn.frba.dds.g13.device.states;

import ar.utn.frba.dds.g13.device.SmartDevice;

public interface DeviceState {
	
	public boolean isOn(SmartDevice device);
	
	public void turnOn(SmartDevice device);
	public void turnOff(SmartDevice device);
	public void turnEnergySaving(SmartDevice device);

}
