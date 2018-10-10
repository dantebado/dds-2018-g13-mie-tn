package ar.utn.frba.dds.g13;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnEnergySavingRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnOffWhenCold;
import ar.utn.frba.dds.g13.device.sensor.DeviceStateSensor;
import ar.utn.frba.dds.g13.device.sensor.Sensor;
import ar.utn.frba.dds.g13.device.sensor.TemperatureSensor;
import ar.utn.frba.dds.g13.device.states.DeviceOn;

public class App {
    public static void main( String[] args ) {
    	
    	SmartDevice device = new SmartDevice("Aire Acondicionado",
    			new BigDecimal(15), new ArrayList<TimeIntervalDevice>(), new DeviceOn());
    	
    	TemperatureSensor sensor_1 = new TemperatureSensor(2, device);
    	DeviceStateSensor sensor_2 = new DeviceStateSensor(5, device);
    	sensor_1.start();
    	sensor_2.start();
    	List<Sensor> sensors = new ArrayList<Sensor>();
    	sensors.add(sensor_1);
    	sensors.add(sensor_2);
    	
    	List<AutomationRule> rules = new ArrayList<AutomationRule>();
    	rules.add(new TurnOffWhenCold());
    	rules.add(new TurnEnergySavingRule());
    	
    	Actuator actuator = new Actuator(device, rules, sensors);    	
    	
    }
}
