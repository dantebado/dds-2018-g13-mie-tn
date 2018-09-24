package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.json.BeanToJson;

public abstract class Device extends BeanToJson {
	
	@Expose String name;
	@Expose BigDecimal hourlyConsumption;
	DeviceInfoTable Table = DeviceInfoTable.getInstance();
	
	public DeviceInfoTable getTable() {
		return Table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getHourlyConsumption() {
		return hourlyConsumption;
	}

	public void setHourlyConsumption(BigDecimal hourlyConsumption) {
		this.hourlyConsumption = hourlyConsumption;
	}

	public Device(String name, BigDecimal hourlyConsumption) {
		this.name = name;
		this.hourlyConsumption = hourlyConsumption;
	}
	
	public abstract boolean isSmart();

}
