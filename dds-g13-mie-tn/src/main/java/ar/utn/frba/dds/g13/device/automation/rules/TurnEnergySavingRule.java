package ar.utn.frba.dds.g13.device.automation.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.utn.frba.dds.g13.device.automation.AutomationTurnOn;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.MeasureMagnitude;

@Entity
@DiscriminatorValue("TURNENERGYSAVING")
public class TurnEnergySavingRule extends AutomationRule {

	@Transient
	public static int[] required_sensors = {0, 1};
	
	@Override
	protected List<DeviceAction> getActionsToExecute(List<Measure> measures) {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		
		boolean flagTemperature = false;
		boolean flagES= false;
		
		for(Measure measure : measures) {
			
			if(measure.getMagnitude().equalsIgnoreCase("TEMPERATURE")) {
				if(measure.getValue().compareTo(new BigDecimal(10)) > 0) {
					flagTemperature = true;
				}
			} else if(measure.getMagnitude().equalsIgnoreCase("DEVICE_STATE")) {
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
