package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.eclipse.paho.client.mqttv3.MqttException;

import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.mosquitto.SGEPubMQTT;

@Entity
@DiscriminatorValue("TURNENERGYSAVING")
public class AutomationTurnEnergySaving extends DeviceAction {
	
	public void execute(SmartDevice device, Actuator actuator) throws MqttException, InterruptedException {
		device.turnEnergySaving();
		SGEPubMQTT.sendAction(Long.toString(actuator.getId()), "MODO AHORRO ENERGIA", "INTELIGENTE");
	}
	
	public AutomationTurnEnergySaving() {
		
	}

}
