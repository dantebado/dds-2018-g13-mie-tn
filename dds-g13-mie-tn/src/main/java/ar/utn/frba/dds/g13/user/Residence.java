package ar.utn.frba.dds.g13.user;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.apache.commons.math3.optim.PointValuePair;

import ar.utn.frba.dds.g13.device.AdaptedDevice;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.device.states.DeviceOff;
import ar.utn.frba.dds.g13.simplex.simplexAdapter;

public class Residence {
	
	String address;
	List<Device> devices;
	simplexAdapter simplex = new simplexAdapter();
	
	public Residence(String address, List<Device> devices) {
		this.address = address;
		this.devices = devices;
	}
	
	public void addDevice(Device device) {
		devices.add(device);
	}
	
	public void adaptStandardDevice(Device device) {
		if(devices.contains(device) && !device.isSmart()) {
			devices.remove(device);
			addDevice(new AdaptedDevice((StandardDevice) device,
					new ArrayList<TimeIntervalDevice>(), new DeviceOff()));
		}
	}
	
	public Boolean anyDeviceOn() {
		for(Device device : devices) {
			if(device.isSmart()) {
				SmartDevice sd = (SmartDevice) device;
				if(sd.isOn()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public BigDecimal actualConsumption() {
		BigDecimal consumption = new BigDecimal(0);
		for(Device device : devices) {
			if(device.isSmart()) {
				SmartDevice sd = (SmartDevice) device;
				if(sd.isOn()) {
					consumption = consumption.add(sd.getHourlyConsumption());
				}
			}
		}
		return consumption;
	}
	
	public int numberDevicesOn() {
		int counter = 0;
		for(Device device : devices) {
			if(device.isSmart()) {
				SmartDevice sd = (SmartDevice) device;
				if(sd.isOn()) {
					counter++;
				}			
			}
		}
		return counter;
	}
	
	public int numberSmartDevicesTotal() {
		int counter = 0;
		for(Device device : devices) {
			if(device.isSmart()) {
				counter++;		
			}
		}
		return counter;
	}
	
	public int numberDevicesOff() {
		return numberSmartDevicesTotal() - this.numberDevicesOn();
	}
	
	public int numberDevicesTotal() {
		return devices.size();
	}

	public void makeSimplexMethod() {
		DeviceInfoTable Table =DeviceInfoTable.getInstance();
		simplex.enofoqueMAX();
		double [] deviceTotal = new double[devices.size()];
		for (int i = 0; i < devices.size(); ++i) {
			deviceTotal[i] = 1;
		}
		simplex.crearFuncion(deviceTotal);
		double maxConsumption = (double)Table.getMaxConsumption();
		for (int i = 0; i < devices.size(); ++i) {
			String deviceName = devices.get(i).getName();
			double coeficiente = (double)Table.getConsumption(deviceName);
			deviceTotal[i] = coeficiente;
		}
		simplex.crearRestriccionLEQ(maxConsumption,deviceTotal);
		for (int i = 0; i < devices.size(); ++i) {
			for (int j = 0; j < devices.size(); ++j) {
				if (i==j) {deviceTotal[j] = 1;}
				else {deviceTotal[j] = 0;}
			}
			String deviceName = devices.get(i).getName();
			double minUse = (double)Table.getMinHsUse(deviceName);
			double maxUse = (double)Table.getMaxHsUse(deviceName);
			simplex.crearRestriccionGEQ(minUse,deviceTotal);
			simplex.crearRestriccionLEQ(maxUse,deviceTotal);
		}
		PointValuePair resultado = simplex.resolver();
		for (int i = 0; i < devices.size(); ++i) {
			if ( Math.ceil(devices.get(i).getHourlyConsumption()*100) >= Math.ceil(resultado.getPoint()[i]*100)) {
				    static {
         				System.out.println("El dispostivo:"devices.get(i).getName()"ya consumio sus horas horas planificadas, se recomienda apagarlo");
         				System.exit(0);
    				} 
				if(devices.get(i).isEnergySaving() && devices.get(i).isSmart) {
					devices.get(i).turnOff();
					static {
         				System.out.println("Modo ahorro de energia encendido, se envio orden automatica de apagado al dispositivo :"devices.get(i).getName());
         				System.exit(0);
    				} 
				}
			}
			else{
					static {
         				System.out.println("El dispostivo:"devices.get(i).getName()"se encuentra dentro de sus horas de consumo planificadas");
         				System.exit(0);
    				} 
			}
		}
	}
}
