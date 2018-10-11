package ar.utn.frba.dds.g13.transformer;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
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
import ar.utn.frba.dds.g13.json.BeanToJson;
import ar.utn.frba.dds.g13.user.Residence;
import ar.utn.frba.dds.g13.json.BeanToJson;


@Entity
@Table(name = "Transformer")
public class Transformer extends BeanToJson{
	
	@Id								
	@GeneratedValue
	@Column(name="transformer_id")
	private Long id;

	@Transient
    Point coordinate;

	@Column(name="x")
	@Expose double coordX;
	@Column(name="y")
	@Expose double coordY;
	
	@Transient
    @Expose List<Residence> residences;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="area_id")
	@Expose Area area;    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public List<Residence> getResidences(){
		return area.getResidencesByTransformer(this);
	}
	
	public void setResidences(List<Residence> residences){
		this.residences = residences;
	}

	public Transformer() {
		super();
	}
    
    public Transformer(Point coordinate, Area area, List<Residence> residences) {
        this.setCoordinate(coordinate);
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
    
    public BigDecimal energySuppliedAverageBetween(Calendar start, Calendar end) {
        BigDecimal totalConsumption = new BigDecimal(0);
        int counter = 0;
        for(Residence residence : residences) {
            totalConsumption = totalConsumption.add(residence.consumptionBetween(start, end));
            counter = counter + 1;
        }
        return totalConsumption.divide(new BigDecimal(counter));
    }

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}
}