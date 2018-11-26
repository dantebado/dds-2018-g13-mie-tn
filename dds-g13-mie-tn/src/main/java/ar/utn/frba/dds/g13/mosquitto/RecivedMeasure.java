package ar.utn.frba.dds.g13.mosquitto;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecivedMeasure {
	public static  String id;
	public static String name;
	public static String messure;
	public static int value;
	
	public String getId() {
		return id;
	}
	public static void setId(String id) {
		RecivedMeasure.id = id;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		RecivedMeasure.name = name;
	}
	public String getMessure() {
		return messure;
	}
	public static void setMessure(String messure) {
		RecivedMeasure.messure = messure;
	}
	public int getValue() {
		return value;
	}
	public static void setValue(int value) {
		RecivedMeasure.value = value;
	}
	
	public RecivedMeasure(String id, String name, String messure, int receivedValue) {
		RecivedMeasure.id = id;
		RecivedMeasure.name = name;
		RecivedMeasure.messure = messure;
		RecivedMeasure.value = receivedValue;
	}
}