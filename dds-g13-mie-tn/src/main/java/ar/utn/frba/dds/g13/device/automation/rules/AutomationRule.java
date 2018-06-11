package ar.utn.frba.dds.g13.device.automation.rules;

import java.util.ArrayList;
import java.util.List;

import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;

public abstract class AutomationRule {
	
	public List<DeviceAction> analyze(List<Measure> measures) {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		actions.addAll(getActionsToExecute(measures));
		return actions;
	}
	
	protected abstract List<DeviceAction> getActionsToExecute(List<Measure> measures);

}
