package com.emh.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ACCESS_ROLES")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleID;
	private String roleName;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "ROLE_ID", unique = true)
	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
