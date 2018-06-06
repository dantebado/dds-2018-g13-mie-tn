package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class SmartDevice extends Device {
	
	Boolean isOn;
	List<TimeIntervalDevice> consumptionHistory;

	public SmartDevice(String name,
			BigDecimal hourlyConsumption, boolean isOn,
			List<TimeIntervalDevice> consumptionHistory) {
		super(name, hourlyConsumption);
		this.isOn = isOn;
		this.consumptionHistory = consumptionHistory;
	}
	
	public BigDecimal consumptionLastHours(float hours) {
		LocalDate ld = LocalDate.now();
		Date end = new Date(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
		Date start = new Date((end.getTime() - (long)(hours * 60 * 60 * 1000)));
		return consumptionBetween(start, end);
	}
	
	public BigDecimal consumptionBetween(Date start, Date end) {
		BigDecimal acum = new BigDecimal(0);
		for(TimeIntervalDevice interval : consumptionHistory) {
			if(interval.isBetween(start, end)) {
				acum.add(interval.consumtionInInterval(hourlyConsumption));
			}
		}
		return acum;
	}

	@Override
	public boolean isSmart() {
		return true;
	}

	public boolean isOn() {
		return isOn;
	}

}
