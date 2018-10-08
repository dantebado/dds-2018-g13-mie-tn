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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

@Entity
@Table(name = "Actuator")
public class Actuator {
	
	@Id								
	@GeneratedValue
	@Column(name="residence_id")
	private Long id;
	
	@OneToMany(mappedBy = "actuator" , cascade = {CascadeType.ALL})
	List<AutomationRule> rules;
	
	@Transient//@OneToMany(mappedBy = "Actuator" , cascade = {CascadeType.ALL})
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
	}
	
	public Actuator(SmartDevice device, List<AutomationRule> rules, List<Sensor> sensors) {
		this.smartdevice = device;
		this.rules = rules;
		this.sensors = sensors;
		for(Sensor sensor : sensors) {
			sensor.addActuatorToNotify(this);
		}
	}
	
	public void notifySensorChange() {
		analyzeRules();
	}
	
	private void sendActionsToDevice(List<DeviceAction> actions) {
		for(DeviceAction action : actions) {
			action.execute(smartdevice);
		}
	}
	
	public void analyzeRules() {
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
