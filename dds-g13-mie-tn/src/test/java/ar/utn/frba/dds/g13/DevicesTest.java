package ar.utn.frba.dds.g13;

import json.ResidentialClientLoader;
import org.junit.Before;
import org.junit.Test;

import ar.utn.frba.dds.g13.devices.*;
import java.math.BigDecimal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DevicesTest {


	List<ResidentialClient> clients;

    @Test
    public void test_check_state() {
    	StateOn on = new StateOn();
    	BigDecimal pcConsumption = new BigDecimal("0.720");
    	SmartDevice oneSmart = new SmartDevice(true, pcConsumption,"pc", on);
        assertEquals( oneSmart.isOn(), true );
    }
    @Test
    public void test_check_state_change() {
    	StateOn on = new StateOn();
    	BigDecimal pcConsumption = new BigDecimal("0.720");
    	SmartDevice oneSmart = new SmartDevice(true, pcConsumption,"pc", on);
    	oneSmart.setDeviceOff();
        assertEquals( oneSmart.isOff(), true );
    }
}