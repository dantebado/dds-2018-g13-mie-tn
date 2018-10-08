package ar.utn.frba.dds.g13.device.automation.rules;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

@Entity
@Table(name = "Rule")
public abstract class AutomationRule {
	
	@Id								
	@GeneratedValue
	@Column(name="rule_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="actuator_id")
	@Expose Actuator actuator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sensor_id")
	@Expose Sensor sensor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public AutomationRule(){
		super();
	}
	
	public List<DeviceAction> analyze(List<Measure> measures) {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		actions.addAll(getActionsToExecute(measures));
		return actions;
	}
	
	protected abstract List<DeviceAction> getActionsToExecute(List<Measure> measures);

}
