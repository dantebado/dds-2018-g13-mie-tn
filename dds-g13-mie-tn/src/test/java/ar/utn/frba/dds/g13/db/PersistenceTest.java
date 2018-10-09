package ar.utn.frba.dds.g13.db;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.utn.frba.dds.g13.user.Logueable;

public class PersistenceTest {
	
	public static SessionFactory getSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        // Since Hibernate Version 4.x, Service Registry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 
        // Creating Hibernate Session Factory Instance
        SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);      
        return factoryObj;
    }
	
	public static Integer createRecord(Logueable user) {
        Session sessionObj = getSessionFactory().openSession();
        //Creating Transaction Object  
        Transaction transObj = sessionObj.beginTransaction();
        sessionObj.save(user);
        // Transaction Is Committed To Database
        transObj.commit();
        // Closing The Session Object
        sessionObj.close();
        logger.info("Successfully Created " + studentObj.toString());
        return studentObj.getStudentId();
    }


	
	
	@Test
	public void testOne() {
		// crear sesion
		// new user
		// sesion.save(user)
		// cerrar sesion
		// abrir sesion
		// find and display user
		// crear nuevo user modif
		// sesion.commit(user)
		// cerrar sesion
	}
	
	

}
