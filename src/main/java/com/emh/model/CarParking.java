package com.emh.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CARPARKING")
public class CarParking implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String carparkingID;
	private String carType;
	private String plantNumber;
	private boolean free;
	private Float amount;
	private boolean activated;
	private boolean close;
	private Customer customer;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "CARPAKING_ID", unique = true)
	public String getCarparkingID() {
		return carparkingID;
	}

	public void setCarparkingID(String carparkingID) {
		this.carparkingID = carparkingID;
	}

	@Column(name = "CARTYPE")
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	@Column(name = "PLANTNUMBER")
	public String getPlantNumber() {
		return plantNumber;
	}

	public void setPlantNumber(String plantNumber) {
		this.plantNumber = plantNumber;
	}

	@Column(name = "FREE", columnDefinition = "BOOLEAN DEFAULT true")
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	@Column(name = "AMOUNT")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Column(name = "ACTIVATE", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	@Column(name = "CLOSE", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
	
	@ManyToOne()
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
