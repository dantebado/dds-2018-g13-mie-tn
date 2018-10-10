package ar.utn.frba.dds.g13.area;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.utn.frba.dds.g13.transformer.Transformer;
import ar.utn.frba.dds.g13.user.Residence;

@Entity
@Table(name = "Area")
public class Area {

	@Id								
	@GeneratedValue
	@Column(name="area_id")
	private Long id;
	
	@Column(name="area_name")
    String areaName;
	
	@Column(name="radius")
	Float radius;
	
	@Column(name="coordinate")
    Point coordinate;
	
	@OneToMany(mappedBy = "area" , cascade = {CascadeType.ALL})
    List<Transformer> transformers;

	@OneToMany(mappedBy = "area" , cascade = {CascadeType.ALL})
	List<Residence> residences;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}

	public List<Residence> getResidences() {
		return residences;
	}

	public void setResidences(List<Residence> residences) {
		this.residences = residences;
	}
	
	public void setTransformers(List<Transformer> transformers) {
		this.transformers = transformers;
	}

	public Area() {
		super();
	}
	
    public Area(String areaName, Float radius, List<Transformer> transformers, Point coordinate, List<Residence> residences) {
        this.areaName = areaName;
        this.radius = radius;
        this.transformers = transformers;
        this.coordinate = coordinate;
        this.residences = residences;
    }

    public BigDecimal energySupplied() {
        BigDecimal totalConsumption = new BigDecimal(0);
        for(Transformer transformer : transformers) {
            totalConsumption = totalConsumption.add(transformer.energySupplied());
        }
        return totalConsumption;
    }

}