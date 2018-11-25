package ar.edu.dds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import ar.edu.dds.helpers.ClientMsg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientREST {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String serverUrl = "http://localhost:4567/";

		System.out.println("== START CLIENT ==");

		while (true) {


			System.out.print("Ingresar mensaje:\t");
			String selection = scanner.nextLine();

			if (selection.equals("salir")) {
				break;
			} else if (selection.equals("listar")) {
				HttpResponse<String> response;
				try {
					response = Unirest.get(serverUrl).asString();
					if (response.getStatus() == 200) {
						String output = readResponse(response);
						System.out.println(output);

					} else {
						System.out.println(response.getStatusText() + " -- " + response.getBody());
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				HttpResponse<String> response;
				try {
                    ObjectMapper mapper = new ObjectMapper();
					response = Unirest.post(serverUrl)
                            .header("Content-Type", "application/json")
                            .body(mapper.writeValueAsString( new ClientMsg(selection,1)))
                            .asString();
					if (response.getStatus() == 201) {
						String output = readResponse(response);
						System.out.println(output);

					} else {
						System.out.println(response.getStatusText() + " -- " + response.getBody());
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}

	}

	private static String readResponse(HttpResponse<String> response) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = response.getRawBody().read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
		// StandardCharsets.UTF_8.name() > JDK 7
		return result.toString("UTF-8");
	}

}
