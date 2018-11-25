package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.eclipse.paho.client.mqttv3.MqttException;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@DiscriminatorValue("TURNOFF")
public class AutomationTurnOff extends DeviceAction {
	
	public void execute(SmartDevice device) throws MqttException, InterruptedException {
		device.turnOff();
	}
	
	public AutomationTurnOff() {
		
	}

}
