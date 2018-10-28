package ar.utn.frba.dds.g13;
import static spark.Spark.*;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.jtwig.*;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;
import ar.utn.frba.dds.g13.device.StandardDevice;
import ar.utn.frba.dds.g13.device.StateHistory;
import ar.utn.frba.dds.g13.device.TimeIntervalDevice;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRequirement;
import ar.utn.frba.dds.g13.device.automation.rules.AutomationRule;
import ar.utn.frba.dds.g13.device.automation.rules.SensorRequirement;
import ar.utn.frba.dds.g13.device.automation.rules.TurnEnergySavingRule;
import ar.utn.frba.dds.g13.device.automation.rules.TurnOffWhenCold;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfo;
import ar.utn.frba.dds.g13.device.deviceinfo.DeviceInfoTable;
import ar.utn.frba.dds.g13.device.sensor.Sensor;
import ar.utn.frba.dds.g13.device.sensor.TemperatureSensor;
import ar.utn.frba.dds.g13.device.states.DeviceEnergySaving;
import ar.utn.frba.dds.g13.device.states.DeviceOff;
import ar.utn.frba.dds.g13.device.states.DeviceOn;
import ar.utn.frba.dds.g13.user.Client;
import ar.utn.frba.dds.g13.user.Residence;
import ar.utn.frba.dds.g13.user.User;
import spark.Request;

public class SparkApp {
	
	public static Client client1;
	
	public static JtwigTemplate getTemplate(String filename) {
		return JtwigTemplate.fileTemplate( Paths.get("src", "main", "resources", "templates", filename + ".twig").toFile() );
	}

	public static void main(String[] args) {
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Aire Acondicionado 3500", "Aire Acondicionado 3500", true, false, 1.613, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Aire Acondicionado 2200", "Aire Acondicionado 2200", true, true, 1.013, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("TV Tubo 21inch", "TV Tubo 21inch", false, false, 0.075, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("TV Tubo 21-34inch", "TV Tubo 21-34inch", false, false, 0.175, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("LCD 40inch", "LCD 40inch", false, false, 0.18, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("LCD 24inch", "LCD 24inch", true, true, 0.04, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("LCD 32inch", "LCD 32inch", true, true, 0.055, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("LCD 40inch", "LCD 40inch", true, true, 0.08, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Heladera Freezer", "Heladera Freezer", true, true, 0.09, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Heladera", "Heladera", true, true, 0.075, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Lavarropas Auto 5kg Calentador", "Lavarropas Auto 5kg Calentador", false, false, 0.875, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Lavarropas Auto 5kg", "Lavarropas Auto 5kg", true, true, 0.175, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Lavarropas Semiauto 5kg", "Lavarropas Semiauto 5kg", false, true, 0.1275, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Ventilador de Pie", "Ventilador de Pie", false, true, 0.09, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Ventilador de Techo", "Ventilador de Techo", true, true, 0.06, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara Hal�gena 40W", "L�mpara Hal�gena 40W", true, false, 0.04, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara Hal�gena 60W", "L�mpara Hal�gena 60W", true, false, 0.06, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara Hal�gena 100W", "L�mpara Hal�gena 100W", true, false, 0.015, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara 11W", "L�mpara Hal�gena 15W", true, true, 0.011, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara 15W", "L�mpara Hal�gena 15W", true, true, 0.015, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("L�mpara 20W", "L�mpara Hal�gena 20W", true, true, 0.02, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("PC Escritorio", "PC Escritorio", true, true, 0.4, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Microondas Convencional", "Microondas Convencional", false, true, 0.64, 0, 0, 0));
		DeviceInfoTable.addDeviceInfo(new DeviceInfo("Plancha A Vapor", "Plancha A Vapor", false, true, 0.75, 0, 0, 0));
		
		
		Calendar calendarDate = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
		calendarDate.set(Calendar.YEAR, 2017);
		calendarDate.set(Calendar.MONTH, 10);
		calendarDate.set(Calendar.DAY_OF_MONTH, 15);
		ArrayList<Residence> residences = new ArrayList<Residence>();
		
		Category category = new Category("Residencial", 200, 500, new BigDecimal(500), new BigDecimal(1.5));
		
		client1 = new Client("name", "pass",
				"Juan Perez", "Balcarce 50", calendarDate,
				"DNI", "20469755", "43687952",
				category, 13,
				residences);
		client1.setId(1l);

		ArrayList<Device> devices = new ArrayList<Device>();
		Residence residence = new Residence("Segurola y Habana", devices, client1, null);
		residence.setId(1l);
		residences.add(residence);
		
		List<StateHistory> stateHistoryList = new ArrayList<StateHistory>();
		List<StateHistory> stateHistoryListTwo = new ArrayList<StateHistory>();
		List<TimeIntervalDevice> timeIntervalList = new ArrayList<TimeIntervalDevice>();
		List<TimeIntervalDevice> timeIntervalListTwo = new ArrayList<TimeIntervalDevice>();
		
		SmartDevice smart_device = new SmartDevice("TV", DeviceInfoTable.getDeviceByName("TV"), timeIntervalList, new DeviceOn());
		smart_device.setId(1l);
		SmartDevice smart_device_two = new SmartDevice("PC", DeviceInfoTable.getDeviceByName("PC"), timeIntervalListTwo, new DeviceEnergySaving());
		smart_device_two.setId(2l);
		residence.addDevice(smart_device);
		residence.addDevice(smart_device_two);
		
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(new TemperatureSensor(5, smart_device));
		sensors.get(0).setId(1l);
		
		Actuator act = new Actuator(smart_device, new ArrayList<AutomationRule>(), sensors);
		
		
		//DATA
		ArrayList<User> users = new ArrayList<User>();
		users.add(client1);
		
		staticFiles.location("/public/");
		
		get("/test_twig", (req, res) -> {
			
	        JtwigTemplate template = getTemplate("full_base.html");
	        JtwigModel model = JtwigModel.newModel();
	        
	        model.with("name", "Carlos");
	        
	        return template.render(model);
		});
		
		get("/test", (req, res) -> "Test successful");
		
		//WEBSITE
		
		get("/", (request, res) -> {
	        JtwigTemplate template = getTemplate("map.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "map");
	        
	        User user = loadUser(request);
	        if(user == null) {
		        model.with("current_user", "-1");
	        } else {
	        	model.with("current_user", user);
	        }
	        
	        return template.render(model);
		});
		
		post("/login_pst", (request, response) -> {
	        User user = loadUser(request);
	        //Check user credentials
        	for(User u : users) {
        		if(u.checkCredentials(request.queryParams("username"), request.queryParams("password"))) {
                	request.session().attribute("current_user", u);
                	response.redirect("/");
                	return null;
        		}
        	}
        	response.redirect("/");
	        return "";
		});
		
		get("/logout", (request, response) -> {
			request.session().invalidate();
        	response.redirect("/");
        	return "";
		});
		
		get("/client", (request, response) -> {
        	response.redirect("/client/residence");
        	return "";
		});
		
		get("/client/residence", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_home.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "state");
        	model.with("current_user", client);
	        
	        if(client.getResidences().size() == 0) {
	        	//No residences to display
	        	model.with("has_residences", false);
	        } else {
	        	model.with("has_residences", true);
		        String rid = request.queryParamOrDefault("rid", client.getResidences().get(0).getId() + "");
		        
		        Residence r = null;
		        for(Residence tr : client.getResidences()) {
		        	if((tr.getId() + "").equals(rid)) {
		        		r = tr;
		        	}
		        }
		        
		        model.with("rid", r.getId());
		        model.with("display_residence", r);
		        model.with("residences", client.getResidences());
	        }
	        
	        return template.render(model);
		});
		
		get("/client/residence/devices", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_devices.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "devices");
        	model.with("current_user", client);
	        
	        if(client.getResidences().size() == 0) {
	        	//No residences to display
	        	model.with("has_residences", false);
	        } else {
	        	model.with("has_residences", true);
		        String rid = request.queryParamOrDefault("rid", client.getResidences().get(0).getId() + "");
		        
		        Residence r = null;
		        for(Residence tr : client.getResidences()) {
		        	if((tr.getId() + "").equals(rid)) {
		        		r = tr;
		        	}
		        }
		        
		        model.with("rid", r.getId());
		        model.with("display_residence", r);
		        model.with("residences", client.getResidences());
	        }
	        
	        return template.render(model);
		});
		
		get("/client/residence/devices/load", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_devices_load.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "devices");
        	model.with("current_user", client);
	        
	        if(client.getResidences().size() == 0) {
	        	//No residences to display
	        	model.with("has_residences", false);
	        } else {
	        	model.with("has_residences", true);
		        String rid = request.queryParams("rid");
		        
		        Residence r = null;
		        for(Residence tr : client.getResidences()) {
		        	if((tr.getId() + "").equals(rid)) {
		        		r = tr;
		        	}
		        }
		        
		        model.with("rid", r.getId());
		        model.with("display_residence", r);
		        model.with("residences", client.getResidences());
		        model.with("device_info_table", DeviceInfoTable.getDevicesInfos());
	        }
	        
	        return template.render(model);
		});
		
		post("/device_load/", (request, response) -> {
	        Client client = (Client) loadUser(request);

	        String rid = request.queryParams("rid");
	        
	        Residence r = null;
	        for(Residence tr : client.getResidences()) {
	        	if((tr.getId() + "").equals(rid)) {
	        		r = tr;
	        	}
	        }
	        
	        Device d = null;
	        if(DeviceInfoTable.getDeviceByName(request.queryParams("device_type")).isInteligente()) {
	        	d = new StandardDevice(request.queryParams("device_name"), DeviceInfoTable.getDeviceByName(request.queryParams("device_type")));
	        } else {
	        	List<TimeIntervalDevice> consumptionHistory = new ArrayList<TimeIntervalDevice>();
	        	d = new SmartDevice(request.queryParams("device_name"), DeviceInfoTable.getDeviceByName(request.queryParams("device_type")), consumptionHistory, new DeviceOff());
	        }
	        r.addDevice(d);

        	response.redirect("/");
	        return null;
		});
		
		get("/client/residence/automation", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_automation.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "automation");
        	model.with("current_user", client);
	        
	        if(client.getResidences().size() == 0) {
	        	//No residences to display
	        	model.with("has_residences", false);
	        } else {
	        	model.with("has_residences", true);
		        String rid = request.queryParamOrDefault("rid", client.getResidences().get(0).getId() + "");
		        
		        Residence r = null;
		        for(Residence tr : client.getResidences()) {
		        	if((tr.getId() + "").equals(rid)) {
		        		r = tr;
		        	}
		        }
		        
		        model.with("available_rules", AutomationRule.rules_names);
		        model.with("devices", r.getSmartDevices());
		        model.with("rid", r.getId());
		        model.with("display_residence", r);
		        model.with("residences", client.getResidences());
	        }
	        
	        return template.render(model);
		});
		
		post("/client/residence/automation/check/", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_automation_check.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "automation");
        	model.with("current_user", client);
	        
	        String rid = request.queryParamOrDefault("rid", client.getResidences().get(0).getId() + "");	        
	        Residence r = null;
	        for(Residence tr : client.getResidences()) {
	        	if((tr.getId() + "").equals(rid)) {
	        		r = tr;
	        	}
	        }
	        String did = request.queryParams("device");
	        SmartDevice d = null;
	        for(SmartDevice td : r.getSmartDevices()) {
	        	if((td.getId() + "").equals(did)) {
	        		d = td;
	        	}
	        }
	        
	        ArrayList<Integer> r_indexes = new ArrayList<Integer>();
	        ArrayList<AutomationRequirement> to_fullfill = new ArrayList<AutomationRequirement>();
	        ArrayList<Sensor> userSensors = client.getSensorCollection();
	        for(String s : AutomationRule.rules_names) {
	        	if(request.queryParams(s) != null) {
	        		ArrayList<SensorRequirement> requirements = new ArrayList<SensorRequirement>();
	        		AutomationRequirement requirement = new AutomationRequirement(s, requirements);
	        		r_indexes.add(AutomationRule.getRuleIndexByName(s));
	        		
	        		int[] required = AutomationRule.getRequiredSensorsByType(s);
	        		
    				for(int i : required) {
    					ArrayList<Sensor> availables = new ArrayList<Sensor>();
    					String sensor_type = Sensor.types[i];
    					
    					for(Sensor ts : userSensors) {
    						if(ts.getClass() == Sensor.searchClassByType(i)) {
    							availables.add(ts);
    						}
    					}
    					
    					requirements.add(new SensorRequirement(sensor_type, availables));
    				}
	        		to_fullfill.add(requirement);
	        	}
	        }
	        
	        String indexes_str = "";
	        int c = 0;
	        for(c=0 ; c<r_indexes.size() ; c++) {
	        	indexes_str += r_indexes.get(c);
	        	if(c != (r_indexes.size()-1)) {
	        		indexes_str += ";";
	        	}
	        }
	        		
	        model.with("rules_indexes", indexes_str);
	        model.with("rules", to_fullfill);
	        model.with("rid", r.getId());
	        model.with("device", d);
	        model.with("display_residence", r);
	        model.with("residences", client.getResidences());
	        
	        return template.render(model);
		});
		
		
		post("/client/residence/automation/finish_pipeline/", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        
	        String rid = request.queryParamOrDefault("rid", client.getResidences().get(0).getId() + "");	        
	        Residence r = null;
	        for(Residence tr : client.getResidences()) {
	        	if((tr.getId() + "").equals(rid)) {
	        		r = tr;
	        	}
	        }
	        String did = request.queryParams("device");
	        SmartDevice d = null;
	        for(SmartDevice td : r.getSmartDevices()) {
	        	if((td.getId() + "").equals(did)) {
	        		d = td;
	        	}
	        }
	        
	        System.out.println("CREANDO REGLAS SOBRE " + d.getName());

	        String indexes = request.queryParams("rules_indexes");
	        String[] indexes_spl = indexes.split(";");
	        ArrayList<Sensor> userSensors = client.getSensorCollection();
	        for(String str : indexes_spl) {
	        	ArrayList<AutomationRule> a_rules = new ArrayList<AutomationRule>();
	        	ArrayList<Sensor> a_sensors = new ArrayList<Sensor>();
	        	int tindex = Integer.parseInt(str);
	        	
	        	System.out.println("     REGLA " + AutomationRule.rules_names[tindex]);

        		int[] required = AutomationRule.getRequiredSensorsByType(AutomationRule.rules_names[tindex]);
        		for(int q : required) {
        			
        			System.out.println("          REQUIRE SENSOR " + Sensor.types[q]);
        			
        			String input_name = "sensor_" + AutomationRule.rules_names[tindex] + "_" + Sensor.types[q];
        			long value = Long.parseLong(request.queryParams(input_name));
        			if(value == -1) {
        				System.out.println("               CREO UNO NUEVO");
        				a_sensors.add(Sensor.createByType(q, 5, d));	//INTERVALO
        			} else {
        				for(Sensor s : userSensors) {
        					if(s.getId() == value) {
        						a_sensors.add(s);
        						System.out.println("               UTILIZO EL EXISTENTE " + s.getId());
        					}
        				}
        			}
        		}

		        Actuator n_act = new Actuator(d, a_rules, a_sensors);
	        }
	        
        	response.redirect("/client/residence?rid=" + rid);
	        return "";
		});
		
		
		
		//AJAX
		post("/ajax_utils/", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        String function = request.queryParamOrDefault("ajax_utility", "-1");
	        switch(function) {
		        case "search_residence_devices":
			        String rid = request.queryParams("rid");
			        String q = request.queryParams("q");
			        Residence r = null;
			        ArrayList<Device> d = new ArrayList<Device>();
			        for(Residence tr : client.getResidences()) {
			        	if((tr.getId() + "").equals(rid)) {
			        		r = tr;
			        		for(Device td : r.getDevices()) {
			        			if(td.getName().toLowerCase().contains(q.toLowerCase())) {
			        				d.add(td);
			        			}
			        		}
			        	}
			        }
			        JtwigTemplate template = getTemplate("client_devices_helper.html");
			        JtwigModel model = JtwigModel.newModel();
			        model.with("devices", d);
			        return template.render(model);
		        case "-1":
				default:
		        		break;
	        }
	        return "";
		});
		
	}
	
	private static User loadUser(Request request) {
		request.session(true);
        if(request.session().isNew()) {
        	request.session().attribute("current_user", null);
        	request.session().attribute("current_user", client1);
        	return client1;
        } else {
        	request.session().attribute("current_user", client1);
        	User user = request.session().attribute("current_user");
        	if(user == null) {
        		System.out.println(request.session().id() + " no est� logueado");
        	} else {
        		System.out.println(request.session().id() + " logueado " + user.getFullname());
        	}
        	return user;
        }
        //return null;
	}

}
