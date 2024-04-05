package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pta.entities.Publisher;
import com.pta.repositories.PublisherRepository;

@Service
public class PublisherServiceImpl implements PublisherService{

    @Autowired
	private PublisherRepository publisherRepository;

	@Override
	public Iterable<Publisher> findAll() {
		return publisherRepository.findAll();
	}

	@Override
	public Publisher find(int id) {
		return publisherRepository.findById(id).get();
	}

	@Override
	public boolean save(Publisher publisher) {
		try {
			publisherRepository.save(publisher);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			publisherRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    

}
