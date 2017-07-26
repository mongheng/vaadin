package com.emh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "UNIT")
public class Unit {

	private String unitID;
	private Integer unitNumber;
	private Floor floor;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "UNIT_ID", unique = true)
	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}

	@Column(name = "UNIT_NUMBER")
	public Integer getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	@ManyToOne
	@JoinColumn(name = "FLOOR_ID")
	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

}
