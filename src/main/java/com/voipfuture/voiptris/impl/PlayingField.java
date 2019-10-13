package com.voipfuture.voiptris.impl;

import com.voipfuture.voiptris.api.IGameController;
import com.voipfuture.voiptris.api.IPlayingField;
import com.voipfuture.voiptris.api.TileType;

import java.util.Optional;

public class PlayingField implements IPlayingField {

    private int width;
    private int height;
    private IGameController gameController;

    PlayingField(int width, int height, IGameController gameController) {
        this.height = height;
        this.width = width;
        this.gameController = gameController;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public Optional<TileType> getTileType(int x, int y) {
        return this.gameController.getCurrentScreenState(x, y);
    }
}
