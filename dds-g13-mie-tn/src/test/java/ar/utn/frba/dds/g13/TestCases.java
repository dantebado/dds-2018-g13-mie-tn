	/*
	 package ar.utn.frba.dds.g13;


import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import jacklow.model.Robo;

public class TestCases {
	public static void main(String[] args) {
		System.out.println("hola mundo ORM");
		Robo robo = new Robo();
		robo.setCodigoDenuncia("ASD");
		robo.setDenunciante("Carlos");
		
		EntityManager entityManager =
				PerThreadEntityManagers.getEntityManager();
		
		/*
		EntityTransaction transaction =
				entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(robo);
		
		transaction.commit();
	
		
		EntityTransaction transaction =
				entityManager.getTransaction();
		transaction.begin();
		
		Robo roboDb = entityManager.
				find (Robo.class,new Long(1)); //find(Entity,algunID)
		
		roboDb.setDenunciante("Lucas");
		
		transaction.commit();
		System.out.println(robo.getDenunciante());
	}
}

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	

}
*/