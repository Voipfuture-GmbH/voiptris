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

/**
 * Possible user inputs.
 */
public enum UserInput
{
    /**
     * The user wants to restart the game.
     */
    RESTART,
    /**
     * The user wants to pause the game.
     * When this event is received
     * while the game is already paused,
     * the game continues.
     */
    PAUSE,
    /**
     * Move the current tile left (if possible).
     */
    LEFT,
    /**
     * Move the current tile right (if possible).
     */
    RIGHT,
    /**
     * Drop the current tile until it either
     * reaches the bottom of the playing field
     * or hits another tile.
     */
    HARD_DROP,
    /**
     * Rotate the current tile 90 degrees clockwise.
     */
    ROTATE_CW,
    /**
     * Rotate the current tile 90 degrees counter-clockwise.
     */
    ROTATE_CCW
}