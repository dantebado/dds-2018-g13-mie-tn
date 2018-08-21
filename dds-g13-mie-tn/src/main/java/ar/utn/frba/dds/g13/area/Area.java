package ar.utn.frba.dds.g13.area;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;

import ar.utn.frba.dds.g13.transformer.Transformer;

public class Area {

    String areaName;
    List<Transformer> transformers;
    Float radius;
    Point coordinate;

    public Area(String areaName, Float radius, List<Transformer> transformers, Point coordinate) {
        this.areaName = areaName;
        this.radius = radius;
        this.transformers = transformers;
        this.coordinate = coordinate;
    }

    public BigDecimal energySupplied() {
        BigDecimal totalConsumption = new BigDecimal(0);
        for(Transformer transformer : transformers) {
            totalConsumption = totalConsumption.add(transformer.energySupplied());
        }
        return totalConsumption;
    }

}