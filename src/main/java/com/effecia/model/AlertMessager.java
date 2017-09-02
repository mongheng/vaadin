package com.effecia.model;

import java.io.Serializable;
import java.util.Set;

public class AlertMessager implements Serializable {

	private static final long serialVersionUID = -1882397006403808393L;
	
	private String alertId;
	private AlertLevel alertLevel;
	private String details;
	private int counter;
	private Set<String> receivers;
	
	public AlertMessager() {
		super();
	}

	public AlertMessager(String alertId, AlertLevel alertLevel, String details, int counter) {
		super();
		this.alertId = alertId;
		this.alertLevel = alertLevel;
		this.details = details;
		this.counter = counter;
	}

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public AlertLevel getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(AlertLevel alertLevel) {
		this.alertLevel = alertLevel;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Set<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<String> receivers) {
		this.receivers = receivers;
	}
}
