package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.paho.client.mqttv3.MqttException;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.states.DeviceOn;
import ar.utn.frba.dds.g13.device.states.DeviceState;
import ar.utn.frba.dds.g13.device.states.Turnable;
import ar.utn.frba.dds.g13.mosquitto.SGEPubMQTT;


@Entity
@DiscriminatorValue("SMART")
public class SmartDevice extends Device implements Turnable {
	
	@Transient
	DeviceState state;
	
	@OneToMany(
		    mappedBy = "device", 
		    cascade = CascadeType.ALL, 
		    orphanRemoval = true
		)
	List<TimeIntervalDevice> consumptionHistory;
	
	@OneToMany(
		    mappedBy = "device", 
		    cascade = CascadeType.ALL, 
		    orphanRemoval = true
		)
	List<StateHistory> stateHistory;
	
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
		if(state == null) state = new DeviceOn();
	}
	
	public SmartDevice(String name,
			DeviceInfo info,
			List<TimeIntervalDevice> consumptionHistory,
			DeviceState state) {
		super(name, info);
		this.consumptionHistory = consumptionHistory;
		this.state = state;
	}
	
	
	public BigDecimal consumptionLastHours(float hours) {
		Calendar ld = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar start = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar end = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		end.set(Calendar.YEAR, ld.get(Calendar.YEAR));
		end.set(Calendar.MONTH, ld.get(Calendar.MONTH));
		end.set(Calendar.DAY_OF_MONTH, ld.get(Calendar.DAY_OF_MONTH));
		start.add(Calendar.MILLISECOND, (int) (end.getTimeInMillis() - (long)(hours * 60 * 60 * 1000)));
		return consumptionBetween(start, end);
	}
	
	public BigDecimal consumptionBetween(Calendar start, Calendar end) {
		BigDecimal acum = new BigDecimal(0);
		for(TimeIntervalDevice interval : consumptionHistory) {
			BigDecimal consumption = this.getHourlyConsumption().multiply(new BigDecimal(interval.hoursOverlap(start, end)));
			acum = acum.add(consumption);
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

	public void turnOn() throws MqttException, InterruptedException {
		state.turnOn(this);
		SGEPubMQTT.sendAction(Long.toString(this.getId()), "ENCENDER", "INTELIGENTE");
	}

	public void turnOff() throws MqttException, InterruptedException {
		System.out.println(state);
		state.turnOff(this);
		SGEPubMQTT.sendAction(Long.toString(this.getId()), "APAGAR", "INTELIGENTE");
	}

	public void turnEnergySaving() throws MqttException, InterruptedException {
		state.turnEnergySaving(this);
		SGEPubMQTT.sendAction(Long.toString(this.getId()), "MODO AHORRO ENERGIA", "INTELIGENTE");
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
