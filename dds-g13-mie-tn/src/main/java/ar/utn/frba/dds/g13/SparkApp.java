package ar.utn.frba.dds.g13;
import static spark.Spark.*;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import org.jtwig.*;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.user.Client;
import ar.utn.frba.dds.g13.user.Residence;
import ar.utn.frba.dds.g13.user.User;
import spark.Request;

public class SparkApp {
	
	public static JtwigTemplate getTemplate(String filename) {
		return JtwigTemplate.fileTemplate( Paths.get("src", "main", "resources", "templates", filename + ".twig").toFile() );
	}

	public static void main(String[] args) {
		staticFiles.location("/public/");
		
		get("/test_twig", (req, res) -> {
			
	        JtwigTemplate template = getTemplate("full_base.html");
	        JtwigModel model = JtwigModel.newModel();
	        
	        model.with("name", "Carlos");
	        
	        return template.render(model);
		});
		
		get("/test", (req, res) -> "Test successful");
		
		get("/", (request, res) -> {
	        JtwigTemplate template = getTemplate("map.html");
	        JtwigModel model = JtwigModel.newModel();
	        
	        User user = loadUser(request);
	        if(user == null) {
		        model.with("current_user", "-1");
	        } else {
	        	model.with("current_user", user);
	        }
	        
	        return template.render(model);
		});
		
		post("/login_pst", (request, response) -> {
	        User user = loadUser(request);
	        System.out.println("Login Attempt: " + request.queryParams("username"));
	        //Check user credentials
	        if(true) {
	        	Calendar calendarDate = Calendar.getInstance(
	  				  TimeZone.getTimeZone("UTC"));
				calendarDate.set(Calendar.YEAR, 2017);
				calendarDate.set(Calendar.MONTH, 10);
				calendarDate.set(Calendar.DAY_OF_MONTH, 15);
				ArrayList<Residence> residences = new ArrayList<Residence>();
				
				Client client = new Client("jperez", "aovsdyb",
						"Juan Perez", "Balcarce 50", calendarDate,
						"DNI", "20469755", "43687952",
						null, 13,
						residences);
				
				System.out.println("Setting user " + client.getUsername() + " in session " + request.session().id());
	        	request.session().attribute("current_user", client);
	        	response.redirect("/");
	        } else {
	        	response.redirect("/");
	        }
	        return "";
		});
		
		get("/logout", (request, response) -> {
			request.session().invalidate();
        	response.redirect("/");
        	return "";
		});
		
	}
	
	private static User loadUser(Request request) {
		request.session(true);
        if(request.session().isNew()) {
        	request.session().attribute("current_user", null);
        } else {
        	User user = request.session().attribute("current_user");
        	if(user == null) {
        		System.out.println(request.session().id() + " no está logueado");
        	} else {
        		System.out.println(request.session().id() + " logueado " + user.getFullname());
        	}
        	return user;
        }
        return null;
	}

}
