package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	@Query("from Product order by id desc")
	public List<Product> findAllNew();
	
}
