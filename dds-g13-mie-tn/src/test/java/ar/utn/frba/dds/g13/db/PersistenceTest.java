package ar.utn.frba.dds.g13.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StateHistory;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnEnergySavingRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnOffWhenCold;
import ar.utn.frba.dds.g13.device.sensor.DeviceStateSensor;
import ar.utn.frba.dds.g13.device.sensor.Sensor;
import ar.utn.frba.dds.g13.device.sensor.TemperatureSensor;
import ar.utn.frba.dds.g13.device.states.DeviceState;
import ar.utn.frba.dds.g13.json.ClientLoader;
import ar.utn.frba.dds.g13.transformer.Transformer;
import ar.utn.frba.dds.g13.user.Client;
import ar.utn.frba.dds.g13.user.Logueable;
import ar.utn.frba.dds.g13.user.Residence;
import ar.utn.frba.dds.g13.json.TransformerLoader;

public class PersistenceTest {
	
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
	
	@Test
	public void testChangeUserProperty() {
		Calendar calendarDate = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDate.set(Calendar.YEAR, 2017);
				calendarDate.set(Calendar.MONTH, 10);
				calendarDate.set(Calendar.DAY_OF_MONTH, 15);
		ArrayList<Residence> residences = new ArrayList<Residence>();
		
		ArrayList<Device> devices = new ArrayList<Device>();
		
		Category category = new Category("Residencial", 200, 500, new BigDecimal(500), new BigDecimal(1.5));
		
		Client client = new Client("jperez", "aovsdyb",
				"Juan Perez", "Balcarce 50", calendarDate,
				"DNI", "20469755", "43687952",
				category, 13,
				residences);

		/*SAVE TO DB*/
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			tx = session.beginTransaction();
			ID = (Long) session.save(client);
			tx.commit();			
			System.out.println("User saved w id " + ID);
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*LOAD*/
		session = getSessionFactory().openSession();
		tx = null;
		client = null;
		try {
			tx = session.beginTransaction();
			client = (Client) session.load(Client.class, ID);			
			tx.commit();
			System.out.println("Client loaded " + client.getFullname());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*CHANGE NAME*/
		client.setFullname("Juan Rodriguez");
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.update(client);			
			tx.commit();
			System.out.println("Client saved " + client.getFullname());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*RECOVER*/
		session = getSessionFactory().openSession();
		tx = null;
		client = null;
		category = null;
		try {
			tx = session.beginTransaction();
			client = (Client) session.load(Client.class, ID);			
			tx.commit();
			System.out.println("Client loaded " + client.getFullname());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*DELETE ALL*/
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(client);
			tx.commit();
			System.out.println("All deleted");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		assertEquals("Juan Rodriguez", client.getFullname());
		
	}
	
	@Test
	public void testDeviceRecoveryModificationAndPersistence() {
				
		Calendar calendarDateStart = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateStart.set(Calendar.YEAR, 2017);
				calendarDateStart.set(Calendar.MONTH, 10);
				calendarDateStart.set(Calendar.DAY_OF_MONTH, 15);
		
		Calendar calendarDateEnd = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateEnd.set(Calendar.YEAR, 2018);
				calendarDateEnd.set(Calendar.MONTH, 10);
				calendarDateEnd.set(Calendar.DAY_OF_MONTH, 15);
				
				
		List<StateHistory> stateHistoryList = new ArrayList<StateHistory>();
		List<TimeIntervalDevice> timeIntervalList = new ArrayList<TimeIntervalDevice>();
		
		SmartDevice smart_device = new SmartDevice("TV", 
				new BigDecimal(0.200000000000000011102230246251565404236316680908203125), timeIntervalList, null);
		
		TimeIntervalDevice time_one = new TimeIntervalDevice(calendarDateStart, calendarDateEnd, true, smart_device);
		StateHistory state_one = new StateHistory(calendarDateStart, calendarDateEnd, "isOn", smart_device);

		timeIntervalList.add(time_one);
		stateHistoryList.add(state_one);
		smart_device.setStateHistory(stateHistoryList); 
	

		/*SAVE TO DB*/
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			tx = session.beginTransaction();
			ID = (Long) session.save(smart_device);
			tx.commit();			
			System.out.println("Device saved w id " + ID);
			for (TimeIntervalDevice interval : smart_device.getConsumptionHistory()){
				System.out.println("\nInterval: " + interval.getConsuming());
			}
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*LOAD*/
		session = getSessionFactory().openSession();
		tx = null;
		smart_device = null;
		try {
			tx = session.beginTransaction();
			smart_device = (SmartDevice) session.load(SmartDevice.class, ID);			
			tx.commit();
			System.out.println("Device loaded " + smart_device.getName());
			
			// SHOW INTERVAL
			for (TimeIntervalDevice interval : smart_device.getConsumptionHistory()){
				System.out.println("\nInterval: " + interval.getConsuming());
			}
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		smart_device.setName("TV ROTA");
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.update(smart_device);			
			tx.commit();
			System.out.println("Device saved " + smart_device.getName());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*RECOVER*/
		session = getSessionFactory().openSession();
		tx = null;
		smart_device = null;
		try {
			tx = session.beginTransaction();
			smart_device = (SmartDevice) session.load(SmartDevice.class, ID);			
			tx.commit();
			System.out.println("Device loaded " + smart_device.getName());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*DELETE ALL*/
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(smart_device);
			tx.commit();
			System.out.println("All deleted");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		assertEquals("TV ROTA", smart_device.getName());
		
	}

	@Test
	public void transformerCounter() {
		Transformer transformer;
		List<Transformer> transformers = new ArrayList<Transformer>();
		
		final Path path = Paths.get("src", "test", "resources", "jsons", "transformers.json");
        try {
    		transformers = TransformerLoader.load( path.toFile() );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		List<Long> ids = new ArrayList<Long>();
		try {
			tx = session.beginTransaction();
			for (Transformer t : transformers) {
				ID = (Long) session.save(t);
				ids.add(ID);
			}
			tx.commit();			
			System.out.println("Transformers loaded");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		session = getSessionFactory().openSession();
		tx = null;
		transformers.clear();
		try {
			tx = session.beginTransaction();
			for(Long id : ids) {
				transformer = (Transformer) session.load(Transformer.class, ID);
				transformers.add(transformer);
			}		
			tx.commit();
			System.out.println("Transformer loaded COUNT: " + transformers.size());
		
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(transformers);
			tx.commit();
			System.out.println("All deleted");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}	
		assertEquals(3, transformers.size());
	}
	
	@Test
	public void transformerCounterPlusPlus() {
		Transformer transformer;
		List<Transformer> transformers = new ArrayList<Transformer>();
		
		final Path path = Paths.get("src", "test", "resources", "jsons", "transformersPlusPlus.json");
        try {
    		transformers = TransformerLoader.load( path.toFile() );
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		List<Long> ids = new ArrayList<Long>();
		try {
			tx = session.beginTransaction();
			for (Transformer t : transformers) {
				ID = (Long) session.save(t);
				ids.add(ID);
			}
			tx.commit();			
			System.out.println("Transformers loaded");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		session = getSessionFactory().openSession();
		tx = null;
		transformers.clear();
		try {
			tx = session.beginTransaction();
			for(Long id : ids) {
				transformer = (Transformer) session.load(Transformer.class, ID);
				transformers.add(transformer);
			}		
			tx.commit();
			System.out.println("Transformer loaded COUNT: " + transformers.size());
		
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(transformers);
			tx.commit();
			System.out.println("All deleted");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}	
		assertEquals(4, transformers.size());
		
	}
	
	@Test
	public void testRules() {		
		SmartDevice smart_device = new SmartDevice("TV", 
				new BigDecimal(0.200000000000000011102230246251565404236316680908203125), null, null);
				
		TurnEnergySavingRule energySavingRule = new TurnEnergySavingRule();
		List<AutomationRule> rules = new ArrayList<AutomationRule>();
		rules.add(energySavingRule);
		
		TemperatureSensor temperatureSensor = new TemperatureSensor(5);
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(temperatureSensor);
		
		Actuator actuatorEnergy = new Actuator(smart_device, rules, sensors);
		
		/*SAVE TO DB*/
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			tx = session.beginTransaction();
			ID = (Long) session.save(actuatorEnergy);
			session.save(smart_device);
			tx.commit();			
			System.out.println("Actuator saved w id " + ID);
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		
		
		/*LOAD*/
		session = getSessionFactory().openSession();
		tx = null;
		actuatorEnergy = null;
		try {
			tx = session.beginTransaction();
			actuatorEnergy = (Actuator) session.load(Actuator.class, ID);			
			tx.commit();
			System.out.println("Actuator loaded " + actuatorEnergy.getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		
		TurnOffWhenCold turnOffWhenColdRule = new TurnOffWhenCold();
		rules.clear();
		rules.add(turnOffWhenColdRule);
		actuatorEnergy.setRules(rules);
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.update(actuatorEnergy);			
			tx.commit();
			System.out.println("Actuator saved " + actuatorEnergy.getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		
		/*RECOVER*/
		session = getSessionFactory().openSession();
		tx = null;
		actuatorEnergy = null;
		try {
			tx = session.beginTransaction();
			actuatorEnergy = (Actuator) session.load(Actuator.class, ID);			
			tx.commit();
			System.out.println("Actuator loaded " + actuatorEnergy.getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		/*DELETE ALL*/
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(actuatorEnergy);
			tx.commit();
			System.out.println("All deleted");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		assertEquals(rules, actuatorEnergy.getRules());

		
	}
}
