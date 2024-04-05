package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pta.entities.Genres;
import com.pta.repositories.GenresRepository;

@Service
public class GenresServiceImpl implements GenresService{

    @Autowired
	private GenresRepository genresRepository;
    
    @Override
    public Iterable<Genres> findAll() {
        return genresRepository.findAll();
    }

    @Override
    public Genres find(int id) {
        return genresRepository.findById(id).get();
    }

    @Override
    public boolean save(Genres genres) {
        try {
			genresRepository.save(genres);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }


    @Override
    public boolean delete(int id) {
        try {
			genresRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

}
