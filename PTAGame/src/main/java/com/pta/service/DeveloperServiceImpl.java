package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pta.entities.Developer;
import com.pta.repositories.DeveloperRepository;

@Service
public class DeveloperServiceImpl implements DeveloperService{

    @Autowired
	private DeveloperRepository developerRepository;

	@Override
	public Iterable<Developer> findAll() {
		return developerRepository.findAll();
	}

	@Override
	public Developer find(int id) {
		return developerRepository.findById(id).get();
	}

	@Override
	public boolean save(Developer developer) {
		try {
			developerRepository.save(developer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			developerRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    

}
