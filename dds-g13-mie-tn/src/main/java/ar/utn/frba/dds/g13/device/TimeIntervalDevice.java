package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.user.Client;

@Entity
@Table(name = "TimeIntervalDevice")
public class TimeIntervalDevice {
	
	@Id								
	@GeneratedValue
	@Column(name="timeIntervalDevice_id")
	private Long id;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start")
	Calendar start;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end")
	Calendar end;
	
	@Column(name="consuming")
	Boolean consuming; //Cambiar a estado
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="device_id")
	@Expose Device device;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
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
	
	public TimeIntervalDevice(Calendar start, Calendar end,
			Boolean consuming, Device device) {
		this.start = start;
		this.end = end;
		this.consuming = consuming;
		this.device = device;
	}
	
	public BigDecimal consumtionInInterval(BigDecimal hourlyConsumption) {
		float intervalInHours = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		return (consuming)? hourlyConsumption.multiply(new BigDecimal(intervalInHours)) : new BigDecimal(0);
	}
	
	public boolean isBetween(Calendar start, Calendar end) {
		return (start.getTimeInMillis() <= this.start.getTimeInMillis() &&
				this.end.getTimeInMillis() <= end.getTimeInMillis());
	}

	public float hoursOverlap(Calendar start, Calendar end) {
		Calendar startO = start;
		Calendar endO = end;
		if(startO.getTimeInMillis() <= this.start.getTimeInMillis()) startO = this.start;
		if(this.end.getTimeInMillis() <= endO.getTimeInMillis()) endO = this.end;
		return (endO.getTimeInMillis() - startO.getTimeInMillis()) / (1000 * 60 * 60);
	}

}

