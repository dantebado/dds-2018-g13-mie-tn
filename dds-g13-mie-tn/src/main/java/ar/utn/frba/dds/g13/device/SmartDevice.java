package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ar.utn.frba.dds.g13.device.states.DeviceState;
import ar.utn.frba.dds.g13.device.states.Turnable;


@Entity
@DiscriminatorValue("SMART")
public class SmartDevice extends Device implements Turnable {
	
	@Transient
	DeviceState state;
	
	@OneToMany(mappedBy = "device")
	List<TimeIntervalDevice> consumptionHistory;
	
	@OneToMany(mappedBy = "device")
	List<StateHistory> stateHistory;
	
	@OneToMany(mappedBy = "smartdevice")
	
	public List<TimeIntervalDevice> getConsumptionHistory() {
		return consumptionHistory;
	}

	public List<StateHistory> getStateHistory() {
		return stateHistory;
	}

	public void setStateHistory(List<StateHistory> stateHistory) {
		this.stateHistory = stateHistory;
	}

	public void setConsumptionHistory(List<TimeIntervalDevice> consumptionHistory) {
		this.consumptionHistory = consumptionHistory;
	}

	public DeviceState getState() {
		return state;
	}

	public SmartDevice(){
		super();
	}
	
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
