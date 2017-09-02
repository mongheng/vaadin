package com.effecia.model;

/*@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=As.PROPERTY, property="@class")
@JsonSubTypes({@Type(value=HttpAlertSender.class, name="httpAlertSender"), @Type(value=SocketAlertSender.class, name="socketAlertSender")})*/
public interface AlertSender {
	
	public void feedAlert(AlertCommand alertCommand);
}
