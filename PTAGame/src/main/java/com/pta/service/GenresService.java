package com.pta.service;

import com.pta.entities.Genres;

public interface GenresService {
    public Iterable<Genres> findAll();
    public Genres find(int id);

    public boolean save(Genres genres);
	public boolean delete(int id);
}
