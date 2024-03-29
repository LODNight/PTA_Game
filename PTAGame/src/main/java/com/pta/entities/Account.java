package com.pta.entities;
// Generated Mar 29, 2024, 8:12:15 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;

import jakarta.persistence.*;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	private Integer id;
	private Role role;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private boolean status;
	private Date createdTime;
	private Date updatedTime;

	public Account() {
	}

	public Account(Role role, String email, String password, boolean status, Date createdTime) {
		this.role = role;
		this.email = email;
		this.password = password;
		this.status = status;
		this.createdTime = createdTime;
	}

	public Account(Role role, String email, String password, String firstName, String lastName, String phone,
			String address, boolean status, Date createdTime, Date updatedTime) {
		this.role = role;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "email", nullable = false, length = 250)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 250)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "first_name", length = 250)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 250)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone", length = 250)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address", length = 250)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", nullable = false, length = 19)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time", length = 19)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
