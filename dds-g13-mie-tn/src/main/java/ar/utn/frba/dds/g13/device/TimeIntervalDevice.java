package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.user.Client;

@Entity
@Table(name = "TimeIntervalDevice")
public class TimeIntervalDevice {
	
	@Id								
	@GeneratedValue
	@Column(name="timeIntervalDevice_id")
	private Long id;
	
	@Column(name="start")
	Date start;
	
	@Column(name="end")
	Date end;
	
	@Column(name="consuming")
	Boolean consuming; //Cambiar a estado
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="device_id")
	@Expose Device device;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Boolean getConsuming() {
		return consuming;
	}

	public void setConsuming(Boolean consuming) {
		this.consuming = consuming;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public TimeIntervalDevice(){
		super();
	}
	
	public TimeIntervalDevice(Date start, Date end,
			Boolean consuming, Device device) {
		this.start = start;
		this.end = end;
		this.consuming = consuming;
		this.device = device;
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

