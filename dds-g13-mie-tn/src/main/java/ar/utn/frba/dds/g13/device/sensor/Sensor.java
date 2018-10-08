package ar.utn.frba.dds.g13.device.sensor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;

@Entity
@Table(name = "Sensor")
public abstract class Sensor extends Thread {
	
	@Transient
	Measure lastMeasure = null;
	
	@Id								
	@GeneratedValue
	@Column(name="sensor_id")
	private Long id;
	
	@OneToMany(mappedBy = "sensor" , cascade = {CascadeType.ALL})
	@Expose static List<Measure> measures;
	
	@OneToMany(mappedBy = "sensor" , cascade = {CascadeType.ALL})
	@Expose static List<AutomationRule> rules;
	
	@Column(name="intervalInSeconds")
	float intervalInSeconds;
	
	@Transient
	List<Actuator> actuatorsToNotify;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="device_id")
	SmartDevice device;
	
	
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static List<Measure> getMeasures() {
		return measures;
	}

	public static void setMeasures(List<Measure> measures) {
		Sensor.measures = measures;
	}

	public static List<AutomationRule> getRules() {
		return rules;
	}

	public static void setRules(List<AutomationRule> rules) {
		Sensor.rules = rules;
	}

	public float getIntervalInSeconds() {
		return intervalInSeconds;
	}

	public void setIntervalInSeconds(float intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
	}

	public SmartDevice getDevice() {
		return device;
	}

	public void setDevice(SmartDevice device) {
		this.device = device;
	}

	public Sensor(){
		super();
	}
	
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
