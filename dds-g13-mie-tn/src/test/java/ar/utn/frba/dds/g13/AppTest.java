package ar.utn.frba.dds.g13;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import json.ResidentialClientLoader;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
	ArrayList<ResidentialClient> clients;
	
    public AppTest( String testName )
    {
        super( testName );
        
        File file = new File(""); //CARGA DEL ARCHIVO DESDE RESOURCES
        try {
    		clients = (ArrayList<ResidentialClient>) ResidentialClientLoader.load( file );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        assertEquals( 1, clients.size() );
    }
}