package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

public class Measure {
	
	BigDecimal value;
	MeasureMagnitude magnitude;
	
	public Measure(BigDecimal value, MeasureMagnitude magnitude) {
		this.value = value;
		this.magnitude = magnitude;
	}

}
