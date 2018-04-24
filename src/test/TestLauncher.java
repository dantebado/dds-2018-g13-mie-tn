package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clases.Administrador;
import clases.Dispositivo;
import clases.Usuario;

public class TestLauncher {
	
	public static void main(String[] args) {
		
		List<Administrador> administradores = new ArrayList<Administrador>();
		List<Usuario> usuarios = new ArrayList<Usuario>();
			
		String path = "PATH_ABSOLUTO_AL_ARCHIVO_JSON";
		File file = new File(path);
		
		try {
			System.out.println("LEYENDO ARCHIVO JSON");
			
			String content = new String(Files.readAllBytes(file.toPath()));
			
			JSONObject json = new JSONObject(content);
			
			//Carga de Administradores
			System.out.println("\nCARGA DE ADMINISTRADORES");
			JSONArray administradores_data = json.getJSONArray("data_administradores");
			
			for(int i=0 ; i<administradores_data.length() ; i++) {
				JSONObject administrador_data = (JSONObject) administradores_data.get(i);				
				System.out.println("Administrador Registrado " +
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
			
			//Carga de Usuarios
			System.out.println("\nCARGA DE USUARIOS Y DISPOSITIVOS");
			JSONArray usuarios_data = json.getJSONArray("data_usuarios");
			
			for(int i=0 ; i<usuarios_data.length() ; i++) {
				JSONObject usuario_data = (JSONObject) usuarios_data.get(i);				
				System.out.println("Usuario Registrado " +
						usuario_data.getInt("numeroDoc") +
						", " + usuario_data.getString("nombreApellido") +
						", " + usuario_data.getString("fechaAlta") +
						", " + usuario_data.getJSONArray("dispositivos").length() + " dispositivos"
				);				
				String fechaAltaStr = usuario_data.getString("fechaAlta");				
				usuarios.add(new Usuario(
						usuario_data.getString("nombreApellido"),
						usuario_data.getString("domicilio"),
						new Date( Integer.parseInt(fechaAltaStr.split("-")[0]), Integer.parseInt(fechaAltaStr.split("-")[1]), Integer.parseInt(fechaAltaStr.split("-")[2]) ),
						usuario_data.getString("nombreUsuario"),
						usuario_data.getString("contrasena"),
						usuario_data.getInt("telefono"),
						usuario_data.getString("tipoDoc"),
						usuario_data.getInt("numeroDoc"),
						usuario_data.getString("categoria"),
						usuario_data.getInt("kWConsumidos")
						));
				Usuario usuario = usuarios.get(i);
				JSONArray dispositivos = usuario_data.getJSONArray("dispositivos");
				for(int j=0 ; j<dispositivos.length() ; j++) {
					JSONObject dispositivo_data = (JSONObject) dispositivos.get(j);
					usuario.agregarDispositivo(new Dispositivo(
								(dispositivo_data.getInt("encendido") == 1),
								(float) dispositivo_data.getDouble("consumokWh"),
								dispositivo_data.getString("nombre")
							));
					System.out.println("      " + dispositivo_data.getString("nombre"));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
