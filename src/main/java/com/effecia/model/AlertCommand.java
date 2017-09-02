package com.effecia.model;

import java.io.Serializable;
import java.util.Map;

public class AlertCommand implements Serializable {
	
	private static final long serialVersionUID = 7279075465095812611L;
	
	private AlertCommandType alertCommandType;
	private Map<String,Object> parameters;
	private Marshaller marshaller;
	
	public AlertCommandType getAlertCommandType() {
		return alertCommandType;
	}
	public void setAlertCommandType(AlertCommandType alertCommandType) {
		this.alertCommandType = alertCommandType;
	}
	
	public Map<String,Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String,Object> parameters) {
		this.parameters = parameters;
	}
	public Marshaller getMarshaller() {
		return marshaller;
	}
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	
}
