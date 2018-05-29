package ar.utn.frba.dds.g13;

import json.ResidentialClientLoader;
import org.junit.Before;
import org.junit.Test;

import ar.utn.frba.dds.g13.devices.Device;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ParserTest {


	List<ResidentialClient> clients;

	@Before
    public void setup() {
        final Path path = Paths.get("src", "main", "resources", "jsons", "residential_client.json");
        try {
    		clients = ResidentialClientLoader.load( path.toFile() );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @Test
    public void test_clients_count() {
        assertEquals( 1, clients.size() );
    }
    @Test
    public void test_devices_count() {
    	ResidentialClient client = clients.get(0);
    	List<Device> devices = client.getAddresses().get(0).getDevices();
        assertEquals( 2, devices.size() );
    }
    @Test
    public void test_check_username() {
    	ResidentialClient client = clients.get(0);
        assertEquals( client.getUsername(), "elsaquetin" );
    }
}