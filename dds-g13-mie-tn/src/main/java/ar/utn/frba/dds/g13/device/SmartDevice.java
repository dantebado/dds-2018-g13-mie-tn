package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import ar.utn.frba.dds.g13.device.states.DeviceState;
import ar.utn.frba.dds.g13.device.states.Turnable;

public class SmartDevice extends Device implements Turnable {
	
	DeviceState state;
	List<TimeIntervalDevice> consumptionHistory;

	public SmartDevice(String name,
			BigDecimal hourlyConsumption,
			List<TimeIntervalDevice> consumptionHistory,
			DeviceState state) {
		super(name, hourlyConsumption);
		this.consumptionHistory = consumptionHistory;
		this.state = state;
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
	
	//Patron State
	
	public void setState(DeviceState state) {
		this.state = state;
	}

	public boolean isOn() {
		return state.isOn(this);
	}

	public boolean isOff() {
		return state.isOff(this);
	}

	public boolean isEnergySaving() {
		return state.isEnergySaving(this);
	}

	public void turnOn() {
		state.turnOn(this);
	}

	public void turnOff() {
		state.turnOff(this);
	}

	public void turnEnergySaving() {
		state.turnEnergySaving(this);
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
