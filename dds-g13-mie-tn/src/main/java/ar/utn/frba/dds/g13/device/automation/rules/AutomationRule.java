package ar.utn.frba.dds.g13.device.automation.rules;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

@Entity
@Table(name = "Rule")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="rule_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AutomationRule {

	@Transient
	public static String[] rules_names = {"Apagar cuando hace frío",		//0
											"Prender Ahorro de Energía",	//1
											"Apagar cuando hay parciales",	//2
											"Convertir en rata cuando sale la Luna"}; //3
	
	public static int[] getRequiredSensorsByType(String type_p) {
		int type = getRuleIndexByName(type_p);
		switch(type) {
			case 0:
				return TurnOffWhenCold.required_sensors;
			case 1:
				return TurnEnergySavingRule.required_sensors;
			default:
				return null;
		}
	}
	
	public static int getRuleIndexByName(String name) {
		int i=0;
		for(String s : rules_names) {
			if(s.equalsIgnoreCase(name.toLowerCase())) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Transient
	public static int[] required_sensors = {};
	
	@Id								
	@GeneratedValue
	@Column(name="rule_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="actuator_id")
	@Expose Actuator actuator;
	
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
