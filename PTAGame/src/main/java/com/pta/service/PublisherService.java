package com.pta.service;

import com.pta.entities.Publisher;

public interface PublisherService {
    public Iterable<Publisher> findAll();
    public Publisher find(int id);

    public boolean save(Publisher publisher);
	public boolean delete(int id);
}
