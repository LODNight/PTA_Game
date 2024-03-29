package com.pta.entities;
// Generated Mar 29, 2024, 8:12:15 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;

import jakarta.persistence.*;

/**
 * Images generated by hbm2java
 */
@Entity
@Table(name = "images")
public class Images implements java.io.Serializable {

	private Integer id;
	private String name;
	private String lenght;
	private String type;
	private Date createdTime;
	private Date udpatedTime;

	public Images() {
	}

	public Images(String name, String lenght, String type, Date createdTime) {
		this.name = name;
		this.lenght = lenght;
		this.type = type;
		this.createdTime = createdTime;
	}

	public Images(String name, String lenght, String type, Date createdTime, Date udpatedTime) {
		this.name = name;
		this.lenght = lenght;
		this.type = type;
		this.createdTime = createdTime;
		this.udpatedTime = udpatedTime;
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

	@Column(name = "lenght", nullable = false, length = 250)
	public String getLenght() {
		return this.lenght;
	}

	public void setLenght(String lenght) {
		this.lenght = lenght;
	}

	@Column(name = "type", nullable = false, length = 250)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}