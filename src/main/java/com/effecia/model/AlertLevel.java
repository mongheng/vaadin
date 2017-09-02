package com.effecia.model;

public enum AlertLevel {

	WARN("Warn"), ERROR("Error"), FATAL("Fatal");
	
	private final String typeAlertLevel;

	private AlertLevel(String typeAlertLevel) {
		this.typeAlertLevel = typeAlertLevel;
	}
	
	public String getTypeAlertLevel() {
		return typeAlertLevel;
	}
}
