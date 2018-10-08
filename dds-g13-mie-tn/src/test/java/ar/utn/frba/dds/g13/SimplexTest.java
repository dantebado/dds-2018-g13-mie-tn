package ar.utn.frba.dds.g13;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Assert;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.user.Residence;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.*;
import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
public class SimplexTest extends TestCase {
	
	public void testSistemaCompatibleDeterminado() {
		
		DeviceInfo AireI = new DeviceInfo("AireAcondicionado3500", "De 3500 frigorias", true, false, 1.613, 90, 360);
		DeviceInfo PcI =new DeviceInfo("PC", "De escritorio", true, true, 0.4, 60, 360);
		DeviceInfo MicroI =new DeviceInfo("Microondas", "Convencional", false, true, 0.64, 3, 15);
		DeviceInfoTable.getInstance();
		DeviceInfoTable.addDeviceInfo(AireI);
		DeviceInfoTable.addDeviceInfo(PcI);
		DeviceInfoTable.addDeviceInfo(MicroI);
		SmartDevice Aire = new SmartDevice("AireAcondicionado3500", null, null, null);
		SmartDevice Pc = new SmartDevice("PC", null, null, null);
		StandardDevice Micro =new StandardDevice("Microondas", null, new BigDecimal(10));
		//Residence CasaPrueba = new Residence("callefalsa123", new ArrayList<Device>());
		Residence.addDevice(Aire);
		Residence.addDevice(Pc);
		Residence.addDevice(Micro);
		//Residence.makeSimplexMethod();
		//Assert.assertTrue(370, solucion.getPoint()[2], 0.01); // <--- X0*/
	}
}
