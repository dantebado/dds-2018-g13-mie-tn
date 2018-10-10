package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TURNENERGYSAVING")
public class AutomationTurnEnergySaving extends DeviceAction {
	
	public void execute(SmartDevice device) {
		device.turnEnergySaving();
	}
	
	public AutomationTurnEnergySaving() {
		
	}

}
