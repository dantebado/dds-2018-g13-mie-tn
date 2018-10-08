package ar.utn.frba.dds.g13.device.sensor;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.area.Area;

@Entity
@Table(name = "Measure")
public class Measure {
	
	@Id								
	@GeneratedValue
	@Column(name="measure_id")
	private Long id;
	
	@Column(name="value")
	BigDecimal value;
	
	@Column(name="magnitude")
	String magnitude;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sensor_id")
	@Expose Sensor sensor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}

	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Measure(){
		super();
	}
	
	public Measure(BigDecimal value, String magnitude) {
		this.value = value;
		this.magnitude = magnitude;
	}
	
	public BigDecimal getValue() {
		return value;
	}

	public String getMagnitude() {
		return magnitude;
	}

}
