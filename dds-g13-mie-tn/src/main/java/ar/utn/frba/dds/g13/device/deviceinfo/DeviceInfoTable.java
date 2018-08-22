package ar.utn.frba.dds.g13.device.deviceinfo;

import java.util.Collection;
import java.util.function.Predicate;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;

public class DeviceInfoTable {
	private static Collection<DeviceInfo> DevicesInfos = null;
	int maxConsumption = 612000;
	 
	public int getMaxConsumption() {
		return maxConsumption;
	}

	public void setMaxConsumption(int maxConsumption) {
		this.maxConsumption = maxConsumption;
	}

	public Collection<DeviceInfo> getDevicesInfos() {
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
  	
  	public DeviceInfo getDeviceByName(final String deviceName) {
  		return (DeviceInfo) DevicesInfos.stream().filter(new Predicate<DeviceInfo>() {
			public boolean test(DeviceInfo p) {
				return p.getName() == deviceName;
			}
		});
  	}
  	
	private static DeviceInfoTable instance = null;
		private DeviceInfoTable() {
		}

	public static DeviceInfoTable getInstance() {
  if(instance == null) {
     instance = new DeviceInfoTable();
  }
  return instance;
	}
}