package ar.utn.frba.dds.g13.device.deviceinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DeviceInfo")
public class DeviceInfo {
	
	
	@Id								
	@GeneratedValue
	@Column(name="deviceInfo_id")
	private Long id;
	
	@Column(name="name")
	String name;
	
	@Column(name="desc")
	String desc;
	
	@Column(name="inteligente")
	boolean inteligente;
	
	@Column(name="bajoConsumo")
	boolean bajoConsumo;
	
	@Column(name="consumption")
	double consumption;
	
	@Column(name="minHsUse")
	int minHsUse;
	
	@Column(name="maxHsUse")
	int maxHsUse;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public int getMinHsUse() {
		return minHsUse;
	}


	public void setMinHsUse(int minHsUse) {
		this.minHsUse = minHsUse;
	}


	public int getMaxHsUse() {
		return maxHsUse;
	}


	public void setMaxHsUse(int maxHsUse) {
		this.maxHsUse = maxHsUse;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public boolean isInteligente() {
		return inteligente;
	}


	public void setInteligente(boolean inteligente) {
		this.inteligente = inteligente;
	}


	public boolean isBajoConsumo() {
		return bajoConsumo;
	}


	public void setBajoConsumo(boolean bajoConsumo) {
		this.bajoConsumo = bajoConsumo;
	}


	public double getConsumption() {
		return consumption;
	}


	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	
	public void addDevice() {
		DeviceInfoTable p =DeviceInfoTable.getInstance();
		DeviceInfoTable.addDeviceInfo(this);
	}

	public DeviceInfo(){
		super();
	}
	
	public DeviceInfo(String name, String desc, boolean inteligente, boolean bajoConsumo, double consumption,int minHsUse,int maxHsUse) {
		super();
		this.name = name;
		this.desc = desc;
		this.inteligente = inteligente;
		this.bajoConsumo = bajoConsumo;
		this.consumption = consumption;
		this.minHsUse = minHsUse;
		this.maxHsUse = maxHsUse;
		this.addDevice();
	}

}
