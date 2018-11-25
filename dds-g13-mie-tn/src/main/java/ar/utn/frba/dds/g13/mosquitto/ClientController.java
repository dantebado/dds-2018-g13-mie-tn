package ar.utn.frba.dds.g13.mosquitto;

import javax.xml.bind.DatatypeConverter;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;

public class ClientController {
	public static String[] availableCommands = {"APAGAR", "ENCENDER", "MODO AHORRO ENERGIA", "MODO INTELIGENTE", "MODO MANUAL"};
	
	public static boolean listContains(String command) {
		String search = command;
	    for(String str: availableCommands) {
	        if(str.trim().contains(search))
	           return true;
	    }
	    return false;
	}
	
    public static void main(String[] args) throws MqttException, InterruptedException{
    	//String ClientId = args[0];
    	Scanner scanner = new Scanner(System.in);

    	System.out.println("== START CLIENT CONTROLLER ==");

    	while (true) {


            System.out.print("Ingrese nuevo comando:\t");
            String selection = scanner.nextLine();

            if (selection.equals("listado dispositivos")) {
            	//TODO  MEJORA Traer dispositivos del cliente y checkear que solo pueda llamar a sus ids;
            }
            if (selection.equals("salir")) {
                break;
            }
            else {
            	if (listContains(selection)) {
	            	System.out.print("Ingrese ID del dispositivo:\t");
	            	String selectionofID = scanner.nextLine();
	            	SGEPubMQTT.sendAction( selectionofID, selection, "MANUAL");
            	}
            	else {
            		System.out.println("Error: Comando no valido");
            	}
            }
        }

        System.out.println("== END CLIENT CONTROLLER ==");
    }
}