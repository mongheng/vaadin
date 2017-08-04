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
	private boolean statu;
	private Floor floor;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
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

	@Column(name = "STATU", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean isStatu() {
		return statu;
	}

	public void setStatu(boolean statu) {
		this.statu = statu;
	}

	@ManyToOne
	@JoinColumn(name = "FLOOR_ID")
	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Unit unit = (Unit) obj;
		return ((unitNumber == unit.getUnitNumber() || (unitNumber != null && unitNumber.equals(unit.getUnitNumber())))
				&& (floor == unit.getFloor() || (floor != null && floor.equals(unit.getFloor()))));
	}

}
