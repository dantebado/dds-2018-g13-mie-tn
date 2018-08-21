package ar.utn.frba.dds.g13.simplex;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import simplex.facade.SimplexFacade;

public class simplexAdapter {
	
	SimplexFacade simplex;

	public void enofoqueMAX(){
	simplex = new SimplexFacade(GoalType.MAXIMIZE, true);
	}
	
	public void crearFuncion(double array[]){
	simplex.crearFuncionEconomica(array);
	}

	public simplexAdapter simplexAdapter() {
		return this;
	}

	public void crearRestriccionLEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.LEQ, valorcomp, array);
	}
	
	public void crearRestriccionGEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.GEQ, valorcomp, array);
	}
	
	public void crearRestriccionEQ(double valorcomp,double array[]){
	simplex.agregarRestriccion(Relationship.EQ, valorcomp, array);
	}
	
	public PointValuePair resolver(){
		return simplex.resolver();
	}
}