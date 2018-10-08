package ar.utn.frba.dds.g13.device;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ar.utn.frba.dds.g13.device.states.DeviceState;

@Entity
@DiscriminatorValue("ADAPTED")
public class AdaptedDevice extends SmartDevice {
	
	@Transient
	DeviceState state;
	
	@OneToMany(mappedBy = "device")
	List<TimeIntervalDevice> consumptionHistory;

	
	public DeviceState getState() {
		return state;
	}

	public void setState(DeviceState state) {
		this.state = state;
	}

	public List<TimeIntervalDevice> getConsumptionHistory() {
		return consumptionHistory;
	}

	public void setConsumptionHistory(List<TimeIntervalDevice> consumptionHistory) {
		this.consumptionHistory = consumptionHistory;
	}

	public AdaptedDevice(){
		super();
	}
	
	public AdaptedDevice(StandardDevice adaptee,
			List<TimeIntervalDevice> consumptionHistory,
			DeviceState state) {
		super(adaptee.name, adaptee.hourlyConsumption,
				consumptionHistory, state);
		this.consumptionHistory = consumptionHistory;
		this.state = state;
	}


}
