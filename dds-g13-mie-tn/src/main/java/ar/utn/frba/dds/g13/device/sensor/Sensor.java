package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.mosquitto.RecivedMeasure;
import ar.utn.frba.dds.g13.mosquitto.SGESubMQTT;

@Entity
@Table(name = "Sensor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="sensor_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Sensor extends Thread {

	@Transient
	public static String[] types = {"Estado de Dispositivo",	//0
									"Temperatura"};				//1
	
	public static Class searchClassByType(int type) {
		switch(type) {
		case 0:
			return DeviceStateSensor.class;
		case 1:
			return TemperatureSensor.class;
		}
		return null;
	}
	
	public static Sensor createByType(int type, float intervalInSeconds, SmartDevice device) {
		switch(type) {
		case 0:
			return new DeviceStateSensor(intervalInSeconds, device);
		case 1:
			return new TemperatureSensor(intervalInSeconds, device);
		}
		return null;
	}
	
	@Transient
	Measure lastMeasure = null;
	
	@Id								
	@GeneratedValue
	@Column(name="sensor_id")
	private Long id;
	
	@OneToMany(mappedBy = "sensor" , cascade = {CascadeType.ALL})
	@Expose static List<Measure> measures;
	
	@Column(name="intervalInSeconds")
	float intervalInSeconds;
	
	@ManyToMany(mappedBy = "sensors", fetch = FetchType.EAGER)
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
	
	public Sensor(float intervalInSeconds, SmartDevice device) {
		this.intervalInSeconds = intervalInSeconds;
		this.device = device;
		actuatorsToNotify = new ArrayList<Actuator>();
		
		this.start();
	}
	
	public void addActuatorToNotify(Actuator actuator) {
		actuatorsToNotify.add(actuator);
	}
	
	public Measure getLastMeasure() {
		return (lastMeasure == null) ? measure() : lastMeasure;
	}
	
	private Measure measure() {
		//Measure nm = measureValue();
		Measure nm = Sensor.getNextMeasure(id);
		System.out.println("SENSOR " + id + " MIDIENDO ");
		if (nm != null) {
			nm.setSensor(this);
			lastMeasure = nm;
		}
		System.out.println("      " + nm);
		return nm;
	}
	
	public static Measure getNextMeasure(long id) {
		List<RecivedMeasure> messures = SGESubMQTT.getListaMediciones();
		if (messures != null && messures.size() != 0) {
			List<RecivedMeasure> newMessures = null;
			RecivedMeasure messureFounded = null;
			boolean noneFounded = true;
			for(RecivedMeasure rm : SGESubMQTT.getListaMediciones()) {
				if ((rm.getId()).equals(Long.toString(id)) || noneFounded) {
					messureFounded = rm;
					noneFounded = false;
				}
				else {
					newMessures.add(rm);
				}
			}
			SGESubMQTT.setListaMediciones(newMessures);
			if(noneFounded) {
				return null;
			}
			return new Measure(new BigDecimal(messureFounded.getValue()), messureFounded.getMessure());	
		}
		return null;
	}
	
	public void run() {
		long lm = System.currentTimeMillis();
		while(true) {
			if (System.currentTimeMillis()-lm >= intervalInSeconds*1000) {
				lm = System.currentTimeMillis();				
				measure();
				for(Actuator actuator : actuatorsToNotify) {
					try {
						actuator.notifySensorChange();
					} catch (MqttException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	protected abstract Measure measureValue();

}