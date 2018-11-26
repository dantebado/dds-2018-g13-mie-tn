package ar.utn.frba.dds.g13.device.automation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

@Entity
@Table(name = "Actuator")

public class Actuator {
	@Transient
	public static ArrayList<Actuator> GLOBAL_ACTUATORS = new ArrayList<Actuator>();
	
	@Id								
	@GeneratedValue
	@Column(name="actuator_id")
	private Long id;
	
	@OneToMany(mappedBy = "actuator" , cascade = {CascadeType.ALL})
	List<AutomationRule> rules;
	
	@ManyToMany(
			cascade = { CascadeType.ALL },
			fetch = FetchType.EAGER)
    @JoinTable(
        name = "ActuatorSensor", 
        joinColumns = { @JoinColumn(name = "actuator_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "sensor_id") }
    )
	List<Sensor> sensors;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="device_id")
	@Expose SmartDevice smartdevice;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AutomationRule> getRules() {
		return rules;
	}

	public void setRules(List<AutomationRule> rules) {
		this.rules = rules;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public SmartDevice getDevice() {
		return smartdevice;
	}

	public void setDevice(SmartDevice device) {
		this.smartdevice = device;
	}

	public Actuator(){
		super();
		Actuator.GLOBAL_ACTUATORS.add(this);
	}
	
	public Actuator(SmartDevice device, List<AutomationRule> rules, List<Sensor> sensors) {
		this.smartdevice = device;
		this.rules = rules;
		this.sensors = sensors;
		for(Sensor sensor : sensors) {
			sensor.addActuatorToNotify(this);
		}
		for(AutomationRule rule : rules) {
			rule.setActuator(this);
		}
		if(!Actuator.GLOBAL_ACTUATORS.contains(this)) {
			Actuator.GLOBAL_ACTUATORS.add(this);	
		}
	}
	
	public void notifySensorChange() throws MqttException, InterruptedException {
		analyzeRules();
	}
	
	private void sendActionsToDevice(List<DeviceAction> actions) throws MqttException, InterruptedException {
		for(DeviceAction action : actions) {
			action.execute(smartdevice);
		}
	}
	
	public void analyzeRules() throws MqttException, InterruptedException {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		for(AutomationRule rule : rules) {
			actions.addAll(rule.analyze(getMeasureSet()));
		}
		
		//Tengo las acciones para ejecutar
		sendActionsToDevice(actions);
	}
	
	private List<Measure> getMeasureSet() {
		List<Measure> measures = new ArrayList<Measure>();
		for(Sensor sensor : sensors) {
			measures.add(sensor.getLastMeasure());
		}
		return measures;
	}

}
