package ar.utn.frba.dds.g13;
import static spark.Spark.*;

import java.awt.Point;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jtwig.*;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

import com.google.gson.Gson;

import ar.utn.frba.dds.g13.area.Area;
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
import ar.utn.frba.dds.g13.transformer.Transformer;
import ar.utn.frba.dds.g13.user.Administrator;
import ar.utn.frba.dds.g13.user.Client;
import ar.utn.frba.dds.g13.user.Residence;
import ar.utn.frba.dds.g13.user.User;
import spark.Request;

public class SparkApp {
	
	public static Client client1;
	public static Administrator admin1;
	
	public static JtwigTemplate getTemplate(String filename) {
		return JtwigTemplate.fileTemplate( Paths.get("src", "main", "resources", "templates", filename + ".twig").toFile() );
	}
	
	static SessionFactory factory = null; 
	public static SessionFactory getSessionFactory() {
		if(factory == null) {
	        Configuration configObj = new Configuration();
	        configObj.configure("hibernate.cfg.xml");

	        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

	        factory = configObj.buildSessionFactory(serviceRegistryObj);    
		}
		return factory;
    }

	static ArrayList<Category> categories = null;
	static ArrayList<Area> areas = null;
	static ArrayList<Transformer> transformers = null;

	static ArrayList<Client> users = null;
	static ArrayList<Administrator> administrators = null;

	public static void main(String[] args) {
		
		loadData();
		
		staticFiles.location("/public/");
		
		EnvironmentConfigurationBuilder
	        .configuration()
	            .resources()
	                .withDefaultInputCharset(Charset.forName("UTF-8"))
	            .and()
	            .render()
	                .withOutputCharset(Charset.forName("UTF-8"))
	            .and()
	        .build();
		
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

	        model.with("areas", areas);
	        model.with("transformers", transformers);
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
        	for(Administrator u : administrators) {
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
		
		//CLIENT
		
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
	        System.out.println(request.queryParams("device_type"));
	        if(DeviceInfoTable.getDeviceByName(request.queryParams("device_type")).isInteligente()) {
	        	d = new StandardDevice(request.queryParams("device_name"), DeviceInfoTable.getDeviceByName(request.queryParams("device_type")));
	        } else {
	        	List<TimeIntervalDevice> consumptionHistory = new ArrayList<TimeIntervalDevice>();
	        	d = new SmartDevice(request.queryParams("device_name"), DeviceInfoTable.getDeviceByName(request.queryParams("device_type")), consumptionHistory, new DeviceOn());
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
		
		get("/client/residence/terms", (request, response) -> {
	        Client client = (Client) loadUser(request);
	        JtwigTemplate template = getTemplate("client_terms.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "client_home");
	        model.with("client_section", "timelines");
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
		
		//ADMIN

		get("/admin/devices", (request, response) -> {
	        Administrator admin = (Administrator) loadUser(request);
	        JtwigTemplate template = getTemplate("admin_devices.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "admin_home");
	        model.with("admin_section", "devices");
        	model.with("current_user", admin);
        	
        	model.with("devices_list", DeviceInfoTable.getDevicesInfos());
	        
	        return template.render(model);
		});
		
		get("/admin/reports", (request, response) -> {
	        Administrator admin = (Administrator) loadUser(request);
	        JtwigTemplate template = getTemplate("admin_reports.html");
	        JtwigModel model = JtwigModel.newModel();
	        model.with("menu_section", "admin_home");
	        model.with("admin_section", "reports");
        	model.with("current_user", admin);
	        
	        return template.render(model);
		});
		
		
		
		//AJAX
		post("/ajax_utils/", (request, response) -> {
	        String function = request.queryParamOrDefault("ajax_utility", "-1");
	        System.out.println("AjaxCall");
	        switch(function) {
		        case "search_residence_devices":
			        {
				        Client client = (Client) loadUser(request);
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
			        }
		        case "query_residence_terms":
			        {
				        Client client = (Client) loadUser(request);
				        String rid = request.queryParams("rid");
				        Residence r = null;
				        for(Residence tr : client.getResidences()) {
				        	if((tr.getId() + "").equals(rid)) {
				        		r = tr;
				        	}
				        }
				        
				        String str_start = request.queryParams("start");
				        Calendar start = formatDateToCalendar(str_start);
				        
				        String str_end = request.queryParams("end");
				        Calendar end = formatDateToCalendar(str_end);
						
				        BigDecimal res = r.consumptionBetween(start, end);
				        JtwigTemplate template = getTemplate("client_terms_helper.html");
				        JtwigModel model = JtwigModel.newModel();
				        
				        model.with("start", formatDateToString(start));
				        model.with("end", formatDateToString(end));
				        model.with("consumption", res.doubleValue());
				        return template.render(model);
			        }
		        case "search_device_info":
			        {
				        String q = request.queryParams("q");
			        	Collection<DeviceInfo> devices_infos = DeviceInfoTable.getDevicesInfos();
			        	Collection<DeviceInfo> result = new ArrayList<DeviceInfo>();
			        	
			        	for(DeviceInfo info : devices_infos) {
			        		if(info.getName().toLowerCase().contains(q.toLowerCase())) {
			        			result.add(info);
			        		}
			        	}
				        JtwigTemplate template = getTemplate("util_render_devicesinfo_list.html");
				        JtwigModel model = JtwigModel.newModel();			        	
			        	model.with("devices_list", result);				        
				        return template.render(model);
			        }
		        case "admin_report":
			        {
			        	System.out.println("ADMIN REPORT");
			        	String type = request.queryParams("report_type");
			        	
				        String str_start = request.queryParams("start");
				        Calendar start = formatDateToCalendar(str_start);
				        
				        String str_end = request.queryParams("end");
				        Calendar end = formatDateToCalendar(str_end);
				        
			        	switch(type) {
				        	case "devices":
					        	{
					        		BigDecimal consumptionSmart = new BigDecimal(0);
					        		int countSmart = 0;
					        		BigDecimal consumptionStandard = new BigDecimal(0);
					        		int countStandard = 0;
					        		
					        		System.out.println("    DEVICES");
					        		
					        		for(Client c : users) {
					        			for(Residence r : c.getResidences()) {
					        				for(Device d : r.getDevices()) {
					        					if(d.isSmart()) {
					        						countSmart++;
					        						consumptionSmart.add(((SmartDevice) d).consumptionBetween(start, end));
					        					} else {
					        						countStandard++;
					        					}
					        				}
					        			}
					        		}
					        		
					        		if(countSmart == 0) countSmart = 1;
					        		if(countStandard == 0) countStandard = 1;
					        		
							        JtwigTemplate template = getTemplate("admin_report_devices.html");
							        JtwigModel model = JtwigModel.newModel();	
							        model.with("start", formatDateToString(start));
							        model.with("end", formatDateToString(end));
							        model.with("consumption_smart", (consumptionSmart.divide(new BigDecimal(countSmart))).doubleValue());
							        model.with("consumption_standard", (consumptionStandard.divide(new BigDecimal(countStandard))).doubleValue());
							        return template.render(model);
					        	}				        		
				        	case "residences":
					        	{					        		
							        JtwigTemplate template = getTemplate("admin_report_residences.html");
							        JtwigModel model = JtwigModel.newModel();	
							        model.with("start", formatDateToString(start));
							        model.with("end", formatDateToString(end));
							        model.with("start_obj", start);
							        model.with("end_obj", end);
							        model.with("clients", users);
							        return template.render(model);
					        	}
				        	case "transformers":
					        	{
					        		ArrayList<Transformer> transformers_r = new ArrayList<Transformer>();
					        		
					        		for(Client c : users) {
					        			for(Residence r : c.getResidences()) {
					        				if(!transformers_r.contains(r.getTransformer())) {
					        					transformers_r.add(r.getTransformer());
					        				}
					        			}
					        		}
					        		
							        JtwigTemplate template = getTemplate("admin_report_transformers.html");
							        JtwigModel model = JtwigModel.newModel();	
							        model.with("start", formatDateToString(start));
							        model.with("end", formatDateToString(end));
							        model.with("start_obj", start);
							        model.with("end_obj", end);
							        model.with("transformers", transformers_r);
							        return template.render(model);
					        	}
			        	}
			        }		        	
		        	return "";
		        case "load_transformer_data":
			        {
			        	JtwigTemplate template = getTemplate("transformer_sidebar.html");
				        JtwigModel model = JtwigModel.newModel();	

				        Transformer tr = null;
				        String tid = request.queryParams("tid");
				        for(Transformer t : transformers) {
				        	if((t.getId() + "").equals(tid)) {
				        		tr = t;
				        	}
				        }
				        
				        model.with("transformer", tr);
				        return template.render(model);
			        }
		        case "-1":
				default:
		        		break;
	        }
	        return "";
		});
		
	}
	
	private static Calendar formatDateToCalendar(String date) {
	    Calendar cal = Calendar.getInstance(
				  TimeZone.getTimeZone("UTC"));
	    cal.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
	    cal.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]));
	    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("-")[2]));
    
		return cal;
	}
	
	private static String formatDateToString(Calendar date) {
		return date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR);
	}
	
	private static User loadUser(Request request) {
		request.session(true);
        if(request.session().isNew()) {
        	request.session().attribute("current_user", null);
        	//request.session().attribute("current_user", admin1);
        	//return admin1;
        } else {
        	//request.session().attribute("current_user", admin1);
        	User user = request.session().attribute("current_user");
        	if(user == null) {
        		System.out.println(request.session().id() + " no está logueado");
        	} else {
        		System.out.println(request.session().id() + " logueado " + user.getFullname());
        	}
        	return user;
        }
        return null;
	}

	private static void loadData() {		
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Long ID = null;
		try {
			DeviceInfoTable.setDevices((ArrayList<DeviceInfo>) session.createCriteria(DeviceInfo.class).list());

			categories = (ArrayList<Category>) session.createCriteria(Category.class).list();
			areas = (ArrayList<Area>) session.createCriteria(Area.class).list();
			
			transformers = (ArrayList<Transformer>) session.createCriteria(Transformer.class).list();
			
			administrators = (ArrayList<Administrator>) session.createCriteria(Administrator.class).list();
			users = (ArrayList<Client>) session.createCriteria(Client.class).list();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}
	}


}
