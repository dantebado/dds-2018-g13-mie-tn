package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardDevice extends Device {
	
	public StandardDevice(){
		super();
	}
	
	public StandardDevice(String name,
			DeviceInfo info) {
		super(name, info);
	}
	
	@Override
	public boolean isSmart() {
		return false;
	}


	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
