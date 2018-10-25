package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.json.BeanToJson;
import ar.utn.frba.dds.g13.user.Residence;

@Entity
@Table(name = "Device")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="device_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Device extends BeanToJson {
	
	@Id								
	@GeneratedValue
	@Column(name="device_id")
	private Long id;
	
	@Column(name="name")
	@Expose String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="residence_id")
	@Expose Residence residence;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="deviceinfo_id")
	@Expose DeviceInfo device_info;
	
	@Transient
	DeviceInfoTable Table = DeviceInfoTable.getInstance();
	
	public DeviceInfoTable getTable() {
		return Table;
	}
	
	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeviceInfo getDevice_info() {
		return device_info;
	}

	public void setDevice_info(DeviceInfo device_info) {
		this.device_info = device_info;
	}

	public BigDecimal getHourlyConsumption() {
		return new BigDecimal(device_info.getConsumption());
	}
	
	public Device() {
		super();
	}
	
	public Device(String name, DeviceInfo info) {
		this.name = name;
		if(info == null) {
			this.device_info = DeviceInfoTable.getDeviceByName(name);
		} else {
			this.device_info = info;
		}
	}
	
	public abstract boolean isSmart();

}
