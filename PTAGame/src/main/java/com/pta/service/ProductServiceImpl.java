package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
import com.pta.entities.Product;
import com.pta.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
	private ProductRepository productRepository;
    
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product find(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public boolean save(Product product) {
        try {
        	productRepository.save(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }


    @Override
    public boolean delete(int id) {
        try {
        	productRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

}
