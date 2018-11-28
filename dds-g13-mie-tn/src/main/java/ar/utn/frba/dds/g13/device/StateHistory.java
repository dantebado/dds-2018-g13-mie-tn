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

import ar.utn.frba.dds.g13.SparkApp;
import ar.utn.frba.dds.g13.user.Client;

@Entity
@Table(name = "StateHistory")
public class StateHistory {
	
	@Id								
	@GeneratedValue
	@Column(name="stateHistory_id")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start")
	Calendar start;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end")
	Calendar end;
	
	@Column(name="state")
	String state;
	
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public StateHistory(){
		super();
	}
	
	public StateHistory(Calendar start, Calendar end,
			String state, Device device) {
		this.start = start;
		this.end = end;
		this.state = state;
		this.device = device;
	}
	
	public BigDecimal consumtionInInterval(BigDecimal hourlyConsumption) {
		float intervalInHours = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		return (!state.equalsIgnoreCase("off"))? hourlyConsumption.multiply(new BigDecimal(intervalInHours)) : new BigDecimal(0);
	}
	
	public boolean isBetween(Calendar start, Calendar end) {
		return (start.getTimeInMillis() <= this.start.getTimeInMillis() &&
				this.end.getTimeInMillis() <= end.getTimeInMillis());
	}

	public double secondsOverlap(Calendar start, Calendar end) {
		System.out.println("BTW " + SparkApp.formatDateToString(start) + " AND " + SparkApp.formatDateToString(this.start));
		Calendar startO = start;
		Calendar endO = end;
		
		if(startO.getTimeInMillis() > this.start.getTimeInMillis()) {
			startO = this.start;
		}
		
		System.out.println("      " + SparkApp.formatDateToString(startO));
		System.out.println("BTW " + SparkApp.formatDateToString(end) + " AND " + SparkApp.formatDateToString(this.end));
	
		if(this.end.getTimeInMillis() <= endO.getTimeInMillis()) {
			endO = this.end;
		}
		System.out.println("      " + SparkApp.formatDateToString(endO));
		
		long e = endO.getTimeInMillis(); long s = startO.getTimeInMillis();
		double d = (e-s) / (6000);
		
		System.out.println("             " + d);
		if(d<0)d=0;
		return d;
	}
	
	public BigDecimal consumptionBetween(Calendar start, Calendar end) {
		BigDecimal ovl = new BigDecimal(secondsOverlap(start, end));
		return (!state.equalsIgnoreCase("off"))? device.getHourlyConsumption().divide(new BigDecimal(60)).multiply(ovl) : new BigDecimal(0);
	}

}

