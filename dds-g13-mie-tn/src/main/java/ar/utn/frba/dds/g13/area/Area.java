package ar.utn.frba.dds.g13.area;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

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

	@Column(name="x")
	@Expose double coordX;
	@Column(name="y")
	@Expose double coordY;

	@Transient
    Point coordinate;
	
	@OneToMany(mappedBy = "area" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    List<Transformer> transformers;

	@OneToMany(mappedBy = "area" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
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
		if(coordinate == null) {
			this.coordinate = new Point();
			coordinate.setLocation(coordX, coordY);
		}
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordX = coordinate.getX();
		this.coordY = coordinate.getY();
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
	
	public List<Transformer> getTransformers() {
		return transformers;
	}

	public Area() {
		super();
	}
	
    public Area(String areaName, Float radius, List<Transformer> transformers, Point coordinate, List<Residence> residences) {
        this.areaName = areaName;
        this.radius = radius;
        this.transformers = transformers;
        this.setCoordinate(coordinate);
        this.residences = residences;
    }

    public BigDecimal energySupplied() {
        BigDecimal totalConsumption = new BigDecimal(0);
        for(Transformer transformer : transformers) {
            totalConsumption = totalConsumption.add(transformer.energySupplied());
        }
        return totalConsumption;
    }
    
    public Transformer assignTransformer(Point point) {
    	Transformer t = transformers.get(0);
    	System.out.println("TRANSFORMERS " + transformers.size());
    	System.out.println("ASDASD " + t.getId());
    	System.out.println("X " + this.coordX);
    	System.out.println("POS " + t.getCoordinate().getX());
    	double dist = t.getCoordinate().distance(point);
    	for(Transformer tt : transformers) {
    		double tdistv = tt.getCoordinate().distance(point);
    		if(tdistv < dist) {
    			dist = tdistv;
    			t = tt;
    		}
    	}
    	return t;
    }

	public List<Residence> getResidencesByTransformer(Transformer transformer) {
		ArrayList<Residence> residences = new ArrayList<Residence>();
		for(Residence r : this.residences) {
			if(r.getTransformer().getId() == transformer.getId()) {
				residences.add(r);
			}
		}
		return residences;
	}

}