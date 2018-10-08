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
@Table(name = "StateHistory")
public class StateHistory {
	
	@Id								
	@GeneratedValue
	@Column(name="stateHistory_id")
	private Long id;
	
	@Column(name="start")
	Date start;
	
	@Column(name="end")
	Date end;
	
	@Column(name="state")
	String state;
	
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
	
	public StateHistory(Date start, Date end,
			String state, Device device) {
		this.start = start;
		this.end = end;
		this.state = state;
		this.device = device;
	}

}

