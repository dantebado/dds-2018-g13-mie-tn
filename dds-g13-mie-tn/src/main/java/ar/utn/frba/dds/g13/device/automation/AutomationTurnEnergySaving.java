package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.eclipse.paho.client.mqttv3.MqttException;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TURNENERGYSAVING")
public class AutomationTurnEnergySaving extends DeviceAction {
	
	public void execute(SmartDevice device) throws MqttException, InterruptedException {
		device.turnEnergySaving();
	}
	
	public AutomationTurnEnergySaving() {
		
	}

}
