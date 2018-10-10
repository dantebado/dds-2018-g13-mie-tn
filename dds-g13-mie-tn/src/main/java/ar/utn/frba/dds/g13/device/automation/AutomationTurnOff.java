package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TURNOFF")
public class AutomationTurnOff extends DeviceAction {
	
	public void execute(SmartDevice device) {
		device.turnOff();
	}
	
	public AutomationTurnOff() {
		
	}

}
