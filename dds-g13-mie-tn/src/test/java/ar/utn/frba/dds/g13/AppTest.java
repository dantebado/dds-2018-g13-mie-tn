package ar.utn.frba.dds.g13;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import json.ResidentialClientLoader;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
	ArrayList<ResidentialClient> clients;
	
    public AppTest( String testName ) {
        super( testName );
        
        File file = new File(""); //CARGA DEL ARCHIVO DESDE RESOURCES
        try {
    		clients = (ArrayList<ResidentialClient>) ResidentialClientLoader.load( file );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void test_clients_count() {
        assertEquals( 1, clients.size() );
    }

    public void test_devices_count() {
    	ResidentialClient client = clients.get(0);
    	List<Device> devices = client.getAddresses().get(0).getDevices();
        assertEquals( 2, devices.size() );
    }

    public void test_check_username() {
    	ResidentialClient client = clients.get(0);
        assertEquals( client.getUsername(), "elsaquetin" );
    }
}