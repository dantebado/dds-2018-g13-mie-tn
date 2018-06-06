package ar.utn.frba.dds.g13.device.automation;

import ar.utn.frba.dds.g13.device.SmartDevice;

public class AutomationTurnEnergySaving implements DeviceAction {
	
	SmartDevice device;
	
	public AutomationTurnEnergySaving(SmartDevice device) {
		this.device = device;
	}

	public void execute() {
		device.turnEnergySaving();
	}

}
