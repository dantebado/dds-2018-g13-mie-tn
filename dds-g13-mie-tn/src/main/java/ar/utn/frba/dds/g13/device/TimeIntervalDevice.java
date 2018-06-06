package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.util.Date;

public class TimeIntervalDevice {
	
	Date start;
	Date end;
	Boolean consuming; //Cambiar a estado
	
	public TimeIntervalDevice(Date start, Date end,
			Boolean consuming) {
		this.start = start;
		this.end = end;
		this.consuming = consuming;
	}
	
	public BigDecimal consumtionInInterval(BigDecimal hourlyConsumption) {
		float intervalInHours = (end.getTime() - start.getTime()) / (1000 * 60 * 60);
		return (consuming)? hourlyConsumption.multiply(new BigDecimal(intervalInHours)) : new BigDecimal(0);
	}
	
	public boolean isBetween(Date start, Date end) {
		return (start.getTime() <= this.start.getTime() &&
				this.end.getTime() <= end.getTime());
	}

}

