package ar.utn.frba.dds.g13.transformer;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.area.Area;
import ar.utn.frba.dds.g13.user.Residence;


@Entity
@Table(name = "Transformer")
public class Transformer {
	
	@Id								
	@GeneratedValue
	@Column(name="transformer_id")
	private Long id;

	@Column(name="coordinate")
    Point coordinate;
	
	@Transient
    List<Residence> residences;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	@Expose Area area;
    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Transformer() {
		super();
	}
    
    public Transformer(Point coordinate, Area area, List<Residence> residences) {
        this.coordinate = coordinate;
        this.area = area;
        this.residences = residences;
    }

    public BigDecimal energySupplied() {
        BigDecimal totalConsumption = new BigDecimal(0);
        for(Residence residence : residences) {
            totalConsumption = totalConsumption.add(residence.actualConsumption());
        }
        return totalConsumption;
    }
}