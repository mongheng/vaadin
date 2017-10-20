package com.emh.model.mock;

import com.emh.model.CarParking;

public class MockParkingCashFlow {
	private String cashflowID;
	private Integer installmentNumber;
	private Float amount;
	private String startDate;
	private String endDate;
	private boolean statu;
	private CarParking carparking;

	public String getCashflowID() {
		return cashflowID;
	}

	public void setCashflowID(String cashflowID) {
		this.cashflowID = cashflowID;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isStatu() {
		return statu;
	}

	public void setStatu(boolean statu) {
		this.statu = statu;
	}

	public CarParking getCarparking() {
		return carparking;
	}

	public void setCarparking(CarParking carparking) {
		this.carparking = carparking;
	}
}
