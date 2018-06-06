package ar.utn.frba.dds.g13.device.automation;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class AutomationTurnOn implements DeviceAction {
	
	SmartDevice device;
	
	public AutomationTurnOn(SmartDevice device) {
		this.device = device;
	}

	public void execute() {
		device.turnOn();
	}

}
