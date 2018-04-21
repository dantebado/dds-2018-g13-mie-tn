package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clases.Administrador;

public class TestLauncher {
	
	public static void main(String[] args) {
		
		ArrayList<Administrador> administradores = new ArrayList<Administrador>();
			
		String path = "D:\\Mis Documentos\\Documentos\\2018\\DDS\\workspace\\dds-2018-g13-mie-tn\\src\\test\\test_carga_data.json";
		File file = new File(path);
		
		try {
			System.out.println("LEYENDO ARCHIVO JSON");
			
			String content = new String(Files.readAllBytes(file.toPath()));
			
			JSONObject json = new JSONObject(content);
			JSONArray administradores_data = json.getJSONArray("data_administradores");
			
			for(int i=0 ; i<administradores_data.length() ; i++) {
				JSONObject administrador_data = (JSONObject) administradores_data.get(i);
				
				System.out.println("Persona Registrada " +
						administrador_data.getInt("nIdentificacion") +
						", " + administrador_data.getString("nombreApellido") +
						", " + administrador_data.getString("fechaAlta")
				);
				
				String fechaAltaStr = administrador_data.getString("fechaAlta");
				
				administradores.add(new Administrador(
						administrador_data.getInt("nIdentificacion"),
						administrador_data.getString("nombreApellido"),
						administrador_data.getString("domicilio"),
						new Date( Integer.parseInt(fechaAltaStr.split("-")[0]), Integer.parseInt(fechaAltaStr.split("-")[1]), Integer.parseInt(fechaAltaStr.split("-")[2]) ),
						administrador_data.getString("nombreUsuario"),
						administrador_data.getString("contrasena")
						));
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
