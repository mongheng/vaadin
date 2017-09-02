package com.effecia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AlertAgent {

	private AlertSender alertSender;
	private String moduleName;
	private Set<String> receivers;

	public AlertSender getAlertSender() {
		return alertSender;
	}

	public void setAlertSender(AlertSender alertSender) {
		this.alertSender = alertSender;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<String> receivers) {
		this.receivers = receivers;
	}

	public static void trigerAlert(String uuid, String details, AlertLevel alertLevel) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("senderContext.xml");
		AlertAgent alertAgent = (AlertAgent) applicationContext.getBean("alertAgent");

		AlertCommand alertCommand = (AlertCommand) applicationContext.getBean("alertCommand");
		Map<String,Object> parameters = new HashMap<String,Object>();
		Marshaller marshaller = new Marshaller();
		List<AlertMessager> alertMessagers = new ArrayList<AlertMessager>();
		if (alertCommand.getAlertCommandType().equals(AlertCommandType.ALERT)) {
			AlertMessager alertMessager = (AlertMessager) applicationContext.getBean("alertMessager");
			alertMessager.setAlertId(uuid);
			alertMessager.setDetails(details.replace(" ", "~"));
			alertMessager.setAlertLevel(alertLevel);
			alertMessager.setReceivers(alertAgent.getReceivers());
			alertMessagers.add(alertMessager);
			parameters.put(AlertCommandType.ALERT.toString(), alertMessager);
			marshaller.setCommandLine(AlertCommandType.ALERT.toString());
			marshaller.setAlertMessagers(alertMessagers);
			alertCommand.setMarshaller(marshaller);
			alertCommand.setParameters(parameters);
		} else if (alertCommand.getAlertCommandType().equals(AlertCommandType.AGENT_REGISTER)) {
			parameters.put(AlertCommandType.AGENT_REGISTER.toString(), alertAgent);
			alertCommand.setParameters(parameters);
		} else if (alertCommand.getAlertCommandType().equals(AlertCommandType.RESOLVE)) {
			parameters.put(AlertCommandType.RESOLVE.toString(), "CLIENT RESOLVED");
			alertCommand.setParameters(parameters);
		} else if (alertCommand.getAlertCommandType().equals(AlertCommandType.APP_REGISTER)) {
			
		}
		alertAgent.alertSender.feedAlert(alertCommand);
		((ClassPathXmlApplicationContext) applicationContext).close();
	}
}
