package com.voipfuture.voiptris.impl;

import com.voipfuture.voiptris.api.*;

import javax.swing.Timer;
import java.awt.*;
import java.util.*;

import static com.voipfuture.voiptris.api.TileType.TYPE_NONE;
import static com.voipfuture.voiptris.impl.GameShapeConstant.GAME_SHAPES;

public class GameController implements IGameController {

    private PlayingField playingField;
    private GameState gameState = GameState.GAME_OVER;
    private int totalScore = 0;
    private int level = 0;
    boolean isPieceNew = true;

    private Point currentPoint;
    private int currentIndex;
    private Set<Point> pointArr = new HashSet<>();
    private TileType[][] boardState;

    final Timer timer = new Timer(1000, ev -> {
        if(gameState == GameState.RUNNING) {
            this.dropToDownCurrentShape();
        }
    });


    public GameController(int playingFieldWidth, int playingFieldHeight) {
        if (playingFieldHeight < 15 || playingFieldWidth < 10) {
            throw new IllegalArgumentException("Invalid Controller Parameters!");
        }
        this.playingField = new PlayingField(playingFieldWidth, playingFieldHeight, this);
        boardState = new TileType[playingField.width()][playingField.height()];
    }

    @Override
    public void tick(IUserInputProvider inputProvider) {
        Optional<UserInput> userInputOptional = inputProvider.tick();
        if (userInputOptional.isPresent()) {
            inputProvider.clearInput();
            switch (userInputOptional.get()) {
                case RESTART:
                    startGame();
                    startNewShape();
                    break;
                case PAUSE:
                    pauseGame();
                    break;
                case RIGHT:
                    // TODO Move To Right
                    break;
                case LEFT:
                    // TODO Move To Left
                    break;
            }
        }
    }

    @Override
    public GameState getState() {
        return this.gameState;
    }

    @Override
    public int getScore() {
        return totalScore;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getRowsUntilNextLevel() {
        return 0;
    }

    @Override
    public IPlayingField getPlayingField() {
        return this.playingField;
    }

    @Override
    public Optional<TileType> getCurrentScreenState(int x, int y) {
        if (boardState[0][0] != null)
            return Optional.of(this.boardState[x][y]);
        else
            return Optional.of(TYPE_NONE);
    }

    private void pauseGame() {
        if (gameState == GameState.RUNNING) {
            gameState = GameState.PAUSED;
            timer.stop();
        } else if (gameState == GameState.PAUSED) {
            gameState = GameState.RUNNING;
            timer.start();
        }
    }

    private void startGame() {
        gameState = GameState.RUNNING;
        setDefaultBoardState();
        timer.start();
    }

    public void startNewShape() {
        int centerX = playingField.width() / 2 - 1;
        currentPoint = new Point(centerX, 0);
        currentIndex = new Random().nextInt(7);
    }

    private boolean checkCollision(int x, int y) {
        Set<Point> points = new HashSet<>();
        for (Point point : GAME_SHAPES[currentIndex]) {
            points.add(new Point(point.x + x, point.y + y - 1));
        }
        for (Point point : GAME_SHAPES[currentIndex]) {
            Point newPoint = new Point(point.x + x, point.y + y);
            if (((y + point.y) == playingField.height() || (!points.contains(newPoint) && boardState[point.x + x][point.y + y] != TYPE_NONE))) {
                return true;
            }
        }
        return false;
    }

    private void refreshBoardStatus() {
        if (isPieceNew)
            for (Point p : pointArr) {
                boardState[p.x][p.y] = TYPE_NONE;
            }
        isPieceNew = true;
        pointArr = new HashSet<>();
        for (Point p : GAME_SHAPES[currentIndex]) {
            pointArr.add(new Point(currentPoint.x + p.x, currentPoint.y + p.y));
            boardState[currentPoint.x + p.x][currentPoint.y + p.y] = TileType.values()[currentIndex];
        }
    }

    private void dropToDownCurrentShape() {
        if (!checkCollision(currentPoint.x, currentPoint.y)) {
            refreshBoardStatus();
            currentPoint.y += 1;
        } else {
            completeCurrentShapeAndStartNew();
            isPieceNew = false;
        }
    }

    public void completeCurrentShapeAndStartNew() {
        for (Point p : GAME_SHAPES[currentIndex]) {
            boardState[currentPoint.x + p.x][currentPoint.y + p.y - 1] = TileType.values()[currentIndex];
        }
        isPieceNew = true;
        startNewShape();
    }

    private void setDefaultBoardState() {
        for (TileType[] array : boardState) {
            Arrays.fill(array, TileType.TYPE_NONE);
        }
    }
}
