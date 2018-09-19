package ar.utn.frba.dds.g13.JsonTests;

import ar.utn.frba.dds.g13.json.ResidenceLoader;
import org.junit.Before;
import org.junit.Test;

import ar.utn.frba.dds.g13.user.*;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ClientTest {
	
	List<Residence> clients;

	@Before
    public void setup() {
        final Path path = Paths.get("src", "test", "resources", "jsons", "residential_client.json");
        try {
    		clients = ResidenceLoader.load( path.toFile() );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	@Test
    public void test_clients_count() {
        assertEquals( 1, clients.size() );
    }
}
