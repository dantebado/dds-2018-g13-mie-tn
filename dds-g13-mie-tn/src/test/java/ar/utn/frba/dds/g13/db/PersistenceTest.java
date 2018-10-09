package ar.utn.frba.dds.g13.db;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
import ar.utn.frba.dds.g13.user.Client;
import ar.utn.frba.dds.g13.user.Logueable;
import ar.utn.frba.dds.g13.user.Residence;

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

}
