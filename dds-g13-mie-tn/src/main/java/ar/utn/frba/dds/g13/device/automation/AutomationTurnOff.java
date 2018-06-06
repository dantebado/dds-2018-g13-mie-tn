package ar.utn.frba.dds.g13.device.automation;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class AutomationTurnOff implements DeviceAction {
	
	SmartDevice device;
	
	public AutomationTurnOff(SmartDevice device) {
		this.device = device;
	}

	public void execute() {
		device.turnOff();
	}

}
