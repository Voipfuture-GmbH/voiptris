/**
 * Copyright 2012 Tobias Gierke <tobias.gierke@code-sourcery.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.voipfuture.voiptris.api;

import java.util.Optional;

/**
 * Responsible for controlling the game.
 *
 * Implementations <b>must</b> provide a two-arguments constructor
 * that takes the desired playing field width as first parameter
 * and the desired playing field height as second parameter.
 *
 * A playing field will always be at least 10 cells wide
 * and 15 cells tall.
 *
 * New tiles must appear at the very top of the playing field
 */
public interface IGameController
{
    /**
     * Game loop: Invoked 60 times per second to advance the game state.
     *
     * This method should process the user's current input (if any)
     * and advance the game state (move tile downwards etc) if necessary.
     * @param inputProvider can be used to read user input
     */
    void tick(IUserInputProvider inputProvider);

    /**
     * Returns the current game state.
     * @return
     */
    GameState getState();

    /**
     * Returns the player's current score.
     * @return
     */
    int getScore();

    /**
     * Returns the current difficulty level.
     * @return
     */
    int getLevel();

    /**
     * Returns the number of rows the player has still to
     * complete to advance to the next level.
     *
     * @return
     */
    int getRowsUntilNextLevel();

    /**
     * Returns the playing field to be rendered.
     *
     * As this method is also called during rendering
     * (and thus roughly sixty times per second), it
     * should avoid object allocations and costly operations.
     *
     * @return
     */
    IPlayingField getPlayingField();

    /**
     *
     * @param x
     * @param y
     * @return
     */
    Optional<TileType> getCurrentScreenState(int x, int y);

}