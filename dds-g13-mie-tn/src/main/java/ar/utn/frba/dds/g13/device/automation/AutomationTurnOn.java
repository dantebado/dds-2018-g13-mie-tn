package ar.utn.frba.dds.g13.device.automation;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class AutomationTurnOn implements DeviceAction {
	
	public void execute(SmartDevice device) {
		device.turnOn();
	}

}
