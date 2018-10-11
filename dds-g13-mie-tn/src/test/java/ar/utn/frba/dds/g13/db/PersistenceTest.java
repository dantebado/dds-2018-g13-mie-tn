package ar.utn.frba.dds.g13.db;

import static org.junit.Assert.*;

import java.awt.Point;
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

import ar.utn.frba.dds.g13.area.Area;
import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StateHistory;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.AutomationTurnOff;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnEnergySavingRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnOffWhenCold;
import ar.utn.frba.dds.g13.device.sensor.DeviceStateSensor;
import ar.utn.frba.dds.g13.device.sensor.Sensor;
import ar.utn.frba.dds.g13.device.sensor.TemperatureSensor;
import ar.utn.frba.dds.g13.device.states.DeviceOn;
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
		
		/*DELETE ALL
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
		}*/
		
		assertEquals("Juan Rodriguez", client.getFullname());
		
	}
	
	@Test
	public void testDeviceRecoveryModificationAndPersistence() {
				
		Calendar calendarDateStartOne = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateStartOne.set(Calendar.YEAR, 2017);
				calendarDateStartOne.set(Calendar.MONTH, 10);
				calendarDateStartOne.set(Calendar.DAY_OF_MONTH, 15);
		
		Calendar calendarDateEndOne = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateEndOne.set(Calendar.YEAR, 2018);
				calendarDateEndOne.set(Calendar.MONTH, 10);
				calendarDateEndOne.set(Calendar.DAY_OF_MONTH, 15);
				
		Calendar calendarDateStartTwo = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateStartTwo.set(Calendar.YEAR, 2012);
				calendarDateStartTwo.set(Calendar.MONTH, 10);
				calendarDateStartTwo.set(Calendar.DAY_OF_MONTH, 15);
		
		Calendar calendarDateEndTwo = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDateEndTwo.set(Calendar.YEAR, 2013);
				calendarDateEndTwo.set(Calendar.MONTH, 10);
				calendarDateEndTwo.set(Calendar.DAY_OF_MONTH, 15);
				
				
		List<StateHistory> stateHistoryList = new ArrayList<StateHistory>();
		List<TimeIntervalDevice> timeIntervalList = new ArrayList<TimeIntervalDevice>();
		
		SmartDevice smart_device = new SmartDevice("TV", 
				new BigDecimal(0.200000000000000011102230246251565404236316680908203125), timeIntervalList, new DeviceOn());
		
		TimeIntervalDevice time_one = new TimeIntervalDevice(calendarDateStartOne, calendarDateEndOne, true, smart_device);
		TimeIntervalDevice time_two = new TimeIntervalDevice(calendarDateStartTwo, calendarDateEndTwo, false, smart_device);


		timeIntervalList.add(time_one);
		timeIntervalList.add(time_two);
	

		/*SAVE TO DB*/
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			tx = session.beginTransaction();
			ID = (Long) session.save(smart_device);
			tx.commit();			
			System.out.println("Device saved w id " + ID);
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
				if (interval.getConsuming()) {
					System.out.println("Interval: " + interval.getConsuming() + " " + 
							interval.getStart().getTimeInMillis() + " " + interval.getEnd().getTimeInMillis());
				}
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
		
		/*DELETE ALL
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
		}*/
		
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
		List<Long> ids = new ArrayList<Long>();
		try {
			tx = session.beginTransaction();
			for (Transformer t : transformers) {
				Long ID = (Long) session.save(t);
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
				transformer = (Transformer) session.load(Transformer.class, id);
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
			for (Transformer t : transformers) {
				session.delete(t);
			}
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
		List<Long> ids = new ArrayList<Long>();
		try {
			tx = session.beginTransaction();
			for (Transformer t : transformers) {
				Long ID = (Long) session.save(t);
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
				transformer = (Transformer) session.load(Transformer.class, id);
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
			for (Transformer t : transformers) {
				session.delete(t);
			}
			
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
		
		TemperatureSensor temperatureSensor = new TemperatureSensor(5, smart_device);
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(temperatureSensor);
		
		Actuator actuatorEnergy = new Actuator(smart_device, rules, sensors);
		
		energySavingRule.setActuator(actuatorEnergy);
		
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
		
		session = getSessionFactory().openSession();
		tx = null;
		actuatorEnergy = null;
		Actuator loaderActuator = null;
		try {
			tx = session.beginTransaction();
			loaderActuator = (Actuator) session.load(Actuator.class, ID);
			tx.commit();
			System.out.println(loaderActuator.getId());
			System.out.println("Actuator loaded " + loaderActuator.getRules().get(0).getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		
		TurnOffWhenCold turnOffWhenColdRule = new TurnOffWhenCold();
		turnOffWhenColdRule.setActuator(loaderActuator);
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			for( AutomationRule r : loaderActuator.getRules()) {
				session.delete(r);
			}
			loaderActuator.getRules().clear();
			loaderActuator.getRules().add(turnOffWhenColdRule);
			session.update(loaderActuator);			
			tx.commit();
			System.out.println("Actuator saved " + loaderActuator.getRules().get(0).getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		session = getSessionFactory().openSession();
		tx = null;
		actuatorEnergy = null;
		try {
			tx = session.beginTransaction();
			actuatorEnergy = (Actuator) session.load(Actuator.class, ID);			
			tx.commit();
			System.out.println("Actuator loaded " + actuatorEnergy.getRules().get(0).getClass());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		/*
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
		*/
		assertEquals(TurnOffWhenCold.class, actuatorEnergy.getRules().get(0).getClass());

		
	}
	
	@Test
	public void finalTest() {
		Calendar calendarDate = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				calendarDate.set(Calendar.YEAR, 2017);
				calendarDate.set(Calendar.MONTH, 10);
				calendarDate.set(Calendar.DAY_OF_MONTH, 15);
				
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
				
		Calendar startConsumption = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				startConsumption.set(Calendar.YEAR, 2017);
				startConsumption.set(Calendar.MONTH, 10);
				startConsumption.set(Calendar.DAY_OF_MONTH, 15);
		
		Calendar endConsumption = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
				endConsumption.set(Calendar.YEAR, 2018);
				endConsumption.set(Calendar.MONTH, 10);
				endConsumption.set(Calendar.DAY_OF_MONTH, 15);

		ArrayList<Residence> residences = new ArrayList<Residence>();
		ArrayList<Residence> residencesTwo = new ArrayList<Residence>();
		
		ArrayList<Residence> areaResidences = new ArrayList<Residence>();
		
		ArrayList<Device> devices = new ArrayList<Device>();
		ArrayList<Device> devicesTwo = new ArrayList<Device>();
		
		Category category = new Category("Residencial", 200, 500, new BigDecimal(500), new BigDecimal(1.5));
		
		Client client = new Client("jperez", "aovsdyb",
				"Juan Perez", "Balcarce 50", calendarDate,
				"DNI", "20469755", "43687952",
				category, 13,
				residences);
		
		Client clientTwo = new Client("jcarlos", "aovsdyb",
				"Juan Carlos", "Balcarce 50", calendarDate,
				"DNI", "20469755", "43687952",
				category, 13,
				residencesTwo);
		
		List<Transformer> transformers = new ArrayList<Transformer>();
		
		Area area = new Area("Devoto", (float) 50, transformers, new Point(30, 70), areaResidences);
				
		Residence residence = new Residence("Segurola y Habana", devices, client, area);
		Residence residenceTwo = new Residence("Segurola y Beiro", devicesTwo, clientTwo, area);
		
		List<StateHistory> stateHistoryList = new ArrayList<StateHistory>();
		List<StateHistory> stateHistoryListTwo = new ArrayList<StateHistory>();
		List<TimeIntervalDevice> timeIntervalList = new ArrayList<TimeIntervalDevice>();
		List<TimeIntervalDevice> timeIntervalListTwo = new ArrayList<TimeIntervalDevice>();
		
		SmartDevice smart_device = new SmartDevice("TV", 
				new BigDecimal(0.200000000000000011102230246251565404236316680908203125), timeIntervalList, new DeviceOn());
		SmartDevice smart_device_two = new SmartDevice("PC", 
				new BigDecimal(0.1000000000000000111022432251565404236316680908203125), timeIntervalListTwo, new DeviceOn());
		
		TimeIntervalDevice time_one = new TimeIntervalDevice(calendarDateStart, calendarDateEnd, true, smart_device);
		StateHistory state_one = new StateHistory(calendarDateStart, calendarDateEnd, "isOn", smart_device);
		
		TimeIntervalDevice time_two = new TimeIntervalDevice(startConsumption, endConsumption, true, smart_device);
		StateHistory state_two = new StateHistory(startConsumption, endConsumption, "isOn", smart_device);

		timeIntervalList.add(time_one);
		stateHistoryList.add(state_one);
		timeIntervalListTwo.add(time_two);
		stateHistoryListTwo.add(state_two);
		smart_device.setStateHistory(stateHistoryList); 
		smart_device_two.setStateHistory(stateHistoryListTwo); 
		
		residence.addDevice(smart_device);
		residenceTwo.addDevice(smart_device);
		residenceTwo.addDevice(smart_device_two);
		
		residences.add(residence);
		residencesTwo.add(residenceTwo);
		
		areaResidences.add(residence);
		areaResidences.add(residenceTwo);
		area.setResidences(areaResidences);
		
		client.setResidences(residences);
		clientTwo.setResidences(residencesTwo);
		
		Transformer transformer = new Transformer(new Point(30, 70), area, areaResidences);
		
		transformers.add(transformer);
		area.setTransformers(transformers);
		
		System.out.println("Total residence consumption:" + residence.consumptionBetween(calendarDateStart, calendarDateEnd));
		System.out.println("Transformer consumption average:" + transformer.energySuppliedAverageBetween(startConsumption, endConsumption));
		
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			tx = session.beginTransaction();
			ID = (Long) session.save(area);
			session.save(client);
			session.save(smart_device);
			tx.commit();			
			System.out.println("Actuator saved w id " + ID);
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		Area larea = null;
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			larea = (Area) session.load(Area.class, ID);
			tx.commit();			
			System.out.println("AREA " + larea.getAreaName());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		
		Transformer tranf = larea.getTransformers().get(0);
		List<Device> rdevices = tranf.getResidences().get(0).getDevices();
		Device d = rdevices.get(0);
		/*
		d.setHourlyConsumption(d.getHourlyConsumption()*1000);
		
		session = getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			larea = (Area) session.load(Area.class, ID);
			tx.commit();			
			System.out.println("AREA " + larea.getAreaName());
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
		*/
		
	}
}
