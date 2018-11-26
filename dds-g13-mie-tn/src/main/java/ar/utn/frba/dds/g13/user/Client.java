package ar.utn.frba.dds.g13.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.sensor.Sensor;

@Entity
@Table(name = "Client")
public class Client extends User {
	
	@Id								
	@GeneratedValue
	@Column(name="client_id")
	private Long id;
	
	@Column(name="type_id")
	@Expose String idType;

	@Column(name="number_id")
	@Expose String idNumber;
	
	@Column(name="phone_number")
	@Expose String phoneNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name="category_id")
	@Expose Category category;
	
	@Column(name="score")
	@Expose int score;
	
	@OneToMany(mappedBy = "client" , cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose List<Residence> residences;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Residence> getResidences() {
		return residences;
	}
	
	public void setResidences(List<Residence> residences) {
		this.residences = residences;
	}
	
	public ArrayList<Sensor> getSensorCollection() {
		ArrayList<Sensor> ss = new ArrayList<Sensor>();
		System.out.println(ss.size());
		if(ss.size() != 0) {
			for(Actuator a : Actuator.GLOBAL_ACTUATORS) {
				if(a.getDevice().getResidence().getClient().getId() == this.getId()) {
					ss.addAll(a.getSensors());
				}
			}
		}
		return ss;
	}

	public Client() {
		super();
	}
	
	public Client(String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate,
			String idType, String idNumber, String phoneNumber,
			Category category, int score,
			List<Residence> residences) {
		super(username, password, fullname, residenceAddress, registrationDate);
		this.idType = idType;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.category = category;
		this.score = score;
		this.residences = residences;
	}
	
	public void registerDevice(Device device, Residence residence) {
		if(residences.contains(residence)) {
			residence.addDevice(device);
			if(device.isSmart()) {
				increaseScore(15);
			}
		} else {
			//Error
		}
	}
	
	public void adaptStandardDevice(Device device, Residence residence) {
		if(residences.contains(residence)) {
			residence.adaptStandardDevice(device);
			increaseScore(10);
		} else {
			//Error
		}
	}
	
	public void increaseScore(int ammount) {
		score += ammount;
	}
	
	public Boolean anyDeviceOn() {
		for(Residence residence : residences) {
			if(residence.anyDeviceOn()) {
				return true;
			}
		}
		return false;
	}
	
	public int numberDevicesOn() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesOn();
		}
		return counter;
	}
	
	public int numberDevicesOff() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesOff();
		}
		return counter;
	}
	
	public int numberDevicesTotal() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesTotal();
		}
		return counter;
	}

	@Override
	public boolean isAdmin() {
		return false;
	}
	
}
