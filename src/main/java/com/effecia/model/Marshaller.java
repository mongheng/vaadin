package com.effecia.model;

import java.io.Serializable;
import java.util.List;

public class Marshaller implements Serializable {

	private static final long serialVersionUID = 1L;

	private String commandLine;
	private List<Object> objects;
	private List<AlertMessager> alertMessagers;

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public List<AlertMessager> getAlertMessagers() {
		return alertMessagers;
	}

	public void setAlertMessagers(List<AlertMessager> alertMessagers) {
		this.alertMessagers = alertMessagers;
	}
}
