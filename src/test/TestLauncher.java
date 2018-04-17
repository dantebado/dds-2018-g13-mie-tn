package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestLauncher {
	
	public static void main(String[] args) {
			
		String path = "PATH_ABSOLUTO_A_ARCHIVO_JSON_DE_PERSONAS";
		File file = new File(path);
		
		try {
			System.out.println("LEYENDO ARCHIVO JSON");
			
			String content = new String(Files.readAllBytes(file.toPath()));
			
			JSONObject json = new JSONObject(content);
			JSONArray array = json.getJSONArray("data");
			
			for(int i=0 ; i<array.length() ; i++) {
				JSONObject person = (JSONObject) array.get(i);
				System.out.println("Persona Registrada: " + person.getString("lastname") + ", " + person.getString("firstname"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
