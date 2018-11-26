package ar.utn.frba.dds.g13.device.automation;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.eclipse.paho.client.mqttv3.MqttException;

import ar.utn.frba.dds.g13.device.SmartDevice;

@Entity
@Table(name = "DeviceAction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="deviceAction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceAction {
	
	@Id								
	@GeneratedValue
	@Column(name="deviceAction_id")
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract void execute(SmartDevice device, Actuator actuator) throws MqttException, InterruptedException;

}