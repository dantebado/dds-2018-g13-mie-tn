/*package ar.utn.frba.dds.g13;

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
import java.util.Collection;
public class SimplexTest extends TestCase {
	
	@Test
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
		StandardDevice Micro =new StandardDevice("Microondas", null, null);//(BigDecimal)("1115.37");
		Residence CasaPrueba = new Residence("callefalsa123", null);
		Residence.addDevice(Aire);
		Residence.addDevice(Pc);
		Residence.addDevice(Micro);
		Residence.makeSimplexMethod();
		Assert.assertEquals(760, solucion.getValue(), 0.01);
		Assert.assertEquals(360, solucion.getPoint()[0], 0.01); // <--- X2
		Assert.assertEquals(30, solucion.getPoint()[1], 0.01); // <--- X1
		Assert.assertEquals(370, solucion.getPoint()[2], 0.01); // <--- X0
	}
}*/
