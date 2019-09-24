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
 * Game states.
 */
public enum GameState
{
    /**
     * The game is currently paused.
     */
    PAUSED,
    /**
     * Game is running.
     */
    RUNNING,
    /**
     * Game over.
     *
     * This must also be the initial state after
     * the application has been started.
     */
    GAME_OVER,
}
