package ar.utn.frba.dds.g13.device.automation;

import java.util.ArrayList;
import java.util.List;

import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

public class Actuator {
	
	List<AutomationRule> rules;
	List<Sensor> sensors;
	SmartDevice device;
	
	public Actuator(SmartDevice device, List<AutomationRule> rules, List<Sensor> sensors) {
		this.device = device;
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
			action.execute(device);
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
