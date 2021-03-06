package com.emh.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HISTORY_PAYMENT")
public class HistoryPayment implements Serializable{

	private static final long serialVersionUID = 1L;

	private String historyID;
	private Integer installmentNumber;
	private String customerName;
	private Integer floorNumber;
	private Integer unitNumber;
	private Float amount;
	private String carType;
	private String plantNumber;
	private LocalDate paymentDate;
	private LocalDate receiveDate;
	private User user;

	@Id
	@Column(name = "HISTORY_ID")
	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	@Column(name = "INSTALLMENT_NUMBER")
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "FLOOR_NUMBER")
	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	@Column(name = "UNIT_NUMBER")
	public Integer getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	@Column(name = "AMOUNT")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Column(name = "CARTPYE")
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

	@Column(name = "PAYMENT_DATE")
	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name = "RECEIVE_DATE")
	public LocalDate getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(LocalDate receiveDate) {
		this.receiveDate = receiveDate;
	}

	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
