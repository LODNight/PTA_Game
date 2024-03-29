package com.pta.entities;
// Generated Mar 29, 2024, 8:12:15 PM by Hibernate Tools 4.3.6.Final

import jakarta.persistence.*;

/**
 * GameMode generated by hbm2java
 */
@Entity
@Table(name = "game_mode")
public class GameMode implements java.io.Serializable {

	private Integer id;
	private String name;
	private String imageIcon;

	public GameMode() {
	}

	public GameMode(String name) {
		this.name = name;
	}

	public GameMode(String name, String imageIcon) {
		this.name = name;
		this.imageIcon = imageIcon;
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

	@Column(name = "imageIcon", length = 250)
	public String getImageIcon() {
		return this.imageIcon;
	}

	public void setImageIcon(String imageIcon) {
		this.imageIcon = imageIcon;
	}

}
