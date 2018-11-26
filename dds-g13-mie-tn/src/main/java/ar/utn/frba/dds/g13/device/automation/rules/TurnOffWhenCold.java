package ar.utn.frba.dds.g13.device.automation.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.utn.frba.dds.g13.device.automation.AutomationTurnOff;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;
import ar.utn.frba.dds.g13.device.sensor.Measure;
import ar.utn.frba.dds.g13.device.sensor.MeasureMagnitude;

@Entity
@DiscriminatorValue("TURNOFFWHENCOLD")
public class TurnOffWhenCold extends AutomationRule {

	@Transient
	public static int[] required_sensors = {1};

	@Override
	protected List<DeviceAction> getActionsToExecute(List<Measure> measures) {
		List<DeviceAction> actions = new ArrayList<DeviceAction>();
		for(Measure measure : measures) {
			System.out.println(measure.getMagnitude() + " " + measure.getValue());
			if(measure.getMagnitude().equalsIgnoreCase("temperature")) {
				System.out.println(measure.getValue().doubleValue());
				if(measure.getValue().doubleValue() < 20) {
					actions.add(new AutomationTurnOff());
					System.out.println("TURN OFF");
				}
			}
		}
		return actions;
	}

}
