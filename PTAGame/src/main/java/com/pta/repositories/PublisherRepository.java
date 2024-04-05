package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Integer>{
    
@Query("from Publisher order by id desc")
	public List<Publisher> findAllNew();
}
