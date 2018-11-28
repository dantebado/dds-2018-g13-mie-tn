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
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.cfg.Configuration;

import ar.utn.frba.dds.g13.SparkApp;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.states.DeviceEnergySaving;
import ar.utn.frba.dds.g13.device.states.DeviceOff;
import ar.utn.frba.dds.g13.device.states.DeviceOn;
import ar.utn.frba.dds.g13.device.states.DeviceState;
import ar.utn.frba.dds.g13.device.states.Turnable;
import ar.utn.frba.dds.g13.mosquitto.HibernateUtil;
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
	@LazyCollection(LazyCollectionOption.FALSE)
	List<StateHistory> stateHistory;

	@Transient
	Calendar registration;
	
	public List<StateHistory> getStateHistory() {
		return stateHistory;
	}

	public void setStateHistory(List<StateHistory> stateHistory) {
		this.stateHistory = stateHistory;
	}

	public DeviceState getState() {
		return state;
	}

	public SmartDevice(){
		super();
		if(state == null) state = new DeviceOn();
	}
	
	public SmartDevice getSmartDeviceById(Long device_id) {
	    Session session = null;
	    Object device = null;
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        device = (SmartDevice)session.load(SmartDevice.class, device_id);
	        Hibernate.initialize(device);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	    return (SmartDevice) device;
	}
	
	public SmartDevice(String name,
			DeviceInfo info,
			DeviceState state) {
		super(name, info);
		this.state = state;
	}
	
	
	public BigDecimal consumptionLastHours(float hours) {
		Calendar ld = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		Calendar start = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		Calendar end = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		end.set(Calendar.YEAR, ld.get(Calendar.YEAR));
		end.set(Calendar.MONTH, ld.get(Calendar.MONTH));
		end.set(Calendar.DAY_OF_MONTH, ld.get(Calendar.DAY_OF_MONTH));
		start.add(Calendar.MILLISECOND, (int) (end.getTimeInMillis() - (long)(hours * 60 * 60 * 1000)));
		return consumptionBetween(start, end);
	}
	
	public BigDecimal consumptionBetween(Calendar start, Calendar end) {
		BigDecimal acum = new BigDecimal(0);
		for(StateHistory interval : stateHistory) {
			BigDecimal consumption = interval.consumptionBetween(start, end);
			acum = acum.add(consumption);
		}
		Calendar ls = null;
		if(stateHistory.size() == 0) {
			ls = registration;
		} else {
			ls = stateHistory.get(stateHistory.size()-1).getEnd();
		}
		Calendar le = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		StateHistory st = new StateHistory(ls, le, state.toString(), this);
		BigDecimal consumption = st.consumptionBetween(start, end);
		acum = acum.add(consumption);
		
		return acum;
	}
	
	public void init_in_case() {
		if(stateHistory.size() == 0) {
			this.registration = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
			System.out.println("REGISTRATION " + SparkApp.formatDateToString(registration));
		} else {
			StateHistory last = stateHistory.get(stateHistory.size()-1);
			if(last.getState().equalsIgnoreCase("on")) {
				state = new DeviceOn();
			} else if(last.getState().equalsIgnoreCase("off")) {
				state = new DeviceOff();
			} else {
				state = new DeviceEnergySaving();
			}
		}
		
		System.out.println("DEVICE " + this.getId() + " is " + state.toString());
	}
	
	public void addInterval(String state, Calendar start, Calendar end) {
		stateHistory.add(new StateHistory(start, end, state, this));
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
	
	public void saveCurrentStatusInterval() {
		this.addInterval(state.toString(),
				(stateHistory.size() == 0) ? registration : stateHistory.get(stateHistory.size()-1).getEnd(),
				Calendar.getInstance(TimeZone.getTimeZone("GMT-3")));
		SparkApp.saveData();
	}

	public void turnOn() throws MqttException, InterruptedException {
		state.turnOn(this);
	}

	public void turnOff() throws MqttException, InterruptedException {
		state.turnOff(this);
	}

	public void turnEnergySaving() throws MqttException, InterruptedException {
		state.turnEnergySaving(this);
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
