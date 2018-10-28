package ar.utn.frba.dds.g13.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.awt.Point;
import java.math.BigDecimal;

import org.apache.commons.math3.optim.PointValuePair;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.area.Area;
import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.AdaptedDevice;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.states.DeviceOff;
import ar.utn.frba.dds.g13.json.BeanToJson;
import ar.utn.frba.dds.g13.simplex.simplexAdapter;
import ar.utn.frba.dds.g13.transformer.Transformer;

@Entity
@Table(name = "Residence")
public class Residence extends BeanToJson {

	@Id								
	@GeneratedValue
	@Column(name="residence_id")
	private Long id;
	
	@Column(name="address")
	@Expose String address;

	@OneToMany(mappedBy = "residence" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@Expose List<Device> devices;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name="client_id")
	@Expose Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	@Expose Area area;

	@Column(name="x")
	@Expose double coordX;
	@Column(name="y")
	@Expose double coordY;
	
	@Transient
	@Expose static simplexAdapter simplex = new simplexAdapter();
	
	@Transient
	@Expose Calendar cal = Calendar.getInstance();
	
	@Transient	
	@Expose int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public List<Device> getDevices() {
		return devices;
	}
	
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<SmartDevice> getSmartDevices() {
		ArrayList<SmartDevice> l = new ArrayList<SmartDevice>();
		for(Device d : this.getDevices()) {
			if(d.isSmart()) {
				l.add((SmartDevice) d);
			}
		}
		return l;
	}

	public Client getClient() {
		return client;
	}
	
	public Point getCoords() {
		Point p = new Point();
		p.setLocation(coordX, coordY);
		return p;
	}
	
	public Transformer getTransformer() {
		return area.assignTransformer(getCoords());
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Residence(){
		super();
	}
	
	public Residence(String address, List<Device> devices, Client client, Area area) {
		this.address = address;
		this.devices = devices;
		for(Device d : devices) {
			d.setResidence(this);
		}
		this.area = area;
		this.client = client;
	}

	public void addDevice(Device device) {
		devices.add(device);
		device.setResidence(this);
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
	
	public BigDecimal consumptionBetween(Calendar start, Calendar end) {
        BigDecimal acum = new BigDecimal(0);
        for(Device device : devices) {
            if(device.isSmart()) {
                acum = acum.add( ((SmartDevice)device).consumptionBetween(start, end) );
            }
            else {
            	System.out.println("NO ES SMART");
            }
        }
        return acum;
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
				if ( Math.ceil(((StandardDevice) devices.get(i)).getDevice_info().getDailyEstimate() *dayOfMonth *100) >= Math.ceil( resultado.getPoint()[i] *100)) {
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
