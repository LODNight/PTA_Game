package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.Developer;

public interface DeveloperRepository extends CrudRepository<Developer, Integer>{
    
@Query("from Developer order by id desc")
	public List<Developer> findAllNew();
}
