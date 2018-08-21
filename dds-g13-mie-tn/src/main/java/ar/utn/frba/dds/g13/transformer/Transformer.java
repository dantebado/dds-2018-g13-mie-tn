package ar.utn.frba.dds.g13.transformer;

import java.math.BigDecimal;
import java.util.List;

import ar.utn.frba.dds.g13.area.Area;
import ar.utn.frba.dds.g13.user.Residence;

public class Transformer {

    int transformerId;
    Area geographicalArea;
    List<Residence> residences;

    public Transformer(int transformerId, Area geographicalArea, List<Residence> residences) {
        this.transformerId = transformerId;
        this.geographicalArea = geographicalArea;
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