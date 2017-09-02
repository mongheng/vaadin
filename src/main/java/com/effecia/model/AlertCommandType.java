package com.effecia.model;

public enum AlertCommandType {
	
	ALERT("Alert"), RESOLVE("Resolve"), AGENT_REGISTER("Agent_Register"), APP_REGISTER("App_Register");
	
	private String typeAlertCommandType;

	private AlertCommandType(String typeAlertCommandType) {
		this.typeAlertCommandType = typeAlertCommandType;
	}

	public String getTypeAlertCommandType() {
		return typeAlertCommandType;
	}
}
