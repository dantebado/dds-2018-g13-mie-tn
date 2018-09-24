package ar.utn.frba.dds.g13.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.math.BigDecimal;

import org.apache.commons.math3.optim.PointValuePair;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.device.AdaptedDevice;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.device.states.DeviceOff;
import ar.utn.frba.dds.g13.json.BeanToJson;
import ar.utn.frba.dds.g13.simplex.simplexAdapter;

public class Residence extends BeanToJson {

	
	@Expose String address;
	@Expose static List<Device> devices;
	@Expose static simplexAdapter simplex = new simplexAdapter();
	@Expose Calendar cal = Calendar.getInstance();
	@Expose int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	
	public Residence(String address, List<Device> devices) {
		this.address = address;
		this.devices = devices;
	}
	
	public String getAddress() {
		return address;
	}
	
	public static List<Device> getDevices() {
		return devices;
	}

	public static void addDevice(Device device) {
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
		PointValuePair resultado = simplex.makeSimplexMethod(this);
		for (int i = 0; i < devices.size(); ++i) {
			if(devices.get(i).isSmart()) {
				
				///DESCOMENTAR LINEA SIGUIENTE PARA FUNCIONAMIENTO NORMAL
				//if ( Math.ceil( ((SmartDevice) devices.get(i)).consumptionBetween(firstDayOfMonth, today).doubleValue() *100) >= Math.ceil( resultado.getPoint()[i] *100)) {
				
				///DESCOMENTAR LINEA SIGUIENTE PARA TEST
				if ( 148 >= Math.ceil( resultado.getPoint()[i] *100)) {
				
					System.out.printf("El dispostivo: %s ya consumio sus horas horas planificadas, se recomienda apagarlo\n" , devices.get(i).getName());
					if(((SmartDevice) devices.get(i)).isEnergySaving()) {
						((SmartDevice) devices.get(i)).turnOff();
						System.out.printf("Modo ahorro de energia encendido, se envio orden automatica de apagado al dispositivo : %s\n" , devices.get(i).getName());
					}
				}
				else{
					System.out.printf("El dispostivo: %s se encuentra dentro de sus horas de consumo planificadas\n" , devices.get(i).getName());
				}
			}
			else {
				if ( Math.ceil(((StandardDevice) devices.get(i)).getDailyUseEstimation().doubleValue() *dayOfMonth *100) >= Math.ceil( resultado.getPoint()[i] *100)) {
					System.out.printf("El dispostivo: %s ya consumio sus horas horas planificadas, se recomienda apagarlo" , devices.get(i).getName());
				}
				else{
					System.out.printf("El dispostivo: %s se encuentra dentro de sus horas de consumo planificadas" , devices.get(i).getName());
				}	
			}
		}
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}
}
