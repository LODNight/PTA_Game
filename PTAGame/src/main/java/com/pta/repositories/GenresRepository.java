package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.Genres;

public interface GenresRepository extends CrudRepository<Genres, Integer>{
    
@Query("from Genres order by id desc")
	public List<Genres> findAllNew();
}
