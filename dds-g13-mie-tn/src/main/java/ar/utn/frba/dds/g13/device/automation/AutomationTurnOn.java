package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TURNON")
public class AutomationTurnOn extends DeviceAction {
	
	public void execute(SmartDevice device) {
		device.turnOn();
	}
	public AutomationTurnOn() {
		
	}

}
