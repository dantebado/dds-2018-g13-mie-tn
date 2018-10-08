package ar.utn.frba.dds.g13.device;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardDevice extends Device {
	
	@Column(name="dailyUseEstimation")
	BigDecimal dailyUseEstimation;

	public StandardDevice(){
		super();
	}
	
	public StandardDevice(String name,
			BigDecimal hourlyConsumption,
			BigDecimal dailyUseEstimation) {
		super(name, hourlyConsumption);
		this.dailyUseEstimation = dailyUseEstimation;
	}
	
	
	public void setDailyUseEstimation(BigDecimal dailyUseEstimation) {
		this.dailyUseEstimation = dailyUseEstimation;
	}


	public BigDecimal getDailyUseEstimation() {
		return dailyUseEstimation;
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
