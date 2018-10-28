package ar.utn.frba.dds.g13.device.automation.rules;

import java.util.List;

public class AutomationRequirement {
	
	String rule_name;
	
	List<SensorRequirement> requirements;
	
	public AutomationRequirement(String rule_name, List<SensorRequirement> requirements) {
		this.rule_name = rule_name;
		this.requirements = requirements;
	}
	
	public void addRequirement(SensorRequirement req) {
		requirements.add(req);
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public List<SensorRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<SensorRequirement> requirements) {
		this.requirements = requirements;
	}

}
