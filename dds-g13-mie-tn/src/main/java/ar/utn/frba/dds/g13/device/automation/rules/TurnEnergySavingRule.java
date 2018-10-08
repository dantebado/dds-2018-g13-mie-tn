package ar.utn.frba.dds.g13.device.automation.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ar.utn.frba.dds.g13.device.automation.AutomationTurnOn;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.MeasureMagnitude;

public class TurnEnergySavingRule extends AutomationRule {
	
	@Override
	protected List<DeviceAction> getActionsToExecute(List<Measure> measures) {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		
		boolean flagTemperature = false;
		boolean flagES= false;
		
		for(Measure measure : measures) {
			
			if(measure.getMagnitude() == "TEMPERATURE") {
				if(measure.getValue().compareTo(new BigDecimal(10)) > 0) {
					flagTemperature = true;
				}
			} else if(measure.getMagnitude() == "DEVICE_STATE") {
				if(measure.getValue().compareTo(new BigDecimal(-1)) == 0) {
					flagES = true;
				}
			}
			
		}
		if(flagES && flagTemperature) {
			actions.add(new AutomationTurnOn());
		}
		return actions;
	}

}
