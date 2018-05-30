package ar.utn.frba.dds.g13;

import json.ResidentialClientLoader;
import org.junit.Before;
import org.junit.Test;

import ar.utn.frba.dds.g13.devices.*;
import java.math.BigDecimal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

public class ClientsTest {
	
	Device tv = new Device(true, new BigDecimal("0.03"), "tv");
	Device stereo = new Device(false, new BigDecimal("0.01"), "stereo");
	Device smart = new Device(false, new BigDecimal("0.02"), "smart");
	List<Device> devicesLondon = new ArrayList<Device>(Arrays.asList(tv, stereo));
	List<Device> devicesCaba = new ArrayList<Device>(Arrays.asList(smart));
	
	Category categoryOne = new Category("categoryOne", new BigDecimal("45.00"),
			new BigDecimal("355.00"),  new BigDecimal("01.00"));
	Address london = new Address(5000, new Date(20,03,2018), "30CENTER",
			new BigDecimal("0.30"), categoryOne, devicesLondon);
	Address caba = new Address(5001, new Date(20,03,2018), "11LUGANO",
			new BigDecimal("0.1"), categoryOne, devicesCaba);
	List<Address> addresses = new ArrayList<Address>(Arrays.asList(london, caba));

	ResidentialClient client = new ResidentialClient("Robbie Williams", 1, 16000000, "41113333", "theangel",
			"dsaRfmIOEW92324$%#", new BigDecimal("0.30"), addresses);

    @Test
    public void test_avblDevicesList() {
    	List<Device> devicesTotal = new ArrayList<Device>(Arrays.asList(tv,smart,stereo));
        assertTrue(client.avblDevicesList().containsAll(devicesTotal));
    }
    
    @Test
    public void test_totalAvblDevicesList() {
        assertTrue(client.totalAvblDevices() == 3);
    }
    
    @Test
    public void test_totalDevicesOn() {
        assertEquals(client.totalDevicesOn(), new ArrayList<Device>(Arrays.asList(tv)));
    }
    
    @Test
    public void test_totalDevicesOff() {
        assertEquals(client.totalDevicesOff(), new ArrayList<Device>(Arrays.asList(stereo, smart)));
    }
    
    @Test
    public void test_estimateMonthCost() {
        assertEquals(client.estimateMonthCost(), new BigDecimal("0.40"));
    }
   
}