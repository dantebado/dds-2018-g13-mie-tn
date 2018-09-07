package ar.utn.frba.dds.g13.simplex;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.user.Residence;
import simplex.facade.SimplexFacade;

public class simplexAdapter {
	
	SimplexFacade simplex;
	Calendar cal = Calendar.getInstance();
	int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	Date today = Calendar.getInstance().getTime();
	Date firstDayOfMonth = subtractDays(today,dayOfMonth);

	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
				
		return cal.getTime();
	}
	
	public simplexAdapter simplexAdapter() {
		return this;
	}
	
	public PointValuePair makeSimplexMethod(Residence residence){
	this.enofoqueMAX();
	double [] deviceTotal = new double[residence.numberDevicesTotal()];
	for (int i = 0; i < residence.numberDevicesTotal(); ++i) {
		deviceTotal[i] = 1;
	}
	this.crearFuncion(deviceTotal);
	List<Device> devices = residence.getDevices();
	double maxConsumption = (double) devices.get(0).getTable().getMaxConsumption();
	this.crearRestriccionLEQ(maxConsumption,deviceTotal);
	double [] deviceMatrix = new double[residence.numberDevicesTotal()];
	for (int i = 0; i < devices.size(); ++i) {
		deviceMatrix = deviceMatrix.clone();
		for (int j = 0; j < devices.size(); ++j) {
			if (i==j) {deviceMatrix[j] = 1;}
			else {deviceMatrix[j] = 0;}
		}
		String deviceName = devices.get(i).getName();
		double minUse = (double)devices.get(i).getTable().getMinHsUse(deviceName);
		double maxUse = (double)devices.get(i).getTable().getMaxHsUse(deviceName);
		this.crearRestriccionGEQ(minUse,deviceTotal);
		this.crearRestriccionLEQ(maxUse,deviceTotal);
	}
	PointValuePair resultado = this.resolver();
	return resultado;
	}
	
	public void enofoqueMAX(){
	simplex = new SimplexFacade(GoalType.MAXIMIZE, true);
	}
	
	public void crearFuncion(double array[]){
	simplex.crearFuncionEconomica(array);
	}

	public void crearRestriccionLEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.LEQ, valorcomp, array);
	}
	
	public void crearRestriccionGEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.GEQ, valorcomp, array);
	}
	
	public void crearRestriccionEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.EQ, valorcomp, array);
	}
	
	public PointValuePair resolver(){
		return simplex.resolver();
	}
}