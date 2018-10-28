package ar.utn.frba.dds.g13.device.deviceinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;

public class DeviceInfoTable {
	private static ArrayList<DeviceInfo> DevicesInfos = new ArrayList<DeviceInfo>();
	int maxConsumption = 612000;
	 
	public int getMaxConsumption() {
		return maxConsumption;
	}

	public void setMaxConsumption(int maxConsumption) {
		this.maxConsumption = maxConsumption;
	}

	public static Collection<DeviceInfo> getDevicesInfos() {
		return DevicesInfos;
	}
	
	public static void addDeviceInfo(DeviceInfo device) {
		DevicesInfos.add(device);
	}
   	
  	public String getDesc(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.getDesc();
	}  
      
  	public boolean isInteligente(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.isInteligente();
	}
      
  	public boolean isBajoConsumo(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.isBajoConsumo();
	}
      
  	public double getConsumption(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.getConsumption();
	}
  	
  	public double getMinHsUse(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.getMinHsUse();
	}
  	
  	public double getMaxHsUse(String deviceName) {
  		DeviceInfo device = getDeviceByName(deviceName);
		return device.getMaxHsUse();
	}
  	
  	public static DeviceInfo getDeviceByName(final String deviceName) {
  		for (DeviceInfo deviceInfo : DevicesInfos) {
			if(deviceInfo.getName() == deviceName) {
				return deviceInfo;
			}
		}
  		return null;
  	}
  	
	private static DeviceInfoTable instance = null;
	
	private DeviceInfoTable() {
		//TODO Populate with devices (from persistance)
	}

	public static DeviceInfoTable getInstance() {
		if(instance == null) {
			instance = new DeviceInfoTable();
		}
		return instance;
	}
}