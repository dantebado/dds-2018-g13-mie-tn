package ar.utn.frba.dds.g13.device.sensor;

import java.util.ArrayList;
import java.util.List;

import ar.utn.frba.dds.g13.device.automation.Actuator;

public abstract class Sensor extends Thread {
	
	Measure lastMeasure = null;
	float intervalInSeconds;
	List<Actuator> actuatorsToNotify;
	
	public Sensor(float intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
		actuatorsToNotify = new ArrayList<Actuator>();
	}
	
	public void addActuatorToNotify(Actuator actuator) {
		actuatorsToNotify.add(actuator);
	}
	
	public Measure getLastMeasure() {
		return (lastMeasure == null) ? measure() : lastMeasure;
	}
	
	private Measure measure() {
		Measure nm = measureValue();
		lastMeasure = nm;
		return nm;
	}
	
	public void run() {
		long lm = System.currentTimeMillis();
		while(true) {
			if (System.currentTimeMillis()-lm >= intervalInSeconds*1000) {
				lm = System.currentTimeMillis();				
				measure();
				for(Actuator actuator : actuatorsToNotify) {
					actuator.notifySensorChange();
				}
			}
		}
	}
	
	protected abstract Measure measureValue();

}
