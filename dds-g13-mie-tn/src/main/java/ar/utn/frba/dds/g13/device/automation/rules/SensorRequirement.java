package ar.utn.frba.dds.g13.device.automation.rules;

import java.util.List;

import ar.utn.frba.dds.g13.device.sensor.Sensor;

public class SensorRequirement {
	
	String sensor_type;
	
	List<Sensor> availables;
	
	public SensorRequirement(String sensor_type, List<Sensor> availables) {
		this.sensor_type = sensor_type;
		this.availables = availables;
	}

	public String getSensor_type() {
		return sensor_type;
	}

	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}

	public List<Sensor> getAvailables() {
		return availables;
	}

	public void setAvailables(List<Sensor> availables) {
		this.availables = availables;
	}

}
