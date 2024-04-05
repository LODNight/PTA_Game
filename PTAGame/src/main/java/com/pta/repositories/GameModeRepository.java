package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.GameMode;

public interface GameModeRepository extends CrudRepository<GameMode, Integer>{
    
@Query("from GameMode order by id desc")
	public List<GameMode> findAllNew();
}
