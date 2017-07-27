package com.emh.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userid;
	private String username;
	private String password;
	private String telephone;
	private String email;
	private Role role;

	public User() {
	
	}

	public User(String userid, String username, String password, String telephone, String email) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
	}

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "USER_ID", unique = true)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToOne
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
