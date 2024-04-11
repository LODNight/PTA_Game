package com.pta.service;

import com.pta.entities.Product;

public interface ProductService {
    public Iterable<Product> findAll();
    public Product find(int id);

    public boolean save(Product product);
	public boolean delete(int id);
}
