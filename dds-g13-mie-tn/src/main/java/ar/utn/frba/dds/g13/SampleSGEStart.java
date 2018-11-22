package ar.utn.frba.dds.g13;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import ar.utn.frba.dds.g13.area.Area;
import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.user.User;

public class SampleSGEStart {
	
	static SessionFactory factory = null; 
	public static SessionFactory getSessionFactory() {
		if(factory == null) {
	        Configuration configObj = new Configuration();
	        configObj.configure("hibernate.cfg.xml");

	        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

	        factory = configObj.buildSessionFactory(serviceRegistryObj);    
		}
		return factory;
    }
		
	public static JtwigTemplate getTemplate(String filename) {
		return JtwigTemplate.fileTemplate( Paths.get("src", "main", "resources", "templates", filename + ".twig").toFile() );
	}
	
	static ArrayList<Category> categories = null;
	static ArrayList<Area> areas = null;
	
	public static void main( String[] args ) {
		
		staticFiles.location("/public/");
		
		loadDevices();
		loadCategories();
		loadAreas();

		get("/", (request, res) -> {
	        JtwigTemplate template = getTemplate("map.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "map");	        
	        User user = null;
	        if(user == null) {
		        model.with("current_user", "-1");
	        }
	        model.with("areas", areas);
	        return template.render(model);
		});
	}
	
	private static void loadDevices() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			DeviceInfoTable.setDevices((ArrayList<DeviceInfo>) session.createCriteria(DeviceInfo.class).list());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
	}

	private static void loadCategories() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			categories = (ArrayList<Category>) session.createCriteria(Category.class).list();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
	}
	
	private static void loadAreas() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			areas = (ArrayList<Area>) session.createCriteria(Area.class).list();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
	}

}
