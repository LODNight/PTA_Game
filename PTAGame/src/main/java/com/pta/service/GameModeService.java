package com.pta.service;

import com.pta.entities.GameMode;

public interface GameModeService {
    public Iterable<GameMode> findAll();
    public GameMode find(int id);

    public boolean save(GameMode gamemode);
	public boolean delete(int id);
}
