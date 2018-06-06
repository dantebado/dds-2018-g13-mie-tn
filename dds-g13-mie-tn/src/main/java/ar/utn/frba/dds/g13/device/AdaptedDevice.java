package ar.utn.frba.dds.g13.device;

import java.util.List;

import ar.utn.frba.dds.g13.device.states.DeviceState;

public class AdaptedDevice extends SmartDevice {
	
	DeviceState state;
	List<TimeIntervalDevice> consumptionHistory;

	public AdaptedDevice(StandardDevice adaptee,
			List<TimeIntervalDevice> consumptionHistory,
			DeviceState state) {
		super(adaptee.name, adaptee.hourlyConsumption,
				consumptionHistory, state);
		this.consumptionHistory = consumptionHistory;
		this.state = state;
	}


}
