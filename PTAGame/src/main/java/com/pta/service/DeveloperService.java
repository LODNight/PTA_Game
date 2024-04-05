package com.pta.service;

import com.pta.entities.Developer;

public interface DeveloperService {
    public Iterable<Developer> findAll();
    public Developer find(int id);

    public boolean save(Developer developer);
	public boolean delete(int id);
}
