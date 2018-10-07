package ar.utn.frba.dds.g13.category;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.json.BeanToJson;

@Entity
@Table(name = "Category")
public class Category extends BeanToJson {
	
	@Id								
	@GeneratedValue
	@Column(name="category_id")
	private Long id;
	
	@Column(name="name")
	@Expose String name;
	
	@Column(name="minConsumption")
	@Expose float minConsumption;
	
	@Column(name="maxConsumption")
	@Expose float maxConsumption;
	
	@Column(name="fixedCharge")
	@Expose BigDecimal fixedCharge;
	
	@Column(name="variableCharge")
	@Expose BigDecimal variableCharge;
	
	@OneToMany(mappedBy = "category")
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMinConsumption() {
		return minConsumption;
	}

	public void setMinConsumption(float minConsumption) {
		this.minConsumption = minConsumption;
	}

	public float getMaxConsumption() {
		return maxConsumption;
	}

	public void setMaxConsumption(float maxConsumption) {
		this.maxConsumption = maxConsumption;
	}

	public BigDecimal getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(BigDecimal fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public BigDecimal getVariableCharge() {
		return variableCharge;
	}

	public void setVariableCharge(BigDecimal variableCharge) {
		this.variableCharge = variableCharge;
	}

	public Category() {
		super();
	}
	
	public Category(String name,
			float minConsumption, float maxConsumption,
			BigDecimal fixedCharge, BigDecimal variableCharge) {
		this.name = name;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
		this.fixedCharge = fixedCharge;
		this.variableCharge = variableCharge;
	}
	
	public Boolean belongsToCategory(BigDecimal monthlyConsumption) {
		float consumption = (float) monthlyConsumption.doubleValue();
		return minConsumption < consumption && consumption <= maxConsumption; 
	}
	
	public BigDecimal estimateBill(BigDecimal monthlyConsumption) {
		return fixedCharge.add(variableCharge.multiply(monthlyConsumption)); 
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
