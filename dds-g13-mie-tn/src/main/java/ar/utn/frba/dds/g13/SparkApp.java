package ar.utn.frba.dds.g13;
import static spark.Spark.*;

import java.nio.file.Paths;

import org.jtwig.*;

public class SparkApp {
	
	public static JtwigTemplate getTemplate(String filename) {
		return JtwigTemplate.fileTemplate( Paths.get("src", "main", "resources", "templates", filename + ".twig").toFile() );
	}

	public static void main(String[] args) {
		
		get("/test_twig", (req, res) -> {
			
	        JtwigTemplate template = getTemplate("example");
	        JtwigModel model = JtwigModel.newModel();
	        
	        model.with("name", "Carlos");
	        
	        return template.render(model);
		});
		
		get("/test", (req, res) -> "Test successful");
		
	}

}
