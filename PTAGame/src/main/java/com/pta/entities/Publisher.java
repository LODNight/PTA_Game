package com.pta.entities;
// Generated Mar 29, 2024, 8:12:15 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

/**
 * Publisher generated by hbm2java
 */
@Entity
@Table(name = "publisher")
public class Publisher implements java.io.Serializable {

	private Integer id;
	private String name;
	private boolean status;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdTime;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date udpatedTime;
	
	private Set<Product> products = new HashSet<Product>(0);

	public Publisher() {
	}

	public Publisher(String name, boolean status, Date createdTime) {
		this.name = name;
		this.status = status;
		this.createdTime = createdTime;
	}

	public Publisher(String name, boolean status, Date createdTime, Date udpatedTime, Set<Product> products) {
		this.name = name;
		this.status = status;
		this.createdTime = createdTime;
		this.udpatedTime = udpatedTime;
		this.products = products;
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

	@Column(name = "name", nullable = false, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	@Column(name = "udpated_time", length = 19)
	public Date getUdpatedTime() {
		return this.udpatedTime;
	}

	public void setUdpatedTime(Date udpatedTime) {
		this.udpatedTime = udpatedTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
