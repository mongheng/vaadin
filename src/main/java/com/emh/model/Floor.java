package com.emh.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FLOOR")
public class Floor {

	private String floorID;
	private Integer floorNumber;
	private Integer totalFloor;
	private Set<Unit> units;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "FLOOR_ID", unique = true)
	public String getFloorID() {
		return floorID;
	}

	public void setFloorID(String floorID) {
		this.floorID = floorID;
	}

	@Column(name = "FLOOR_NUMBER")
	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	@Column(name = "TOTAL_FLOOR")
	public Integer getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}

	@OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
	public Set<Unit> getUnits() {
		return units;
	}

	public void setUnits(Set<Unit> units) {
		this.units = units;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Floor floor = (Floor) obj;
		return ((floorNumber == floor.getFloorNumber()
				|| (floorNumber != null && floorNumber.equals(floor.getFloorNumber()))));
	}

}
