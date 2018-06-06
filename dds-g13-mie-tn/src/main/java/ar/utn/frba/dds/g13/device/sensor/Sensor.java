package ar.utn.frba.dds.g13.device.sensor;

public abstract class Sensor extends Thread {
	
	Measure lastMeasure;
	float intervalInSeconds;
	
	public Sensor(float intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
	}
	
	public Measure getLastMeasure() {
		return lastMeasure;
	}
	
	public Measure measure() {
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
			}
		}
	}
	
	public abstract Measure measureValue();

}
