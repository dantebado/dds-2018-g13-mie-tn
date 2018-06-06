package ar.utn.frba.dds.g13.device.sensor;

public abstract class Sensor {
	
	Measure lastMeasure;
	
	public Measure getLastMeasure() {
		return lastMeasure;
	}
	
	public Measure measure() {
		Measure nm = measureValue();
		lastMeasure = nm;
		return nm;
	}
	
	public abstract Measure measureValue();

}
