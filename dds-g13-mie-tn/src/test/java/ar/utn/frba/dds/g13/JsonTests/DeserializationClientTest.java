package ar.utn.frba.dds.g13.JsonTests;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.json.ClientLoader;
import org.junit.Before;
import org.junit.Test;

import ar.utn.frba.dds.g13.user.*;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DeserializationClientTest {
	
	List<Client> clients;
	List<Residence> residences;
	String maradonaAddress = "Segurola y Habana";	
	Calendar maradonaDate = new GregorianCalendar(2018, 1, 1);
	BigDecimal maradonaHourlyConsumption = new BigDecimal("0.200000000000000011102230246251565404236316680908203125");

	@Before
    public void setup() {
        final Path path = Paths.get("src", "test", "resources", "jsons", "residential_client.json");
        try {
    		clients = ClientLoader.load( path.toFile() );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	@Test
    public void test_clients_count() {
        assertEquals( 1, clients.size() );
    }
	
	@Test
    public void test_residence_address() {
        assertEquals( maradonaAddress, clients.get(0).getResidences().get(0).getAddress() );
    }
	
	@Test
    public void test_registration_date() {
        assertEquals( maradonaDate, clients.get(0).getRegistrationDate() );
    }
	
	@Test
    public void test_hourly_consumption() {
		assertEquals( maradonaHourlyConsumption, clients.get(0).getResidences().get(0).getDevices().get(0).getHourlyConsumption());
    }
}


