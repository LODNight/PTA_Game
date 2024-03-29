package com.pta.entities;
// Generated Mar 29, 2024, 8:12:15 PM by Hibernate Tools 4.3.6.Final

import jakarta.persistence.*;
/**
 * GameGenres generated by hbm2java
 */
@Entity
@Table(name = "game_genres")
public class GameGenres implements java.io.Serializable {

	private Integer id;
	private Genres genres;
	private Product product;

	public GameGenres() {
	}

	public GameGenres(Genres genres, Product product) {
		this.genres = genres;
		this.product = product;
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
	@JoinColumn(name = "genre_id", nullable = false)
	public Genres getGenres() {
		return this.genres;
	}

	public void setGenres(Genres genres) {
		this.genres = genres;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
