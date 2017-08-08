package com.emh.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CASHFLOW")
public class CashFlow {

	private String cashflowID;
	private Integer installmentNumber;
	private Float amount;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean statu;
	private Contract contract;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "CASHFLOW_ID", unique = true)
	public String getCashflowID() {
		return cashflowID;
	}

	public void setCashflowID(String cashflowID) {
		this.cashflowID = cashflowID;
	}

	@Column(name = "INSTALLMENT_NUMBER")
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	@Column(name = "AMOUNT")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Column(name = "STARTDATE")
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Column(name = "ENDDATE")
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Column(name = "STATU", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean getStatu() {
		return statu;
	}

	public void setStatu(boolean statu) {
		this.statu = statu;
	}

	@OneToOne
	@JoinColumn(name = "CONTRACT_ID")
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}
