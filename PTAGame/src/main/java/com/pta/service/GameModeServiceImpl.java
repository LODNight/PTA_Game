package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pta.entities.GameMode;
import com.pta.repositories.GameModeRepository;

@Service
public class GameModeServiceImpl implements GameModeService{

    @Autowired
	private GameModeRepository gamemodeRepository;
    
    @Override
    public Iterable<GameMode> findAll() {
        return gamemodeRepository.findAll();
    }

    @Override
    public GameMode find(int id) {
        return gamemodeRepository.findById(id).get();
    }

    @Override
    public boolean save(GameMode gamemode) {
        try {
        	gamemodeRepository.save(gamemode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }


    @Override
    public boolean delete(int id) {
        try {
        	gamemodeRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

}
